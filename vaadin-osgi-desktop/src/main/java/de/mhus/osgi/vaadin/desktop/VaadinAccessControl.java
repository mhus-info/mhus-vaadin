/**
 * Copyright 2018 Mike Hummel
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mhus.osgi.vaadin.desktop;

import org.apache.shiro.subject.Subject;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import de.mhus.lib.core.M;
import de.mhus.lib.core.MLog;
import de.mhus.lib.core.security.AccessControl;
import de.mhus.lib.core.security.Account;
import de.mhus.lib.core.shiro.ShiroAccount;
import de.mhus.lib.core.shiro.AccessApi;
import de.mhus.lib.core.shiro.AccessUtil;

public class VaadinAccessControl extends MLog implements AccessControl {

    public static final String ATTR_SUBJECT = "_access_subject";
    public static final String ATTR_NAME = "_access_name";
    public static final String ATTR_CONTEXT = "_access_context";
    private VaadinSession session;

    public VaadinAccessControl() {
        session = UI.getCurrent().getSession();
    }

    @Override
    public boolean hasGroup(String role) {
        Account acc = getAccount();
        if (acc == null) return false;
        return acc.hasGroup(role);
    }

    @Override
    public String getName() {
        String ret = (String) session.getAttribute(ATTR_NAME);
        if (ret == null) return "?";
        return ret;
    }

    @Override
    public boolean signIn(String username, String password) {

        try {
            Subject subject = M.l(AccessApi.class).createSubject();
            if (!AccessUtil.login(subject, username, password, true, session.getLocale()))
                return false;
            session.setAttribute(ATTR_SUBJECT, subject);
            session.setAttribute(ATTR_NAME, username);

            // need to set subject session NOW
            DesktopUi.subjectSet(session);

            return true;
        } catch (Throwable t) {
            log().w(username, t);
            return false;
        }
    }

    @Override
    public boolean isUserSignedIn() {
        return session.getAttribute(ATTR_SUBJECT) != null;
    }

    @Override
    public void signOut() {
        session.setAttribute(ATTR_NAME, null);
        Subject subject = (Subject) session.getAttribute(ATTR_SUBJECT);
        session.setAttribute(ATTR_SUBJECT, null);
        if (subject != null) {
            try {
                subject.logout();
            } catch (Throwable t) {
            }
        }
        // remove subject session
        DesktopUi.subjectRemove(session);
    }

    @Override
    public Account getAccount() {
        // String account = (String) session.getAttribute(ATTR_NAME);
        Subject subject = (Subject) session.getAttribute(ATTR_SUBJECT);
        if (subject == null) return null;
        return new ShiroAccount(subject);
    }

    public static String getUserName(VaadinSession session) {
        String ret = (String) session.getAttribute(ATTR_NAME);
        if (ret == null) return "?";
        return ret;
    }

    public static Account getUserAccount(VaadinSession session) {
        Subject subject = (Subject) session.getAttribute(ATTR_SUBJECT);
        if (subject == null) return null;
        return new ShiroAccount(subject);
    }
}
