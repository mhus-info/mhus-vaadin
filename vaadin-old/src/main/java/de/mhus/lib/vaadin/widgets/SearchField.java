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
package de.mhus.lib.vaadin.widgets;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.GeneratedVaadinComboBox.CustomValueSetEvent;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import de.mhus.lib.core.util.FilterRequest;
import de.mhus.lib.core.util.MNlsProvider;

public class SearchField extends HorizontalLayout {

	private static final long serialVersionUID = 1L;
	private ComboBox<String> filter;
	private Listener listener;
	private Button bSearch;
	@SuppressWarnings("unused")
	private MNlsProvider nlsProvider;
//	private LinkedList<String> knownFacetNames = new LinkedList<>();

	@SuppressWarnings("serial")
	public SearchField(MNlsProvider nlsProvider) {
		this.nlsProvider = nlsProvider;
        filter = new ComboBox<>();
        filter.setAllowCustomValue(true);
        filter.addCustomValueSetListener(new ComponentEventListener<CustomValueSetEvent<ComboBox<String>>>() {

            private static final long serialVersionUID = 1L;
            @Override
            public void onComponentEvent(CustomValueSetEvent<ComboBox<String>> event) {
                String newItemCaption = event.getDetail();
                addKnownFacetName(newItemCaption);
                filter.setValue(newItemCaption);
            }
            
        });
        //XXX filter.setInputPrompt(MNls.find(nlsProvider, "filter.prompt=Filter"));

        filter.addValueChangeListener(new HasValue.ValueChangeListener<HasValue.ValueChangeEvent<?>>() {

            @Override
            public void valueChanged(ValueChangeEvent<?> event) {
                doFilter();
            }
        });

        add(filter);
        setFlexGrow(1, filter);
        filter.setWidth("100%");
        
        bSearch = new Button();
        bSearch.setIcon(VaadinIcon.SEARCH.create());
        bSearch.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                doFilter();
            }
        });

        add(bSearch);
        setFlexGrow(0, bSearch);
        setWidth("100%");

	}

	protected void doFilter() {
		if (listener != null) listener.doFilter(this);
	}

	public void setInputPrompt(String prompt) {
//XXX		filter.setInputPrompt(prompt);
	}
	
	public void setValue(String value) {
		filter.setValue(value);
	}
	
	public FilterRequest createFilterRequest() {
		return new FilterRequest( (String)filter.getValue() );
	}
	
	public Listener getListener() {
		return listener;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public void addKnownFacetName(String name) {
//XXX
	}

	public static interface Listener {

		void doFilter(SearchField searchField);
		
	}
	
}
