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

package rcp.e4.sample.web.view.func;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ejpark
 *
 */
public class BrowserCommonFunction extends BrowserFunction {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrowserCommonFunction.class);
	
	public BrowserCommonFunction(Browser browser, String name) {
		super(browser, name);
		LOGGER.debug("{}", name);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.browser.BrowserFunction#function(java.lang.Object[])
	 */
	public Object function (Object[] arguments) {
		LOGGER.debug("function called. [arguments] {}", arguments);
		String returnValue = arguments[0].toString()+"===Return";
		LOGGER.debug("return {}", returnValue);
		this.getBrowser().execute("callback('"+returnValue+"');");
		return null;
	}
}
