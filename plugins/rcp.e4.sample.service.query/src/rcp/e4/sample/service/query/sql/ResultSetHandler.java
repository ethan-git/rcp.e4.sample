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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rcp.e4.sample.core.exception.SystemException;

/**
 * ResultSetHandler
 *
 * @author ejpark
 *
 */
public class ResultSetHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResultSetHandler.class);

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private int currentRowIndex;
	private int totalRowCnt;
	private int columnCnt;
	private List<ResultColumnMetaInfo> resultColumnMetaInfoList;

	/**
	 * ResultSetHandler
	 *
	 * @param connection
	 * @param statement
	 * @param resultSet
	 */
	public ResultSetHandler(Connection connection, Statement statement, ResultSet resultSet) {
		this.connection = connection;
		this.statement = statement;
		this.resultSet = resultSet;
		this.currentRowIndex = 0;
		initialize();
	}

	private void initialize() {
		try {
			this.resultColumnMetaInfoList = new ArrayList<ResultColumnMetaInfo>();
			ResultSetMetaData resultSetMetaData = this.resultSet.getMetaData();
			this.columnCnt = resultSetMetaData.getColumnCount();
			for (int i = 0; i < this.columnCnt; i++) {
				ResultColumnMetaInfo resultColumnMetaInfo = new ResultColumnMetaInfo();
				resultColumnMetaInfo.setLabel(resultSetMetaData.getColumnLabel(i + 1));
				this.resultColumnMetaInfoList.add(resultColumnMetaInfo);
			}
			this.resultSet.last();
			this.totalRowCnt = this.resultSet.getRow();
			this.resultSet.beforeFirst();
			LOGGER.debug("columnCnt:{}, totalRowCnt:{}", this.columnCnt, this.totalRowCnt);
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * getFetchRows
	 *
	 * @param fetchSize
	 * @return rows
	 */
	public Object[] getFetchRows(int fetchSize) {

		// all rows fetched.
		if (this.currentRowIndex == this.totalRowCnt) {
			LOGGER.debug("all rows fetched.[currentRowIndex:{}, totalRowCnt:{}]", this.currentRowIndex,
					this.totalRowCnt);
			return new Object[0];
		}

		int rowSize = fetchSize;

		// set row size for last block.
		if (this.currentRowIndex + fetchSize > this.totalRowCnt) {
			rowSize = this.totalRowCnt - this.currentRowIndex;
		}
		Object[] rows = new Object[rowSize];

		try {
			// set position current row index.
			this.resultSet.absolute(this.currentRowIndex);
			this.resultSet.setFetchSize(fetchSize);

			int rowIndex = 0;
			while (rowIndex < rowSize && this.resultSet.next()) {
				this.currentRowIndex++;
				Object[] row = new Object[this.columnCnt];
				for (int i = 0; i < this.columnCnt; i++) {
					row[i] = this.resultSet.getObject(i + 1);
				}
				rows[rowIndex]=row;
				rowIndex++;
			}

			// all rows fetched.
			if (this.currentRowIndex == this.totalRowCnt) {
				closeQuietly();
			}
		} catch (SQLException e) {
			throw new SystemException(e);
		}
		return rows;
	}

	/**
	 * getResultColumnMetaInfoList
	 *
	 * @return resultColumnMetaInfoList
	 */
	public List<ResultColumnMetaInfo> getResultColumnMetaInfoList() {
		return this.resultColumnMetaInfoList;
	}

	/**
	 * closeQuietly
	 */
	public void closeQuietly() {
		LOGGER.debug("closeQuietly.");
		try {
			if (this.statement != null) {
				this.statement.close();
			}
			if (this.connection != null) {
				this.connection.close();
			}
		} catch (SQLException e) {
			// Ignore.
			LOGGER.debug("closeQuietly.[Error:{}]", e.getMessage());
		}
	}
}
