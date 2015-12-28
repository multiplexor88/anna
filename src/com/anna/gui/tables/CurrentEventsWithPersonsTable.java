/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.data.Event;
import com.anna.data.Person;
import com.anna.gui.commands.ListButtonCommand;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.ControllerFactory;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.strategies.PeopleTableSearchStrategy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author igor
 */
public class CurrentEventsWithPersonsTable extends SimpleTable<Event>
{
    public CurrentEventsWithPersonsTable(TableFactory.TableType id, String name) 
    {
        tableName = name;
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
                case "name":            tc.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
                                        break;
                    
                case "description":     ButtonCommand command_2 = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.event.description"), String.class);
                                        command_2.setController(ControllerFactory.getInstance().create(ControllerFactory.ControllerType.TEXT_AREA));
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_2)));
                                        break;
                    
                case "personList":      ButtonCommand command_3 = new ListButtonCommand(DataLoader.getLangResources().getString("key.persons.title"));
                                        AbstractController peopleController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.ADD_PERSONS_TO_EVENT);
                                        peopleController.setStrategy(new PeopleTableSearchStrategy());
                                        command_3.setController(peopleController);
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_3)));
                                        break;
            }
            tableView.getColumns().add(tc);
        }
    } 
}