package de.mhus.lib.vaadin.desktop;

import de.mhus.lib.core.aaa.Aaa;
import de.mhus.lib.core.security.AccessControl;

public abstract class AaaGuiSpace extends SimpleGuiSpace {

    @Override
    public boolean hasAccess(AccessControl control) {
        return Aaa.hasAccess(GuiSpaceService.class.getCanonicalName() + ":view:" + getName() );
    }

}
