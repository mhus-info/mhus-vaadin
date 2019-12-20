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
package de.mhus.lib.vaadin.widgets.form;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;

import de.mhus.lib.form.UiWizard;
import de.mhus.lib.vaadin.widgets.VGridLayout;

/**
 * | 0       | 1     | 2      | 3       | 4     | 5      |
 * 
 * 50 x 50
 * | Caption | Field | Wizard | Caption | Field | Wizard |
 * | Caption | Field          | Caption | Field          |
 * |         | Error          |         | Error          |
 * 
 * 50 x 50 full
 * | Caption                  | Caption                  |
 * | Field                    | Field                    |
 * | Field           | Wizard | Field           | Wizard |
 * | Error                    | Error                    |
 * 
 * 100
 * | Caption | Field                            | Wizard |
 * | Caption | Field                                     |
 * |         | Error                                     |
 * 
 * 100 full
 * | Caption                                             |
 * | Field                                               |
 * | Field                                      | Wizard |
 * | Error                                               |
 * 
 * @author mikehummel
 *
 */

public abstract class AbstractColLayout extends UiLayout {

	private static final long serialVersionUID = 1L;
	private int columns;
	private VGridLayout layout;
	private int rows;
	private UiSlot slot;

	
	public AbstractColLayout(int columns) {
		this.columns = columns;
		this.layout = new VGridLayout(columns*3,1);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setHideEmptyRowsAndColumns(true);
		for (int i = 0; i < columns; i++) {
			layout.setColumnExpandRatio(i*3 + 0, 0.3f);
			layout.setColumnExpandRatio(i*3 + 1, 0.7f);
			layout.setColumnExpandRatio(i*3 + 2, 0);
		}
//		layout.setSizeFull();
		layout.setWidth("100%");
		rows = 0;
	}
	
	@Override
	public void createRow(final UiVaadin c) {
		final UiWizard wizard = c.getWizard();
		Component e = c.createEditor();
		if (e == null) return;
		c.setComponentEditor(e);
		
		if (e instanceof HasSize)
		    ((HasSize)e).setWidth("100%");
		//e.setHeight("30px");
		
		c.setListeners();

		if (slot == null) {
			slot = new UiSlot();
		} else {
			if (c.getConfig().getInt("columns", 1) > (columns-1))
				slot = new UiSlot();
		}
		
		slot.add(c, wizard, e);
		
		if (slot.isFull()) slot = null;
		
	}
	
	protected UiRow createRow(int col, int size, int row) {
		return new UiRow(layout, col, row, size);
	}

	@Override
	public Component getComponent() {
		return layout;
	}

	public class UiRow {

		private VGridLayout layout;
		private int row;
		private boolean full;
		private boolean wizard;
		private int col;
		private int size;

		public UiRow(VGridLayout layout, int col, int row, int size) {
			this.layout = layout;
			this.row = row;
			this.col = col;
			this.size = size;
		}
		
		public void setLeft(Component component) {
			if (full) return;
			layout.add(component, col, row);
		}

		public void setRight(Component component) {
			if (full) return;
			if (wizard)
				layout.add(component, col+1, row, col+size-1, row);
			else
				layout.add(component, col+1, row, col+size, row);
		}
		
		public void setComponent(Component component) {
			if (!full) {
				setRight(component);
				return;
			}
			if (wizard)
				layout.add(component, 0, row, col+size-1, row);
			else
				layout.add(component, 0, row, col+size, row);
		}

		public boolean isFull() {
			return full;
		}

		public void setFull(boolean full) {
			this.full = full;
		}

		public boolean isWizard() {
			return wizard;
		}

		public void setWizard(Component wizard) {
			this.wizard = wizard != null;
			if (wizard == null) return;
			layout.add(wizard, col+size, row );
		}
		
	}

	protected class UiSlot {

		private int startRow;
		private int col;

		public UiSlot() {
			startRow = rows;
			rows+=3;
			layout.setRows(rows);
			col = 0;
		}
		
		public void add(final UiVaadin c, final UiWizard wizard, final Component e) {

			int cc = c.getConfig().getInt("columns", 1);
			if (cc > columns) cc = columns;
			int size = (cc-1) * 3 + 2;
			UiRow row1 = createRow(col, size, startRow);
			UiRow row2 = createRow(col, size, startRow+1);
			UiRow row3 = createRow(col, size, startRow+2);
			
			if (c.isFullSize()) {
				
				row1.setFull(true);
				
				Label l = new Label();
				l.setClassName("form-label");
				l.setWidth("100%");
				c.setComponentLabel(l);
				row1.setComponent(l);

				row2.setFull(true);
				
				if (wizard != null) {
					Button b = new Button();
					b.setIcon(VaadinIcon.COG.create()); //FontAwesome.COG
					b.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
                        
					    private static final long serialVersionUID = 1L;
                        @Override
                        public void onComponentEvent(ClickEvent<Button> event) {
                            wizard.showWizard(c);
                        }
                    });
					b.setWidth("100%");
					row2.setWizard(b);
					c.setComponentWizard(b);
				}
				
				row2.setComponent(e);
				
			} else {
				
				Label l = new Label();
				l.setWidth("100%");
				c.setComponentLabel(l);
				row1.setLeft(l);

				if (wizard != null) {
					Button b = new Button("W");
					b.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
                        
					    private static final long serialVersionUID = 1L;
                        @Override
                        public void onComponentEvent(ClickEvent<Button> event) {
                            wizard.showWizard(c);
                        }
                    });
					b.setWidth("100%");
					row1.setWizard(b);
					c.setComponentWizard(b);
				}

				row1.setRight(e);
				
			}
			
			row3.setFull(c.isFullSize());
			Label le = new Label();
			le.setClassName("error-text");
			le.setWidth("100%");
			c.setComponentError(le);
			row3.setComponent(le);
			
			col+=1 + size;
		}

		public boolean isFull() {
			return col >= columns*3;
		}
		
	}
	
}
