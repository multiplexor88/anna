/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.gui.interfaces.AbstractTable;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.MapValueFactory;

/**
 * If data of a row consists different objects or parts of objects, than can be used for 
 * table creation
 * Columns are created by standard procedure using MapValueFactory
 * Rows creation must be overrided by user according to used object's fields
 * @Deprecated
 * @author igor
 */
abstract public class ComplexTable<T> extends AbstractTable<T>
{
    protected ObservableList<Map<String, Object>>   items                       = FXCollections.<Map<String, Object>>observableArrayList();

    @Override
    protected void createAndSetColumns()
    {
        //EventHandler<TableColumn.CellEditEvent<Map, String>> standardCellHandler = e->setOnEditCommitTextCellProcess(e);
        double sz = columnNameArr.length;
        for(int i = 0; i < sz; ++i)
        {
            TableColumn<Map, String> tc = new TableColumn<>(columnNameArr[i]);
            tc.setCellValueFactory(new MapValueFactory<>(fieldIdArr[i]));
            
            //tc.setCellFactory(TextFieldTableCell.<Map>forTableColumn());
            //tc.setOnEditCommit(standardCellHandler);
            
            //tc.setPrefWidth(prefWidth);
            
            tableView.getColumns().add(tc);
        }
    }
}
