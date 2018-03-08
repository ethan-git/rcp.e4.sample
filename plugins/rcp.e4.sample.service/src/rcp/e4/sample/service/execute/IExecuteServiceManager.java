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

package rcp.e4.sample.service.execute;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * IExecuteServiceManager
 *
 * @author ejpark
 *
 */
public interface IExecuteServiceManager {

	/**
	 * execute Runnable
	 *
	 * @param service
	 * @return Future
	 */
	public Future<?> execute(Runnable service);
	
	/**
	 * execute Callable
	 *
	 * @param service
	 * @return Future
	 */
	public Future<?> execute(Callable<?> service);

	/**
	 * finish
	 */
	public void finish();

}