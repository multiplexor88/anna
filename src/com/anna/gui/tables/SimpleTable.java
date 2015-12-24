/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.tables.TableFactory.TableType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * If all data in a row represent entire object T, than can be used for table creation
 * Simple contains just common table cells. 
 * For creation special cells like button cells, inherit from SimpleTable class
 * @author igor
 */
public class SimpleTable<T> extends AbstractTable<T>
{
    public SimpleTable( TableType tabId, 
                        String tabName,
                        String[] columnNameArr,
                        String[] fieldIdArr)
    {
        this.tableName = tabName;
        this.tableId = tabId;
        this.columnNameArr = columnNameArr;
        this.fieldIdArr = fieldIdArr;
    }
    
    public SimpleTable() {
    }
    
    @Override
    protected void createAndSetColumns() 
    {
        double sz = columnNameArr.length;
        for(int i = 0; i < sz; ++i)
        {
            TableColumn<T, String> tc = new TableColumn<>(columnNameArr[i]);
            tc.setCellValueFactory(new PropertyValueFactory<>(fieldIdArr[i]));
            tableView.getColumns().add(tc);
        }
    }

    @Override
    public void createAndSetRows() 
    {
        if(inputDataList != null)
        {
            tableDataList.setAll(inputDataList);
            tableView.getItems().addAll(tableDataList);
        }
        else
            tableView.setItems(tableDataList);
    }
    
    /**
     * Changes value in the current cell (deprecated)
     * @param e
     * @throws InvocationTargetException 
     */
    protected void setOnEditCommitTextCellProcess(TableColumn.CellEditEvent<T, String> e) throws InvocationTargetException
    {
        //T o = e.getTableView().getSelectionModel().getSelectedItem();
        
        T obj = null;
        try 
        {
            //retrieve edited value
            obj = e.getRowValue();
            
            Object value = e.getNewValue();
            TableColumn tc = e.getTableColumn();
            final String propName = ((PropertyValueFactory)tc.getCellValueFactory()).getProperty();
            
            String methodName = "set" + propName.toUpperCase().charAt(0) + propName.substring(1);
            
            Method method = obj.getClass().getDeclaredMethod(methodName, value.getClass());

        } 
        catch (Exception ex) 
        {
            Logger.getLogger(SimpleTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //and update in localdatabase
    }
}
