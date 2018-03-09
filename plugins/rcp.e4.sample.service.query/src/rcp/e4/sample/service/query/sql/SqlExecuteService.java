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

package rcp.e4.sample.service.query.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rcp.e4.sample.connection.pool.ConnectionPoolFactory;
import rcp.e4.sample.connection.pool.IConnectionPool;

/**
 * SqlExecuteService
 *
 * @author ejpark
 *
 */
public class SqlExecuteService implements Callable<ResultSetHandler> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlExecuteService.class);

	private IConnectionPool connectionPool;
	private String sql;

	/**
	 * SqlExecuteService
	 *
	 */
	public SqlExecuteService() {
		this.connectionPool = ConnectionPoolFactory.getInstance().getConnectionPool();
	}

	/**
	 * @param sql
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public ResultSetHandler call() throws Exception {
		LOGGER.debug("sql:{}", sql);
		Connection connection = this.connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = preparedStatement.executeQuery();
		ResultSetHandler resultSetHandler = new ResultSetHandler(connection, preparedStatement, resultSet);
		return resultSetHandler;
	}
}
