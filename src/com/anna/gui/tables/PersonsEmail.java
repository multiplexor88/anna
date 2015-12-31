/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.data.Person;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.ControllerFactory;
import com.anna.gui.interfaces.ButtonCommand;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author igor
 */
public class PersonsEmail extends SimpleTable
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
        TableColumn tc1 = new TableColumn<>(columnNameArr[0]);
        tc1.setCellValueFactory(new PropertyValueFactory<>(fieldIdArr[0]));
        tc1.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        
        TableColumn tc2 = new TableColumn<>(columnNameArr[1]);
        tc2.setCellValueFactory(new PropertyValueFactory<>(fieldIdArr[1]));
        tc2.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        
        TableColumn tc3 = new TableColumn<>(columnNameArr[2]);
        ButtonCommand command = new SimpleButtonCommand(columnNameArr[2], String.class);
        command.setController(ControllerFactory.getInstance().create(ControllerFactory.ControllerType.SEND_EMAIL_MESSAGE));
        tc3.setCellFactory(e->(new SendMessageButtonTableCell(command)));
        
        tableView.getColumns().addAll(tc1, tc2, tc3);
    } 
}
