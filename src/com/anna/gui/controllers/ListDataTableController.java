/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.data.Address;
import com.anna.data.Hobby;
import com.anna.data.Occupation;
import com.anna.data.Person;
import com.anna.gui.commands.ListButtonCommand;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.MyCloneable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author multiplexor88
 */
public class ListDataTableController extends AbstractController
{
    @FXML
    private Button                                          addBtn,
                                                            editBtn,
                                                            deleteBtn,
                                                            okBtn,
                                                            cancelBtn;

    public Button getAddBtn() {
        return addBtn;
    }

    public Button getEditBtn() {
        return editBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public Button getOkBtn() {
        return okBtn;
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    /**
     * Sometimes such controller is responsible for adding/editing data in Collection
     * In such case isOnOkBySelection must be false
     * In other cases controller is responsible for choosing some items from Collection
     * In such case isOnOkBySelection must be true
     * Set isOnOkBySelection when construct controller
     */
    private boolean onOkBySelection;

    public boolean isOnOkBySelection() {
        return onOkBySelection;
    }

    public void setOnOkBySelection(boolean onOkBySelection) {
        this.onOkBySelection = onOkBySelection;
    }
    
    @Override
    public void onOk() 
    {
        if(onOkBySelection)
        {
            ObservableList selectedItems = table.getTableView().getSelectionModel().getSelectedItems();
            original = new ArrayList();
            ((List)original).addAll(selectedItems);
        }
        else
        {//standard procedure
            original = copy;
        }
        stage.close();
    }
    
    @Override
    public void onCancel()
    {
        if(onOkBySelection)
            original = new ArrayList();
        else original = null;
        stage.close();
    }

    
    @Override
    public void setData(Object original) 
    {
        if(original == null || !(original instanceof Collection))//when user adds data
        {
            clearContext();
            this.original = null;
            copy = null;
            return;
        }
        
        this.original = (List) original;
         
        copy = new ArrayList();
        
        //create copy
        for(Object c:(List)this.original)
            ((List)copy).add(((MyCloneable) c).clone());
        
        table.getTableView().setItems(FXCollections.observableArrayList((Collection)copy));
    }
    
    @FXML
    private TextField           searchTxt;

    public TextField getSearchTxt() {
        return searchTxt;
    }

    @Override
    public void onDelete(Event event) 
    {
        int index = table.getTableView().getSelectionModel().getSelectedIndex();
        if(index < 0 || index >= ((List)copy).size())return;
        Object dataObject = table.getTableView().getSelectionModel().getSelectedItem();
        if(dataObject != null)
        {
            table.getTableView().getItems().remove(dataObject);
            ((List)copy).remove(dataObject);
            if(parentController == null)
                DataLoader.getDataBaseService().getServiceByTableType(table.getTableId()).getRepository().delete(dataObject);
            
            table.getTableView().getSelectionModel().clearSelection();
        }
    }

    @Override
    public void onEdit(Event event) 
    {
        int index = table.getTableView().getSelectionModel().getSelectedIndex();
        if(index < 0 || index >= ((List)copy).size())return;
        Object dataObject = table.getTableView().getItems().get(index);
        command.setData(dataObject);
        command.execute(event);
        Object data = command.getData();
        
        if(data != null)
        {           
            ((List)copy).set(index, data);
        
            if(parentController == null)
            {
                //save to data base
                //DataLoader.getDataBaseService().getServiceByTableType(table.getTableId()).getRepository().delete(dataObject);
                DataLoader.getDataBaseService().getServiceByTableType(table.getTableId()).getRepository().save(data);
                //update tableview
            }
            table.getTableView().getItems().set(index, data);
        }
    }

    @Override
    public void onAdd(Event event) 
    {          
        if(command instanceof SimpleButtonCommand)
        {
            switch(table.getTableId())
            {
                case PERSONS_FULL:
                case PERSONS_FL:
                case PERSONS_FLP:
                    command.setData(new Person());
                    break;
                case HOBBIES:
                    command.setData(new Hobby());
                    break;
                case OCCUPATIONS:
                    command.setData(new Occupation());
                    break;
                case EVENTS:
                case EVENTS_NAME_DESCRIPT:
                    command.setData(new com.anna.data.Event());
                    break;
                case ADDRESSES: 
                    command.setData(new Address());
                    break;
            }
        }
        else if(command instanceof ListButtonCommand)
        {
            /*load data from database*/
            command.setData(DataLoader.getDataBaseService().getServiceByTableType(table.getTableId()).getRepository().findAll());
        }
        
        command.execute(event);
        Object data = command.getData();
        
        if(data != null && !((List)copy).contains(data))
        {
            if(data instanceof Collection)
            {
                //check for existing data in copy
                for(Object o:(Collection)data)
                   if(!((List)copy).contains(o))((List)copy).add(o); 
            }
            else 
                //check for existing data in copy
                if(((List)copy).contains(data))
                    return;
                else ((List)copy).add(data);
        
            if(parentController == null)
                DataLoader.getDataBaseService().getServiceByTableType(table.getTableId()).getRepository().save(data);

            table.getTableView().setItems(FXCollections.observableArrayList((List)copy));
        }
    }
    
    @Override
    protected void onKeyPressed(KeyEvent event){}
    
    /**
     * Searching over current table
     * @param keyEvent 
     */
    @FXML
    public void onSearch(KeyEvent keyEvent)
    {
        
    }
}
