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

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

/**
 * WebView
 * 
 * @author ejpark
 *
 */
public class WebView {
	
	private Browser browser;
	
	@PostConstruct
	public void createComposite(Composite parent) {
		this.browser = new Browser(parent, SWT.NONE);
//		browser.setUrl("www.google.com");
//		this.browser.setText("<html><body><h1>Hello</h1></body></html>");
		String baseUrl = Platform.getInstanceLocation().getURL().getPath();
		browser.setUrl(String.format("%s/%s", baseUrl, "script/index.html"));
	}
}
