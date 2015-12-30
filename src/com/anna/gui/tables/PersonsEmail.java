/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.data.Contact;
import com.anna.data.Person;
import com.anna.gui.commands.ListButtonCommand;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.ControllerFactory;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.ButtonCommand;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author igor
 */
public class PersonsEmail extends SimpleTable<T>
{
    /**
     * Create person list table
     * @param title
     * @param id: table id
     */
    public PersonsEmail(TableFactory.TableType id, String title) 
    {
        tableName = title;
        tableId = id;
    }
    
    @Override
    protected void createAndSetColumns() 
    {
        double sz = columnNameArr.length;
        
        for(int i = 0; i < sz; ++i)
        {
            TableColumn tc = new TableColumn<>(columnNameArr[i]);
            
            tc.setCellValueFactory(new PropertyValueFactory<>(fieldIdArr[i]));
            
            switch(fieldIdArr[i])
            {
                case "occupationList":  ButtonCommand command_1 = new ListButtonCommand(columnNameArr[i]);
                                        command_1.setController(ControllerFactory.getInstance().create(ControllerFactory.ControllerType.INTERNAL_OCCUPATIONS));
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_1)));
                                        break;
                    
                default:                tc.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
                                        break;
            }
            /**
             * Add listener for listening cell value changing (deprecated)
             */
            /*
            tc.setOnEditCommit(e->{
                try {
                    setOnEditCommitTextCellProcess(e);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(Person_FullTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            */
            tableView.getColumns().add(tc);
        }
    }       
}
