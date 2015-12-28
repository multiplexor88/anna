/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.gui.interfaces.ButtonCommand;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;

/**
 *
 * @author igor
 */
public class ListViewTableCell <S,T> extends TableCell<S, T>
{
    private ListView                          listView                          = new ListView();
    private ButtonCommand                     command;
    
    public ListViewTableCell(ButtonCommand command)
    {
        this.command = command;
    }  
    
    @Override
    protected void updateItem(T item, boolean empty) 
    {
        if(!empty)
        {
            super.updateItem(item, empty);
            setGraphic(listView);
            listView.setItems(FXCollections.observableArrayList((List)item));
        }
    }
}
