/**
 * Copyright 2018 Mike Hummel
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
package de.mhus.lib.vaadin.widgets.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import de.mhus.lib.core.MCast;

public class BooleanPrimitiveConverter implements Converter<String, Boolean> {

	private static final long serialVersionUID = 1L;

    @Override
    public Result<Boolean> convertToModel(String value, ValueContext context) {
        return new SimpleResult<Boolean>(MCast.toboolean(value, false), null);
    }

    @Override
    public String convertToPresentation(Boolean value, ValueContext context) {
        return value.booleanValue() ? "\u2612" : "\u2610";
    }
	
}
