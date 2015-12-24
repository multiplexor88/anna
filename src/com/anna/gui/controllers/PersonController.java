/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.data.Contact;
import com.anna.data.Person;
import com.anna.gui.commands.ListButtonCommand;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.interfaces.AbstractController;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * When initializes must take database service
 * @author igor
 */
public class PersonController extends AbstractController 
{ 
    @FXML
    private TextField                   firstNameTxt,
                                        lastNameTxt,
                                        patronymicTxt;
    
    @FXML
    private RadioButton                 maleCheck,
                                        femaleCheck;
    
    private AbstractController          addressesController,
                                        contactController,
                                        hobbiesController,
                                        occupationsController;
    
    @Override
    public void setData(Object original) 
    {
        if(original == null)//when user adds data
        {
            clearContext();
            this.original = null;
            copy = null;
            return;
        }
        
        this.original = original;
        firstNameTxt.setText(((Person)this.original).getFirstName());
        lastNameTxt.setText(((Person)this.original).getLastName());
        patronymicTxt.setText(((Person)this.original).getPatronymic());
        boolean isMale = ((Person)this.original).getGender().equals("MALE");
        maleCheck.setSelected(isMale);
        femaleCheck.setSelected(!isMale);
        copy = ((Person)this.original).clone();
    }
    
    @FXML
    protected void initialize()
    {
        addressesController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.ADDRESSES);
        addressesController.setParentController(this);
        
        contactController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.CONTACT);
        
        hobbiesController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.INTERNAL_HOBBIES);
        hobbiesController.setParentController(this);
        
        occupationsController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.INTERNAL_OCCUPATIONS);
        occupationsController.setParentController(this);
    }
    
    @FXML
    public void onAddress(Event event)
    {
        if(copy == null) copy = new Person();
        
        command = new ListButtonCommand(DataLoader.getLangResources().getString("key.addresses.title"));
        command.setController(addressesController);
        command.setData(((Person)copy).getAddressList());
        command.execute(event);
        ArrayList addressList = (ArrayList) command.getData();
        if(addressList != null)
            ((Person)copy).setAddressList(addressList);
    }
    
    @FXML
    public void onContact(Event event)
    {
        if(copy == null) copy = new Person();
        command = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.contact.title"), Contact.class);
        command.setController(contactController);
        command.setData(((Person)copy).getContact());
        command.execute(event);
        Contact contact = (Contact) command.getData();
        if(contact != null)
            ((Person)copy).setContact(contact);
    }
    
    /**
     * Set command->set controller(List)->set command->set simpleAddController
     * @param event 
     */
    @FXML
    public void onHobby(Event event)
    {
        if(copy == null) copy = new Person();
        
        command = new ListButtonCommand(DataLoader.getLangResources().getString("key.event.hobbies"));
        command.setController(hobbiesController);
        command.setData(((Person)copy).getHobbyList());
        command.execute(event);
        ArrayList hobbyList = (ArrayList) command.getData();
        if(hobbyList != null)
            ((Person)copy).setHobbyList(hobbyList);
    }
    
    @FXML
    public void onOccupation(Event event)
    {
        if(copy == null) copy = new Person();
        
        command = new ListButtonCommand(DataLoader.getLangResources().getString("key.event.occupations"));
        command.setController(occupationsController);
        command.setData(((Person)copy).getOccupationList());
        command.execute(event);
        ArrayList occupationList = (ArrayList) command.getData();
        if(occupationList != null)
            ((Person)copy).setOccupationList(occupationList);
    }
    
    @Override
    protected boolean saveData() 
    {
        if(copy == null)
            copy = new Person();
        
        ((Person)copy).setFirstName(firstNameTxt.getText());
        ((Person)copy).setLastName(lastNameTxt.getText());
        ((Person)copy).setPatronymic(patronymicTxt.getText());
        ((Person)copy).setGender(maleCheck.isSelected()?"MALE":"FEMALE");
        
        ResourceBundle langResources = DataLoader.getLangResources();
        Alert alert = new Alert(Alert.AlertType.ERROR,  langResources.getString("key.err.fillData") + 
                                                        "\n" + langResources.getString("key.person.firstName") +
                                                        "\n" + langResources.getString("key.person.lastName") + 
                                                        "\n" + langResources.getString("key.person.patronymic"));
        
        if(((Person)copy).getFirstName() != null && ((Person)copy).getFirstName().isEmpty() &&
           ((Person)copy).getLastName() != null && ((Person)copy).getLastName().isEmpty()   &&
           ((Person)copy).getPatronymic() != null && ((Person)copy).getPatronymic().isEmpty())
        {
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    @Override
    public void clearContext()
    {
        firstNameTxt.clear();
        lastNameTxt.clear();
        patronymicTxt.clear();
        maleCheck.setSelected(true);
        femaleCheck.setSelected(false);
    }
}
