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
