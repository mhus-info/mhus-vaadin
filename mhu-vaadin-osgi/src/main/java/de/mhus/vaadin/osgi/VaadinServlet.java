package de.mhus.vaadin.osgi;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;

import com.vaadin.flow.server.VaadinServletConfiguration;


@Component(service=Servlet.class,property= "alias=/hello",servicefactory=true)
public class VaadinServlet extends com.vaadin.flow.server.VaadinServlet {

    private static final long serialVersionUID = 1L;

    
    
}
