package de.mhus.vaadin.osgi;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.osgi.service.component.annotations.Component;

@Component(service=Servlet.class,property= "alias=/hello",servicefactory=true)
public class VaadinServlet extends com.vaadin.flow.server.VaadinServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        getService().setClassLoader(getClass().getClassLoader());
    }
}
