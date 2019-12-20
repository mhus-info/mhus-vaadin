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
package de.mhus.lib.vaadin.widgets.layouter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;

import de.mhus.lib.core.directory.ResourceNode;
import de.mhus.lib.errors.MException;

public class LayUtil {

	private static final Pattern sizePattern = Pattern
            .compile("^(-?\\d+(\\.\\d+)?)(%|px|em|ex|in|cm|mm|pt|pc)?$");
	
	public static void configure(Component layout, ResourceNode<?> config) throws MException {
		if (config.getBoolean(LayoutBuilder.FULL_SIZE, false))
			((HasSize)layout).setSizeFull();
		else {
			String width = config.getString(LayoutBuilder.WIDTH, null);
			if (width != null) ((HasSize)layout).setWidth(width);
			String height = config.getString(LayoutBuilder.HEIGHT, null);
			if (height != null) ((HasSize)layout).setHeight(height);
		}
		
		// margin
//TODO		if (layout instanceof Layout && config.isProperty(LayoutBuilder.MARGIN))
//			((Layout)layout).setMargin(config.getBoolean(LayoutBuilder.MARGIN, false));
		// spacing
		if (layout instanceof Layout.SpacingHandler && config.isProperty(LayoutBuilder.SPACING))
			((Layout.SpacingHandler)layout).setSpacing(config.getBoolean(LayoutBuilder.SPACING, false));
		// styles
		if (config.isProperty(LayoutBuilder.STYLE)) {
			layout.setStyleName(config.getExtracted(LayoutBuilder.STYLE));
		}
		// hidden
		if (config.getBoolean(LayoutBuilder.HIDDEN, false))
			layout.setVisible(false);
		
		// split
		if (layout instanceof AbstractSplitPanel) {
			String a = config.getString(LayoutBuilder.SPLIT_MIN,null);
			if (a != null) { 
				float[] s = parseStringSize(a);
				if (s[0] >=0)
					((AbstractSplitPanel)layout).setMinSplitPosition(s[0],Unit.values()[(int)s[1]]);
			}
			a = config.getString(LayoutBuilder.SPLIT_MAX,null);
			if (a != null) { 
				float[] s = parseStringSize(a);
				if (s[0] >=0)
					((AbstractSplitPanel)layout).setMaxSplitPosition(s[0],Unit.values()[(int)s[1]]);
			}
			a = config.getString(LayoutBuilder.SPLIT_POS,null);
			if (a != null) { 
				float[] s = parseStringSize(a);
				if (s[0] >=0)
					((AbstractSplitPanel)layout).setSplitPosition(s[0],Unit.values()[(int)s[1]]);
			}
		}
		
	}
	
}
