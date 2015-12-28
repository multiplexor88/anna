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
import com.anna.gui.controllers.AddressController;
import com.anna.gui.controllers.ControllerFactory;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.tables.TableFactory.TableType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author igor
 */
public class PersonsTable<T> extends SimpleTable<T>
{
    /**
     * Create person list table
     * @param title
     * @param id: table id
     */
    public PersonsTable(TableType id, String title) 
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
                    
                case "addressList":     ButtonCommand command_2 = new ListButtonCommand(columnNameArr[i]);
                                        AbstractController addressController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.ADDRESSES);
                                        ((ListDataTableController)addressController).getSearchTxt().setDisable(true);
                                        command_2.setController(addressController);
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_2)));
                                        break;
                    
                case "contact":         ButtonCommand command_3 = new SimpleButtonCommand(columnNameArr[i], Contact.class);
                                        command_3.setController(ControllerFactory.getInstance().create(ControllerFactory.ControllerType.CONTACT));
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_3)));
                                        break;
                    
                case "hobbyList":       ButtonCommand command_4 = new ListButtonCommand(columnNameArr[i]);
                                        AbstractController hobbyListController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.INTERNAL_HOBBIES);
                                        command_4.setController(hobbyListController);
                                        tc.setCellFactory(e->(new ButtonTableCell<>(command_4)));
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
