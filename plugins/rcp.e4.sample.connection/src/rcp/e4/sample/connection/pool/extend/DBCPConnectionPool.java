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

package rcp.e4.sample.connection.pool.extend;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import rcp.e4.sample.connection.data.ConnectionInfo;
import rcp.e4.sample.connection.pool.AbstractConnectionPool;

/**
 * DBCPConnectionPool
 *
 * @author ejpark
 *
 */
public class DBCPConnectionPool extends AbstractConnectionPool {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * rcp.e4.sample.connection.pool.AbstractConnectionPool#getDataSource(rcp.e4.
	 * sample.connection.data.ConnectionInfo)
	 */
	@Override
	public DataSource getDataSource(ConnectionInfo connectionInfo) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(connectionInfo.getDbmsType().getDriverClassName());
		dataSource.setUsername(connectionInfo.getUser());
		dataSource.setPassword(connectionInfo.getPassword());
		dataSource.setUrl(connectionInfo.getJdbcUrl());
		dataSource.setInitialSize(getInitConnCount());
		dataSource.setMaxActive(getMaxConnCount());
		dataSource.setMaxIdle(getMaxConnCount());
		dataSource.setMaxWait(getMaxWait());
		dataSource.setValidationQuery(connectionInfo.getDbmsType().getValidationQuery());
		return dataSource;
	}
}
