/**
 * Copyright (C) 2019 Mike Hummel (mh@mhus.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mhus.lib.vaadin;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class InfoDialog extends ModalDialog {

    private static final long serialVersionUID = 1L;
    private String title;
    private String info;
    private String textHeight = "50px";

    public InfoDialog(String title, String info) {
        this.title = title;
        this.info = info;
        actions = new Action[] {CLOSE};
    }

    public InfoDialog(String title, String info, String textHeight) {
        this.title = title;
        this.info = info;
        this.textHeight = textHeight;
    }

    @Override
    protected void initContent(VerticalLayout layout) throws Exception {
        pack = true;
        setCaption(title);
        TextArea text = new TextArea();
        text.setEnabled(false);
        text.setValue(info);
        text.setHeight(textHeight);
        text.setWidth("100%");
        layout.addComponent(text);
    }

    @Override
    protected boolean doAction(Action action) {
        return true;
    }

    public static void show(UI ui, String title, String info) {
        try {
            InfoDialog dialog = new InfoDialog(title, info);
            dialog.initUI();
            dialog.show(ui);
        } catch (Exception e) {
        }
    }
}
