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

import java.util.List;
import java.util.UUID;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Page;

import de.mhus.lib.core.util.Pair;

@SuppressWarnings("deprecation")
public abstract class VWorkBar extends HorizontalLayout {

	private static final long serialVersionUID = 1L;
	private Button bDelete;
	private Button bModify;
	private Button bAdd;
	private Label tStatus;
	private ComboBox<Pair<String,Object[]>> menuDelete;
	private ComboBox<Pair<String,Object[]>> menuModify;
	private ComboBox<Pair<String,Object[]>> menuAdd;

	@SuppressWarnings("serial")
	public VWorkBar() {

		menuDelete = new ComboBox<>();
		menuDelete.setAllowCustomValue(false);
		menuDelete.setId("a" + UUID.randomUUID().toString().replace('-', 'x'));
		menuDelete.setWidth("0px");
		//menuDelete.setNullSelectionAllowed(false);
		menuDelete.addValueChangeListener(new HasValue.ValueChangeListener<HasValue.ValueChangeEvent<?>>() {

            @Override
            public void valueChanged(ValueChangeEvent<?> event) {
                doMenuSelected();
            }
        });
		add(menuDelete);
		
		bDelete = new Button(VaadinIcon.MINUS.create());
		add(bDelete);
		bDelete.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                doDelete();
            }
        });
		
		menuModify = menuDelete;
//		menuModify = new ComboBox();
//		menuModify.setTextInputAllowed(false);
//		menuModify.setId("a" + UUID.randomUUID().toString().replace('-', 'x'));
//		menuModify.setWidth("0px");
//		menuModify.setNullSelectionAllowed(false);
//		menuModify.addValueChangeListener(new Property.ValueChangeListener() {
//			
//			@Override
//			public void valueChange(ValueChangeEvent event) {
//				doMenuSelected();
//			}
//		});
//		addComponent(menuModify);
		
		bModify = new Button(VaadinIcon.COG.create());
		add(bModify);
		bModify.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                doModify();
            }
        });

		menuAdd = menuDelete;
//		menuAdd = new ComboBox();
//		menuAdd.setTextInputAllowed(false);
//		menuAdd.setId("a" + UUID.randomUUID().toString().replace('-', 'x'));
//		menuAdd.setWidth("0px");
//		menuAdd.setNullSelectionAllowed(false);
//		menuAdd.addValueChangeListener(new Property.ValueChangeListener() {
//			
//			@Override
//			public void valueChange(ValueChangeEvent event) {
//				doMenuSelected();
//			}
//		});
//		addComponent(menuAdd);

		bAdd = new Button(VaadinIcon.PLUS.create());
		add(bAdd);
		bAdd.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                doAdd();
            }
        });

		tStatus = new Label();
		add(tStatus);
		//XXX setExpandRatio(tStatus, 1);
		
	}
	
	public void setButtonStyleName(String style) {
		bAdd.setClassName(style);
		bModify.setClassName(style);
		bDelete.setClassName(style);
	}
	
	@SuppressWarnings("unchecked")
	protected void doMenuSelected() {
		Pair<String,Object[]> item = (Pair<String,Object[]>) menuDelete.getValue();
		if (item == null) return;
		Object[] val = item.getValue();
		if (((String)val[0]).equals("add"))
			doAdd(val[1]);
		else
		if (((String)val[0]).equals("mod"))
			doModify(val[1]);
		else
		if (((String)val[0]).equals("del"))
			doDelete(val[1]);
			
	}

	protected void doAdd() {
		List<Pair<String,Object>> options = getAddOptions();
		if (options == null || options.size() <= 0) return;
//		if (options.size() == 1) {
//			doAdd(options.get(0).getValue());
//		} else {
			
		//XXX menuAdd.removeAll();
			for (Pair<String, Object> item : options) {
				Pair<String, Object[]> out = new Pair<String, Object[]>(item.getKey(), new Object[] {"add", item.getValue()} );
				//XXX		menuAdd.addItem(out);
			}
			String myCode = "$('#" + menuAdd.getId() + "').find('input')[0].click();";
			//XXX	Page.getCurrent().getJavaScript().execute(myCode);
//		}
	}
	
	public abstract List<Pair<String, Object>> getAddOptions();

	public abstract List<Pair<String, Object>> getModifyOptions();
	
	public abstract List<Pair<String, Object>> getDeleteOptions();

	protected void doModify() {
		List<Pair<String,Object>> options = getModifyOptions();
		if (options == null || options.size() <= 0) return;
//		if (options.size() == 1) {
//			doModify(options.get(0).getValue());
//		} else {
			
		//XXX			menuModify.removeAllItems();
			for (Pair<String, Object> item : options) {
				Pair<String, Object[]> out = new Pair<String, Object[]>(item.getKey(), new Object[] {"mod", item.getValue()} );
				//XXX				menuModify.addItem(out);
			}
			String myCode = "$('#" + menuModify.getId() + "').find('input')[0].click();";
//XXX			Page.getCurrent().getJavaScript().execute(myCode);
//		}
	}

	protected void doDelete() {
		List<Pair<String,Object>> options = getDeleteOptions();
		if (options == null || options.size() <= 0) return;
//		if (options.size() == 1) {
//			doDelete(options.get(0).getValue());
//		} else {
			
		//XXX			menuDelete.removeAllItems();
			for (Pair<String, Object> item : options) {
				Pair<String, Object[]> out = new Pair<String, Object[]>(item.getKey(), new Object[] {"del", item.getValue()} );
				//XXX				menuDelete.addItem(out);
			}
			String myCode = "$('#" + menuDelete.getId() + "').find('input')[0].click();";
			//XXX			Page.getCurrent().getJavaScript().execute(myCode);
//		}
	}

	protected abstract void doModify(Object action);

	protected abstract void doDelete(Object action);

	protected abstract void doAdd(Object action);

	public void setStatus(String msg) {
		tStatus.setText(msg);
	}
	
}
