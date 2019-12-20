/**
 * Copyright 2018 Mike Hummel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mhus.lib.vaadin.widgets.login;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import de.mhus.lib.core.util.MNls;
import de.mhus.lib.core.util.MNlsProvider;
import de.mhus.lib.vaadin.widgets.VLabel;
import de.mhus.lib.vaadin.widgets.ui.HelpManager;

@SuppressWarnings("deprecation")
public class LoginPanel extends VerticalLayout implements MNlsProvider {

	private static final long serialVersionUID = 1L;
	private HelpManager helpManager;
	private Listener listener;
	private MNls nls;
	private VLabel error;
	
	public LoginPanel() {
	    addAttachListener(new ComponentEventListener<AttachEvent>() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onComponentEvent(AttachEvent event) {
                doContent();
            }
        });
	}
	
	protected void doContent() {
				
		helpManager = new HelpManager(getUI().get());
        helpManager.closeAll();
        
        
		String welcomeTxt = MNls.find(this, "login.help.title");
		if (welcomeTxt != null) {
			helpManager.showHelp(
							welcomeTxt,
	                		MNls.find(this, "login.help.description"),
	                        "login");
		}
		
        addClassName("login");

        VerticalLayout loginLayout = this;
        loginLayout.setSizeFull();
        loginLayout.addClassName("login-layout");
        loginLayout.setWidth("450px");

        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.addClassName("login-panel");

        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addClassName("labels");
        loginPanel.add(labels);

        Label welcome = new Label(MNls.find(this, "login.welcome=Welcome"));
        welcome.setSizeUndefined();
        welcome.addClassName("h4");
        labels.add(welcome);
        //XXX labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

        Label title = new Label(MNls.find(this, "login.title="));
        title.setSizeUndefined();
        title.addClassName("h2");
        title.addClassName("light");
        labels.add(title);
        // labels.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addClassName("fields");

        final TextField username = new TextField(MNls.find(this, "login.username=Username"));
        username.focus();
        fields.add(username);

        final PasswordField password = new PasswordField(MNls.find(this, "login.password=Password"));
        fields.add(password);

        final Button signin = new Button(MNls.find(this, "login.signin=Sign In"));
        signin.addClassName("default");
        fields.add(signin);
        //XXX fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        error = new VLabel();
        error.addClassName("error");
        error.setSizeUndefined();
        error.addClassName("light");
        // Add animation
        error.addClassName("v-animate-reveal");
        loginPanel.add(error);
        //XXX loginPanel.setComponentAlignment(error, Alignment.MIDDLE_CENTER);

//        final ShortcutListener enter = new ShortcutListener(MNls.find(this, "login.signin=Sign In"),
//                KeyCode.ENTER, null) {
//					private static final long serialVersionUID = 1L;
//
//			@Override
//            public void handleAction(Object sender, Object target) {
//                signin.click();
//            }
//        };

        signin.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                
                if ("".equals(password.getValue())) {
                    password.focus();
                    return;
                }
                
                error.setText("");
                
                if (listener != null && listener.doLogin(username.getValue(),password.getValue())) {
                   //  signin.removeShortcutListener(enter);
                } else {
                    // Add new error message
                    error.setText(MNls.find(LoginPanel.this, "login.error=Wrong username or password."));
                    username.focus();
                }
                
            }
        });

//        signin.addShortcutListener(enter);

        loginPanel.add(fields);

        loginLayout.add(loginPanel);
//XXX        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		
        doCustomize(loginPanel, labels, fields);
	}
	
	protected void doCustomize(VerticalLayout loginPanel, HorizontalLayout labels,
			HorizontalLayout fields) {
		
	}

	public Listener getListener() {
		return listener;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	@Override
	public MNls getNls() {
		return nls;
	}

	public void setNls(MNls nls) {
		this.nls = nls;
	}

	public static interface Listener {

		public boolean doLogin(String username, String password);
		
	}
}
