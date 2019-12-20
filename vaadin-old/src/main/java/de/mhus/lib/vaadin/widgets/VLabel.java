package de.mhus.lib.vaadin.widgets;

import com.vaadin.flow.component.html.Label;

public class VLabel extends Label {

    private static final long serialVersionUID = 1L;
    private boolean captionAsHtml;


    @Override
    public void setText(String text) {
        if (captionAsHtml)
            getElement().setProperty("innerHTML", text);
        else
            super.setText(text);
    }

    public boolean isCaptionAsHtml() {
        return captionAsHtml;
    }


    public void setCaptionAsHtml(boolean captionAsHtml) {
        this.captionAsHtml = captionAsHtml;
    }

}
