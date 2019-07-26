package de.mhus.vaadin.osgi;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
@Theme(Lumo.class)
public class MainView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public MainView() {
        Button button = new Button("Click me",
                event -> {
                    System.out.println("Clicked");
                    Notification.show("Clicked!");
                });
        add(button);
    }
}