/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.commands;

import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.interfaces.TableSearchStrategy;
import com.anna.gui.tables.TableFactory.TableType;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;

/**
 * Executes calling list table view
 * @author multiplexor88
 */
public class ListButtonCommand extends ButtonCommand
{
    private TableType tableId;
    
    public ListButtonCommand(/*TableType tableId, */String viewName) {
        this.viewName = viewName;
        /*this.tableId = tableId;*/
        
        /*Lazy table creation*/
    //    table = TableFactory.create(tableId);
    }

    @Override
    public void setData(Object data) {
        super.setData(data); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*controller must be set*/
    @Override
    public void execute(Event event) 
    {
        controller.showDialog(event, null);

        /*convert to ArrayList, because ButtonTableCell 
        represents ObservableList data*/
        List tmp = (List)controller.getData();
        
        if(tmp != null /*&& !tmp.isEmpty()*/)
        {
            data = new ArrayList();
            ((List)data).addAll(tmp);
            //controller.getTable().getTableView().setItems(FXCollections.observableArrayList(data));
        }
    }
}
