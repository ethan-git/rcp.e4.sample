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

package rcp.e4.sample.service.query.ui;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.eclipse.e4.ui.di.UISynchronize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rcp.e4.sample.service.execute.IExecuteServiceManager;
import rcp.e4.sample.service.execute.ServiceManagerFactory;

/**
 * ExecuteServiceView
 *
 * @author ejpark
 *
 */
public abstract class ExecuteServiceView {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteServiceView.class);
	private IExecuteServiceManager executeServiceManager;

	@Inject
	protected UISynchronize uiSync;

	public ExecuteServiceView() {
		this.executeServiceManager = ServiceManagerFactory.getInstance().getExecuteServiceManager();		
	}

	protected Object executeService(Callable<?> service){
		Object result = null;
		try {
			result = this.executeServiceManager.execute(service).get();
		} catch (InterruptedException e) {
			LOGGER.debug("InterruptedException:{}", e);
			error(e);
		} catch (ExecutionException e) {
			LOGGER.debug("ExecutionException:{}", e);
			error(e);
		}
		return result;
	}

	public abstract void error(Throwable cause);
}
