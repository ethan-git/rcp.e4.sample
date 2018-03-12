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

package rcp.e4.sample.core.exception;

/**
 * SystemException
 *
 * @author ejpark
 *
 */
public class SystemException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 413809336726619333L;

	/**
	 * SystemException
	 *
	 * @param cause
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}

	/**
	 * SystemException
	 *
	 * @param message
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * SystemException
	 *
	 * @param message
	 * @param cause
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
