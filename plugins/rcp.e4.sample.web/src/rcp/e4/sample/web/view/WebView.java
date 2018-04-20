/**
  * Copyright 2017 ethan.park
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

package rcp.e4.sample.web.view;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rcp.e4.sample.core.exception.SystemException;
import rcp.e4.sample.web.view.func.BrowserCommonFunction;

/**
 * WebView
 * 
 * @author ejpark
 *
 */
public class WebView {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebView.class);

	private Browser browser;
	private BrowserFunction function;
	
	@PostConstruct
	public void createComposite(Composite parent) {
		LOGGER.debug("createComposite");
		this.browser = new Browser(parent, SWT.NONE);
//		browser.setUrl("www.google.com");
//		this.browser.setText("<html><body><h1>Hello</h1></body></html>");
		URL url = FrameworkUtil.getBundle(this.getClass()).getEntry("app/index.html");
		try {
			URL localurl = FileLocator.toFileURL(url);
			this.browser.setUrl(localurl.getPath());
		} catch (IOException e) {
			throw new SystemException(e);
		}
		
		this.function = new BrowserCommonFunction(this.browser, "callService");
	}
	
}
