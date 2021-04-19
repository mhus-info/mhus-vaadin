package de.mhus.osgi.vaadin.desktop;

import org.osgi.framework.BundleContext;

import de.mhus.lib.vaadin.desktop.DesktopApi;

public interface InternalDesktopApi extends DesktopApi {

    BundleContext getContext();

}
