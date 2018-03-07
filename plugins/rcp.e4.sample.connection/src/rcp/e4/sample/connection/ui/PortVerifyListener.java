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

package rcp.e4.sample.connection.ui;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

/**
 * PortVerifyListener
 * Port Range 0 to 65535
 *
 * @author ejpark
 *
 */
public class PortVerifyListener implements VerifyListener {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.swt.events.VerifyListener#verifyText(org.eclipse.swt.events.
	 * VerifyEvent)
	 */
	@Override
	public void verifyText(VerifyEvent e) {
		String currentText = ((Text) e.widget).getText();
		String port = currentText.substring(0, e.start) + e.text + currentText.substring(e.end);
		try {
			int portNum = Integer.valueOf(port);
			if (portNum < 0 || portNum > 65535) {
				e.doit = false;
			}
		} catch (NumberFormatException ex) {
			if (!port.equals("")) {
				e.doit = false;
			}
		}
	}
}
