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
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.controllers.TextAreaController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.tables.TableFactory.TableType;
import com.anna.service.DataBaseService;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author igor
 */
public class EventsTable extends SimpleTable<Event>
{
    private AbstractController  listDataTableController,
                                descriptionController;
    
    public EventsTable(TableFactory.TableType id, String name) 
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
                case "occupationList":  ButtonCommand command_1 = new ListButtonCommand(DataLoader.getLangResources().getString("key.occupation.title"));
                                        command_1.setController(ControllerFactory.getInstance().create(ControllerFactory.ControllerType.INTERNAL_OCCUPATIONS));
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_1)));
                                        break;
                    
                case "description":     ButtonCommand command_2 = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.event.description"), String.class);
                                        command_2.setController(ControllerFactory.getInstance().create(ControllerFactory.ControllerType.TEXT_AREA));
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_2)));
                                        break;
                    
                case "personList":      ButtonCommand command_3 = new ListButtonCommand(DataLoader.getLangResources().getString("key.persons.title"));
                                        AbstractController controller = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.PEOPLE);
                                        AbstractTable table = TableFactory.create(TableType.PERSONS_FLP);
                                        controller.setTable(table);
                                        command_3.setController(controller);
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_3)));
                                        break;
                    
                case "hobbyList":       ButtonCommand command_4 = new ListButtonCommand(DataLoader.getLangResources().getString("key.event.hobbies"));
                                        command_4.setController(ControllerFactory.getInstance().create(ControllerFactory.ControllerType.INTERNAL_HOBBIES));
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_4)));
                                        break;
                    
                default:                tc.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
                                        break;
            }
            tableView.getColumns().add(tc);
        }
    } 
}