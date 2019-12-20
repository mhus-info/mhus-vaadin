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
package de.mhus.lib.vaadin.widgets.desktop;

import java.util.List;
import java.util.Locale;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.mhus.lib.core.MLog;
import de.mhus.lib.core.util.MNls;
import de.mhus.lib.core.util.Pair;

public class NlsIFrameHelpContext extends MLog implements HelpContext {

	private MNls nls;
	private HelpPanel panel;

	public NlsIFrameHelpContext(Object owner, Locale locale) {
		this(MNls.lookup(owner));
	}
	
	public NlsIFrameHelpContext(MNls nls) {
		this.nls = nls;
	}
	
	@Override
	public synchronized Component getComponent() {
		if (panel == null) {
			panel = new HelpPanel();
			showHelpTopic(null);
		}
		return panel;
	}

	@Override
	public void showHelpTopic(String topic) {
		String url = null;
		if (topic == null)
			url = MNls.find(nls, "help.main.url");
		else
			url = MNls.find(nls, "help." + topic + ".url");
		if (url != null) {
			getComponent();
			panel.setContent(url);
		}
	}

	private class HelpPanel extends VerticalLayout {
		
		private static final long serialVersionUID = 1L;
		private IFrame iframe;

		public HelpPanel() {
			iframe = new IFrame();
			add(iframe);
			iframe.setSizeFull();
			setSizeFull();
		}

		public void setContent(String url) {
			iframe.setSrc(url);
		}
	}

	@Override
	public List<Pair<String, String>> searchTopics(String search) {
		return null;
	}

	@Override
	public List<Pair<String, String>> getIndex() {
		return null;
	}

}
