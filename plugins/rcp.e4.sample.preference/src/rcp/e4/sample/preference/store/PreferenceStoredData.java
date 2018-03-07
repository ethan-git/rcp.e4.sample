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

package rcp.e4.sample.preference.store;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/**
 * PreferenceStoredData
 *
 * @author ejpark
 *
 */
public class PreferenceStoredData {

	/**
	 * getPreferenceURL
	 *
	 * @param nodeName
	 * @return URL
	 */
	public static URL getPreferenceURL(String nodeName) {
		IEclipsePreferences preferences = ConfigurationScope.INSTANCE.getNode(nodeName);
		String confiurationPath = String.format("%s%s", Platform.getConfigurationLocation().getURL().getPath(),
				preferences.absolutePath());
		File file = new File(confiurationPath);
		URL url = null;
		try {
			url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return url;
	}
}
