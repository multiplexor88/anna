/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.interfaces.ButtonCommand;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Represents single table cell
 * @param<S> table row type
 * @param<T> table cell type
 * REQUIREMENTS!!! param<T> must represent java bean
 * @author igor
 */
public class ButtonTableCell<S,T> extends TableCell<S, T>
{
    private Button                            button                            = new Button("...");
    private ButtonCommand                     command;
    
    public ButtonTableCell(ButtonCommand command)
    {
        this.command = command;
    }
    
    @Override
    protected void updateItem(T item, boolean empty) 
    {
        super.updateItem(item, empty);
        setGraphic(button);
        button.setOnAction((ActionEvent event) -> 
        {
            try 
            {
                if(getTableRow().getIndex() < 0)
                    return;
                
                command.setData(item);
                command.execute(event);
                
                /*get data and check on null*/
                Object t = command.getData();
                if(t == null)
                    return;
                
                /*set data  t to object s*/
                S s = (S)getTableRow().getItem();
                final String propName = ((PropertyValueFactory)getTableColumn().getCellValueFactory()).getProperty();
                String methodName = "set" + propName.toUpperCase().charAt(0) + propName.substring(1);
                Method method = s.getClass().getDeclaredMethod(methodName, t.getClass());
                method.invoke(s, t);
                
                /*update data in current table view*/
                updateIndex(getIndex());
                
                /*save to data base*/
                DataLoader.getDataBaseService().getServiceByObjectType(s).getRepository().save(s);
            } 
            catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) 
            {
                Logger.getLogger(ButtonTableCell.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
    }
}

/*

switch(target)
            {
                case "occupationList":
                    
                    p = personList.get(i);
                    targetTable = new Occupation_FullTable(dataBaseService, rb.getString("key.event.occupations"));
                    targetTable.create( p.getOccupationList(),
                            new String[]{rb.getString("key.occupation.title")},
                            new String[]{"id"});
                    singleColumnController.setData(p.getOccupationList());
                    singleColumnController.showDialog(event, targetTable, dataBaseService);
                    
                    tmpList = (List) singleColumnController.getData();
                    if(tmpList != null)
                    {
                        p.setOccupationList(tmpList);
                        dataBaseService.getPersonService().getRepository().save(p);
                        getTableView().getItems().set(i, (S)p);
                    }
                    break;
                    
                case "addressList":
                    
                    p = personList.get(i);
                    singleColumnController.setData(p.getAddressList());
                    targetTable = new Address_FullTable(rb.getString("key.addresses.title"));
                    targetTable.create(  p.getAddressList(),
                            new String[]{rb.getString("key.address.country"),rb.getString("key.address.state"),rb.getString("key.address.city"),rb.getString("key.address.street")},
                            new String[]{"country", "state", "city", "street"});
                    singleColumnController.showDialog(event, targetTable, dataBaseService);
                    tmpList = (List) singleColumnController.getData();
                    if(tmpList != null)
                    {
                        p.setAddressList(tmpList);
                        dataBaseService.getPersonService().getRepository().save(p);
                        getTableView().getItems().set(i, (S)p);
                    }
                    break;
                    
                case "hobbyList":
                    
                    p = personList.get(i);
                    singleColumnController.setData(p.getHobbyList());
                    targetTable = new Hobby_FullTable(dataBaseService, rb.getString("key.event.hobbies"));
                    targetTable.create( p.getHobbyList(),
                            new String[]{rb.getString("key.hobby.title")},
                            new String[]{"id"});
                    singleColumnController.showDialog(event, targetTable, dataBaseService);
                    tmpList  = (List) singleColumnController.getData();
                    if(tmpList != null)
                    {
                        p.setHobbyList(tmpList);
                        dataBaseService.getPersonService().getRepository().save(p);
                        getTableView().getItems().set(i, (S)p);
                    }
                    break;
                    
                case "contact":
                    
                    p = personList.get(i);
                    contactController.setData(p.getContact());
                    contactController.showDialog(event, rb.getString("key.contact.title"), dataBaseService);
                    Contact c = (Contact) contactController.getData();
                    if(c != null)
                    {
                        p.setContact(c);
                        dataBaseService.getPersonService().getRepository().save(p);
                        getTableView().getItems().set(i, (S)p);
                    }
                    break;
            }

*/