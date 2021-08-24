package de.mhus.osgi.vaadin.desktop;

import java.util.Date;
import java.util.UUID;

import org.apache.shiro.subject.Subject;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.MenuBar.MenuItem;

import de.mhus.lib.core.MDate;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.MSystem;
import de.mhus.lib.core.aaa.Aaa;
import de.mhus.lib.core.aaa.SubjectEnvironment;
import de.mhus.lib.core.cfg.CfgBoolean;
import de.mhus.lib.core.logging.ITracer;
import de.mhus.lib.core.util.MNls;
import de.mhus.lib.core.util.MNlsProvider;
import de.mhus.lib.vaadin.InfoDialog;
import de.mhus.lib.vaadin.TextInputDialog;
import de.mhus.lib.vaadin.desktop.Desktop;
import de.mhus.lib.vaadin.desktop.DesktopApi;
import de.mhus.lib.vaadin.desktop.GuiApi;
import io.opentracing.Scope;

public class DesktopUiDesktop extends Desktop {

    public DesktopUiDesktop(GuiApi api) {
        super(api);
    }

    private static CfgBoolean CFG_GEEK_MODE = new CfgBoolean(DesktopApi.class, "geek", false);

    private static final long serialVersionUID = 1L;
    private MenuItem menuTrace;
    //          private Refresher refresher;
    private MenuItem menuDoAs;

    private String tracerId = null;

    @SuppressWarnings("deprecation")
    @Override
    protected void initGui() {
        super.initGui();

        if (CFG_GEEK_MODE.value()) {
            String part = UI.getCurrent().getPage().getUriFragment();
            Date now = new Date();
            if ("geek_easter".equals(part)
                    || now.getMonth() == 3
                            && (now.getDate() >= 10 && now.getDate() <= 17))
                addStyleName("desktop-easter");
            if ("geek_towel".equals(part)
                    || now.getMonth() == 4 && now.getDate() == 25)
                addStyleName("desktop-towel");
            if ("geek_yoda".equals(part)
                    || now.getMonth() == 4 && now.getDate() == 21)
                addStyleName("desktop-yoda");
            if ("geek_pirate".equals(part)
                    || now.getMonth() == 8 && now.getDate() == 19)
                addStyleName("desktop-pirate");
            if ("geek_suit".equals(part)
                    || now.getMonth() == 9 && now.getDate() == 13)
                addStyleName("desktop-suit");
        }

        //              refresher = new Refresher();
        //              refresher.setRefreshInterval(1000);
        //              refresher.addListener(new Refresher.RefreshListener() {
        //                  private static final long serialVersionUID = 1L;
        //                  @Override
        //                  public void refresh(Refresher source) {
        //                      doTick();
        //                  }
        //              });
        //              addExtension(refresher);


    }

    public String getTracerId() {
        return tracerId;
    }

    public void setTracing(boolean activate) {
        if (activate) {
            this.tracerId = UUID.randomUUID().toString().substring(30,36);
            try (Scope scope2 =
                    ITracer.get().enter("tracing " + Aaa.getPrincipal(),tracerId,"id",tracerId) ) {
            }
        } else {
            this.tracerId = null;
        }
    }

    public void refreshMenu() {

        final MNlsProvider nlsProvider = this;
        if (Aaa.hasAccess(Desktop.class,"action.trace",null)) {
            if (menuTrace == null) {
                menuTrace =
                        menuUser.addItem(
                                MNls.find(nlsProvider, "menu.startTrace=Start trace"),
                                new MenuBar.Command() {
                                    private static final long serialVersionUID = 1L;
    
                                    @Override
                                    public void menuSelected(MenuItem selectedItem) {
                                        if (getTracerId() == null) {
                                            setTracing(true);
                                            menuTrace.setText(
                                                    MNls.find(nlsProvider, "menu.stopTrace=Stop trace") + " (" + getTracerId() + ")");
                                            InfoDialog.show(getUI(),
                                                    MNls.find(nlsProvider, 
                                                            "menu.traceInfoTitle=Trace information"),
                                                            tracerId + "," + MDate.toIsoDateTime(new Date()) + "," + MSystem.getHostname());
                                        } else {
                                            setTracing(false);
                                            menuTrace.setText(MNls.find(nlsProvider, "menu.startTrace=Start trace"));
                                        }
                                    }
                                });
            } else
                menuTrace.setEnabled(true);
        } else
        if (menuTrace != null)
            menuTrace.setEnabled(false);
        
        
        if (Aaa.hasAccess(Desktop.class,"action.doas",null)) {
            if (menuDoAs == null) {
                menuDoAs = menuUser.addItem(
                        MNls.find(nlsProvider, "menu.doAs=Do as"),
                        new MenuBar.Command() {
                            private static final long serialVersionUID = 1L;
    
                            @Override
                            public void menuSelected(MenuItem selectedItem) {
                                TextInputDialog.show(getUI(), 
                                        MNls.find(nlsProvider, "menu.doAsTitle=Do as"),
                                        "",
                                        MNls.find(nlsProvider, "menu.doAsText=Name of the user"),
                                        MNls.find(nlsProvider, "menu.doAsOk=Ok"),
                                        MNls.find(nlsProvider, "menu.doAsCancel=Cancel"),
                                        new TextInputDialog.Listener() {
    
                                            @Override
                                            public boolean validate(String txtInput) {
                                                return MString.isSetTrim(txtInput);
                                            }
    
                                            @Override
                                            public void onClose(TextInputDialog dialog) {
                                                if (dialog.isConfirmed()) {
                                                    // it's a hack
                                                    String username = dialog.getInputText().trim();
                                                    try (Scope scope2 =
                                                            ITracer.get().enter("doas " + Aaa.getPrincipal(),tracerId,"id",tracerId,"username",username) ) {
                                                        Subject subject = Aaa.createSubjectWithoutCheck( username );
                                                        getSession().setAttribute(VaadinAccessControl.ATTR_SUBJECT, subject);
                                                        getSession().setAttribute(VaadinAccessControl.ATTR_NAME, username);
                                                        DesktopUi.subjectSet(getSession());
                                                        try (SubjectEnvironment env = Aaa.asSubject(subject)) {
                                                            refreshSpaceList();
                                                            refreshMenu();
                                                            showOverview(true);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        );
                            }
                        }
                        );
            } else
                menuDoAs.setEnabled(true);
        } else
            menuDoAs.setEnabled(false);
    }

}
