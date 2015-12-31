/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.gui.interfaces.ButtonCommand;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

/**
 *
 * @author igor
 */
public class SendMessageButtonTableCell<S,T> extends TableCell<S, T>
{
    private Button                            button                            = new Button("...");
    private ButtonCommand                     command;
    
    public SendMessageButtonTableCell(ButtonCommand command)
    {
        this.command = command;
    }  
    
    @Override
    protected void updateItem(T item, boolean empty) 
    {
        if(!empty)
        {
            super.updateItem(item, empty);
            button.setId("tableButton");
            setGraphic(button);
            button.setOnAction((ActionEvent event) -> 
            {
                if(getTableRow().getIndex() < 0 || getTableRow().getIndex() >= getTableView().getItems().size())
                    return;

                S s = (S)getTableRow().getItem();
                command.setData(s);
                command.execute(event); 
            });
        }
    }
}