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

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import de.mhus.lib.core.MCast;

public class SqlDateConverter implements Converter<String, Date> {

	private static final long serialVersionUID = 1L;

    @Override
    public Result<Date> convertToModel(String value, ValueContext context) {
        return new SimpleResult<Date>(new Date(MCast.toDate(value, null).getTime()), null);
    }

    @Override
    public String convertToPresentation(Date value, ValueContext context) {
        if (value == null || ((Date) value).getTime() == 0) return "-";
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("dd.MM.yyyy hh:mm:ss a");
        return df.format( ((Date) value).getTime());
    }
	
}
