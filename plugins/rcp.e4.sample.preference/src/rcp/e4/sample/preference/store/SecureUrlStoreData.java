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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.crypto.spec.PBEKeySpec;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.equinox.security.storage.provider.IProviderHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import rcp.e4.sample.core.exception.SystemException;

/**
 * SecureUrlStoreData
 *
 * @author ejpark
 *
 */
public class SecureUrlStoreData {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecureUrlStoreData.class);
	private ISecurePreferences securePreferences;
	private Gson gson;

	/**
	 * SecureUrlStoreData
	 *
	 * @param preferenceURL
	 */
	public SecureUrlStoreData(String preferenceURL) {
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(IProviderHints.DEFAULT_PASSWORD, new PBEKeySpec(preferenceURL.toCharArray()));
		try {
			LOGGER.debug("initialize.");
			this.securePreferences = SecurePreferencesFactory.open(PreferenceStoredData.getPreferenceURL(preferenceURL),
					options);
		} catch (IOException e) {
			throw new SystemException(e);
		}
		this.gson = new Gson();
	}

	/**
	 * addData
	 *
	 * @param name
	 * @param data
	 */
	public void addData(String name, Object data) {
		LOGGER.debug("addData:{}", data);
		try {
			this.securePreferences.put(name, getGsonData(data), true);
			this.securePreferences.flush();
		} catch (StorageException e) {
			throw new SystemException(e);
		} catch (IOException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * removeData
	 *
	 * @param name
	 */
	public void removeData(String name) {
		this.securePreferences.remove(name);
	}

	/**
	 * getData
	 *
	 * @param name
	 * @param classType
	 * @return Data
	 */
	public Object getData(String name, Class<?> classType) {
		String dataJson = "";
		try {
			dataJson = this.securePreferences.get(name, "");
		} catch (StorageException e) {
			throw new SystemException(e);
		}
		return getDataFromJson(dataJson, classType);
	}

	/**
	 * getNames
	 *
	 * @return Names
	 */
	public String[] getNames() {
		String[] keys = this.securePreferences.keys();
		Arrays.sort(keys);
		return keys;
	}

	/**
	 * @param data
	 * @return
	 */
	private String getGsonData(Object data) {
		return this.gson.toJson(data);
	}

	/**
	 * @param dataJsonString
	 * @param classType
	 * @return
	 */
	private Object getDataFromJson(String dataJsonString, Class<?> classType) {
		return this.gson.fromJson(dataJsonString, classType);
	}
}
