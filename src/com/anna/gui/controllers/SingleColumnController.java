/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.data.Address;
import com.anna.gui.Hobby_FullTable;
import com.anna.gui.Occupation_FullTable;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.interfaces._Cloneable;
import com.anna.service.DataBaseService;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;

/**
 *
 * @author igor
 */
public class SingleColumnController extends AbstractController 
{
    @FXML
    private Button              deleteBtn,
                                addBtn,
                                editBtn;
    
    @Override
    public List<_Cloneable> getData() {
        return original;
    }

    @Override
    public void setData(Object original) 
    {
        this.original = (List<_Cloneable>)original;
        
        copy = new ArrayList();
        //create copy
        for(_Cloneable c:this.original)
            copy.add((_Cloneable) c.clone());
    }
    
    private List<_Cloneable>    original, copy;
    
    private final FXMLLoader    addressLoader                                   = new FXMLLoader(),
                                listDataTabLoader                               = new FXMLLoader();
    
    private AbstractController  addressController,
                                listDataTabController;
    
    @Override
    public void showDialog(Event event, AbstractTable table, DataBaseService dbs)
    {
        dataBaseService = dbs;
        this.table = table;
        attachOnAddBtnClicked();
        attachOnEditBtnClicked();
        ((Pane)parent).getChildren().set(0, table.getTableView()); 
        
        loadStage(event);
        stage.setTitle(table.getTableId());
        
        String tabName = table.getTableId();
        if(tabName.equals("Hobby") || tabName.equals("Occupation"))
            editBtn.setDisable(true);
        
        stage.showAndWait();
    }
        
    @Override
    public void onAdd(Event event)
    {
        AbstractTable targetTable;
        ObservableList oList;
        List pList;
        
        switch(table.getTableId())
        {
            case "Addresses":
                
                addressController = initializeController(addressLoader, AddressController.class, "../fxml/Address.fxml");
                
                addressController.clearContext();
                addressController.showDialog(event, table, dataBaseService);
                Address address = (Address)addressController.getData();
                
                if(!address.isEmpty())
                {
                    table.getTableView().getItems().add(address);
                    copy.add(address);
                }
                
                
                break;
            case "Occupations":
                
                listDataTabController = initializeController(listDataTabLoader, ListDataTableController.class, "../fxml/listDataTable.fxml");
                
                //load existed table for single/multiple item selection or adding new item
                targetTable = new Occupation_FullTable(dataBaseService, langResources.getString("key.event.occupations"));
                pList = dataBaseService.getOccupationService().getData();
                targetTable.create( pList, new String[]{langResources.getString("key.occupation.title")}, new String[]{"id"});
                targetTable.getTableView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                
                listDataTabController.setData(copy);
                //((ListDataTableController)listDataTabController).getAddBtn().setDisable(true);
                ((ListDataTableController)listDataTabController).disableEditBtn();
                ((ListDataTableController)listDataTabController).disableDeleteBtn();
                
                listDataTabController.showDialog(event, targetTable, dataBaseService);
                
                oList = targetTable.getTableView().getSelectionModel().getSelectedItems();
                
                validateOnExisting(oList, copy, true);

                break;
                
            case "Hobbies":
                
                listDataTabController = initializeController(listDataTabLoader, ListDataTableController.class, "../fxml/listDataTable.fxml");
                
                //load existed table for single/multiple item selection or adding new item
                targetTable = new Hobby_FullTable(dataBaseService, langResources.getString("key.event.hobbies"));
                pList = dataBaseService.getHobbyService().getData();
                targetTable.create( pList, new String[]{langResources.getString("key.hobby.title")}, new String[]{"id"});
                targetTable.getTableView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                
                listDataTabController.setData(copy);
                //((ListDataTableController)listDataTabController).getAddBtn().setDisable(true);
                ((ListDataTableController)listDataTabController).disableEditBtn();
                ((ListDataTableController)listDataTabController).disableDeleteBtn();
                
                listDataTabController.showDialog(event, targetTable, dataBaseService);
                oList = targetTable.getTableView().getSelectionModel().getSelectedItems();
                
                validateOnExisting(oList, copy, true);
                
                break;
        }
    }
    
    @Override
    public void onEdit(Event event)
    {
        int index = table.getTableView().getSelectionModel().getSelectedIndex();
        if(index >= 0)
        {
            switch(table.getTableId())
            {
                case "Addresses":
                    Address address;

                    addressController = initializeController(addressLoader, AddressController.class, "../fxml/Address.fxml");
                    address = (Address) copy.get(index);
                    addressController.setData(address);
                    addressController.showDialog(event, table, dataBaseService);
                    
                    //if(!address.isEmpty())
                    table.getTableView().getItems().set(index, address);

                    break;
                
                /*Deprecated
                case "Occupation":
                    break;
                case "Hobby":
                    break;
                */
            }
        }
    }
    
    public void onDelete(Event event)
    {
        Object deletedItem = table.getTableView().getSelectionModel().getSelectedItem();
        if(deletedItem != null)
        {
            copy.remove(deletedItem);
            table.getTableView().getItems().remove(deletedItem);
            table.getTableView().getSelectionModel().clearSelection();
        }
    }

    @Override
    public void onCancel() 
    {
        original = null;
        stage.hide();
    }

    @Override
    public void onOk() 
    {
        original = copy;
        stage.hide();
    }
}
