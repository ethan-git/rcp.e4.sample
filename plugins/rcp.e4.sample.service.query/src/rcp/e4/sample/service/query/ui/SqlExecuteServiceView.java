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

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rcp.e4.sample.core.ui.viewer.ObjectArrayColumnLabelProvider;
import rcp.e4.sample.service.query.sql.ResultColumnMetaInfo;
import rcp.e4.sample.service.query.sql.ResultSetHandler;
import rcp.e4.sample.service.query.sql.SqlExecuteService;

/**
 * SqlExecuteView
 *
 * @author ejpark
 *
 */
public class SqlExecuteServiceView extends ExecuteServiceView {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlExecuteServiceView.class);
	private Text text;
	private TableViewer tableViewer;

	private SqlExecuteService sqlExecuteService;
	private ResultSetHandler resultSetHandler;

	public SqlExecuteServiceView() {
		this.sqlExecuteService = SqlExecuteService.getInstance();
	}

	@PostConstruct
	public void createComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		text = new Text(composite, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_text.heightHint = 150;
		gd_text.widthHint = 360;
		text.setLayoutData(gd_text);

		tableViewer = new TableViewer(composite, SWT.BORDER);
		Table table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		text.setText("select * from v$sql");
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.stateMask == SWT.CTRL && (event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR)) {
					event.doit = false;
					sqlExecuteService.setSql(text.getText());
					resultSetHandler = (ResultSetHandler) executeService(sqlExecuteService);
					initExecuteQuery();
				}
			}
		});

		ScrollBar scrollBar = tableViewer.getTable().getVerticalBar();
		scrollBar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (scrollBar.getSelection() + scrollBar.getThumb() == scrollBar.getMaximum()) {
					fetchRows();
				}
			}
		});
	}

	@Override
	public void error(Throwable cause) {
		LOGGER.debug("error:{}", cause);
	}

	private void initExecuteQuery() {
		this.uiSync.asyncExec(new Runnable() {

			@Override
			public void run() {
				removeTableColumns();
				createTalbeColumns();
				fetchRows();
			}
		});
	}

	private void fetchRows() {
		LOGGER.debug("fetchRows");
		Object[] rows = resultSetHandler.getFetchRows(100);
		tableViewer.add(rows);			
	}

	private void removeTableColumns() {
		LOGGER.debug("removeTableColumns");
		Table table = tableViewer.getTable();
		table.removeAll();
		for (int i = table.getColumnCount() - 1; i >= 0; i--) {
			table.getColumn(i).dispose();
		}
	}

	private void createTalbeColumns() {
		LOGGER.debug("createTalbeColumns");
		List<ResultColumnMetaInfo> resultColumnMetaInfoList = resultSetHandler.getResultColumnMetaInfoList();
		int columnCnt = resultColumnMetaInfoList.size();
		LOGGER.debug("resultColumnMetaInfoList:size:{}", columnCnt);
		for (int i = 0; i < columnCnt; i++) {
			ResultColumnMetaInfo resultColumnMetaInfo = resultColumnMetaInfoList.get(i);
			TableViewerColumn dpNameViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
			dpNameViewerColumn.getColumn().setAlignment(SWT.LEFT);
			dpNameViewerColumn.getColumn().setWidth(200);
			dpNameViewerColumn.getColumn().setText(resultColumnMetaInfo.getLabel());
			dpNameViewerColumn.setLabelProvider(new ObjectArrayColumnLabelProvider(i));
		}
	}
}
