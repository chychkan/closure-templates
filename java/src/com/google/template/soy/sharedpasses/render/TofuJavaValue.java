/*
 * Copyright 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.template.soy.sharedpasses.render;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.data.restricted.NullData;
import com.google.template.soy.data.restricted.UndefinedData;
import com.google.template.soy.plugin.java.restricted.JavaValue;
import com.ibm.icu.util.ULocale;
import javax.annotation.Nullable;

/** Wraps a {@link SoyValue} into a {@link JavaValue}. */
final class TofuJavaValue implements JavaValue {
  static TofuJavaValue forSoyValue(SoyValue soyValue) {
    return new TofuJavaValue(soyValue, null);
  }

  static TofuJavaValue forULocale(ULocale locale) {
    return new TofuJavaValue(null, checkNotNull(locale));
  }

  @Nullable private final SoyValue soyValue;
  @Nullable private final ULocale locale;

  private TofuJavaValue(SoyValue soyValue, ULocale locale) {
    this.soyValue = soyValue;
    this.locale = locale;
  }

  boolean hasSoyValue() {
    return soyValue != null;
  }

  SoyValue soyValue() {
    checkState(soyValue != null);
    return soyValue;
  }

  ULocale locale() {
    checkState(locale != null);
    return locale;
  }

  @Override
  public TofuJavaValue isNonNull() {
    return forSoyValue(
        BooleanData.forValue(!(soyValue instanceof UndefinedData || soyValue instanceof NullData)));
  }

  @Override
  public TofuJavaValue isNull() {
    return forSoyValue(
        BooleanData.forValue(soyValue instanceof UndefinedData || soyValue instanceof NullData));
  }

  @Override
  public ValueSoyType soyType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return "TofuJavaValue[soyValue=" + soyValue + ", locale=" + locale + "]";
  }
}
