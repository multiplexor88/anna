/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.interfaces;

import com.anna.gui.tables.TableFactory.TableType;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Contains all types that need for table constructing;
 * Runs basic creation
 * @author igor
 */
abstract public class AbstractTable<T> 
{
    protected String[]                              columnNameArr               = null;
    protected String[]                              fieldIdArr                  = null;
    
    protected Collection<T>                         inputDataList;
    
    protected ObservableList<T>                     tableDataList               = FXCollections.<T>observableArrayList();
    
    protected String                                tableName;/*title*/
    
    protected TableType                             tableId;/*id*/
    
    protected TableView                             tableView                   = new TableView();
    
    public AbstractTable()
    {
    }

    public String getTableName() {
        return tableName;
    }

    public TableType getTableId() {
        return tableId;
    }
        
    /**
     * Basic table creation from class outside
     * @param dataList
     * @param columnNameArr
     * @param fieldIdArr
     * @return 
     */
    public TableView create(Collection<T> dataList, String[]columnNameArr, String[] fieldIdArr)
    {
        this.columnNameArr = columnNameArr;
        this.fieldIdArr = fieldIdArr;
        
        return create(dataList);
    }
    
    /**
     * Basic table creation from class inside
     * @param dataList
     * @return 
     */
    public TableView create(Collection<T> dataList)
    {
        this.inputDataList = dataList;
        
        createAndSetColumns();
        createAndSetRows();
        
        return tableView;
    }
    
    abstract protected void createAndSetColumns();
    
    /*Can be Lazy*/
    public void createAndSetRows(){}

    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
        this.tableView.setEditable(false);
    }
}
