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

package rcp.e4.sample.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import rcp.e4.sample.connection.data.ConnectionInfo;
import rcp.e4.sample.core.exception.SystemException;

/**
 * AbstractConnectionPool
 *
 * @author ejpark
 *
 */
public abstract class AbstractConnectionPool implements IConnectionPool, IConnectionInitializer {

	private DataSource dataSource;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * rcp.e4.sample.connection.pool.IConnectionInitializer#initialize(rcp.e4.sample
	 * .connection.data.ConnectionInfo)
	 */
	@Override
	public void initialize(ConnectionInfo connectionInfo) {
		this.dataSource = getDataSource(connectionInfo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see rcp.e4.sample.connection.pool.IConnectionPool#getConnection()
	 */
	@Override
	public Connection getConnection() {
		Connection connection = null;
		try {
			if (this.dataSource == null) {
				throw new NoDataSourceException("No DataSource.\nPlease, Check Connection Information.");
			}
			connection = this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new SystemException(e);
		}
		return connection;
	}

	/**
	 * getInitConnCount
	 *
	 * @return InitConnCount
	 */
	protected int getInitConnCount() {
		return ConnectionPoolConfig.INIT_CONN_CNT;
	}

	/**
	 * getMaxConnCount
	 *
	 * @return MaxConnCount
	 */
	protected int getMaxConnCount() {
		return ConnectionPoolConfig.MAX_CONN_CNT;
	}

	/**
	 * getMaxWait
	 *
	 * @return MaxWait
	 */
	protected long getMaxWait() {
		return ConnectionPoolConfig.MAX_WAIT;
	}

	/**
	 * getDataSource
	 *
	 * @param connectionInfo
	 * @return DataSource
	 */
	public abstract DataSource getDataSource(ConnectionInfo connectionInfo);
}
