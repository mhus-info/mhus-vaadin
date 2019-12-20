package de.mhus.lib.vaadin.widgets;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class VPanel extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private VerticalLayout content;
    private Div header;
    private Div footer;
    private Label title;
    private Component component;

    public VPanel() {
        
        header = new Div();
        header.setWidth("100%");
        title = new Label();
        header.add(title);
        
        content = new VerticalLayout();
        content.setWidth("100%");
        content.getElement().getStyle().set("overflow", "auto");
        
        footer = new Div();
        footer.setWidth("100%");
        
        header.setEnabled(false);
        footer.setEnabled(false);
        
        super.add(header);
        super.add(content);
        super.add(footer);
    }
    
    public Label getTitleElement() {
        return title;
    }
    
    public String getTitle() {
        return title.getText();
    }
    
    public void setTitle(String title) {
        if (title == null) {
            this.title.setEnabled(false);
            this.title.setText("");
        } else {
            this.title.setEnabled(true);
            this.title.setText(title);
        }
    }
    
    @Override
    public void add(Component ... component) {
        this.content.add(component);
    }
    
    @Override
    public void removeAll() {
        this.content.removeAll();
    }
    
    @Override
    public void remove(Component ... components) {
        this.content.remove(components);
    }
    
    public void setContent(Component component) {
        this.component = component;
        this.content.removeAll();
        if (component != null)
            this.content.add(component);
    }

    public Component getContent() {
        return component;
    }

    public VerticalLayout getContentLayout() {
        return content;
    }
    
}
