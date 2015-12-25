/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.data.Event;
import com.anna.data.Person;
import com.anna.gui.commands.ListButtonCommand;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.interfaces.AbstractController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author igor
 */
public class EventController extends AbstractController
{
    @FXML
    private DatePicker                  dateDtPickr;
    
    @FXML
    private TextField                   nameTxt;
    
    private List                        occupationList                          = new ArrayList(),
                                        personList                              = new ArrayList(),
                                        hobbyList                               = new ArrayList();

    AbstractController                  textAreaController,
                                        personsController,
                                        hobbiesController,
                                        occupationsController,
                                        addPersonsToEventController;
   
    @FXML
    protected  void initialize()
    {
        textAreaController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.TEXT_AREA);
        //personsController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.PEOPLE);
        hobbiesController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.INTERNAL_HOBBIES);
        occupationsController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.INTERNAL_OCCUPATIONS);
        addPersonsToEventController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.ADD_PERSONS_TO_EVENT);
    }
    
    @Override
    public Object getData() {
        return original;
    }

    @Override
    public void setData(Object data) 
    {
        if(data == null)
            return;
        
        original = (Event) data;
        
        //clone data
        copy = ((Event)original).clone();
        
        //set to controlls
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        
        //dateDtPickr.setValue(LocalDate.parse(((Event)copy).getDate(), dtf));
        dateDtPickr.setValue(LocalDate.parse(((Event)copy).getDate(),dtf));
        nameTxt.setText(((Event)copy).getName());
        personList = ((Event)copy).getPersonList();
        occupationList = ((Event)copy).getOccupationList();
        hobbyList = ((Event)copy).getHobbyList();
    }
    
     /**
     * help function: fills copy with window context
     */
    private void fillEvent()
    {  
        String date = dateDtPickr.getEditor().getText();
        ((Event)copy).setName(nameTxt.getText());
        ((Event)copy).setDate(date);
    }
    
    @Override
    public void onOk() 
    {
        fillEvent();
        
        Alert alert = new Alert(Alert.AlertType.ERROR, DataLoader.getLangResources().getString("key.err.dateAndFieldEmpty"));
        if(((Event)copy).getDate()!= null && ((Event)copy).getDate().isEmpty() ||
           ((Event)copy).getName() != null && ((Event)copy).getName().isEmpty())
        {
            alert.showAndWait();
            return;
        }
        //assign data to original
        original = copy;
        stage.hide();
    }

    @Override
    public void clearContext() 
    {
        original = new Event();
        copy = new Event();
        
        dateDtPickr.setValue(LocalDate.now());
        nameTxt.clear();
    }
    
    
    @FXML
    private void onDescription(ActionEvent event)
    {
        if(copy == null) copy = new Event();
        
        command = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.event.description"), String.class);
        command.setController(ControllerFactory.getInstance().create(ControllerFactory.ControllerType.TEXT_AREA));
        command.setData(((Event)copy).getDescription());
        command.execute(event);
        String description = (String) command.getData();
        if(description != null)
            ((Event)copy).setDescription(description);
    }
    
    @FXML
    private void onPeople(ActionEvent event)
    {
        if(copy == null) copy = new Event();
        
        command = new ListButtonCommand(DataLoader.getLangResources().getString("key.event.people"));
        command.setController(addPersonsToEventController);
        command.setData(((Event)copy).getPersonList());
        command.execute(event);
        ArrayList personList = (ArrayList) command.getData();
        if(personList != null)
            ((Event)copy).setPersonList(personList);
    }
    
    @FXML
    private void onHobbies(ActionEvent event)
    {
        if(copy == null) copy = new Event();
        
        command = new ListButtonCommand(DataLoader.getLangResources().getString("key.event.hobbies"));
        command.setController(hobbiesController);
        command.setData(((Event)copy).getHobbyList());
        command.execute(event);
        ArrayList hobbyList = (ArrayList) command.getData();
        if(hobbyList != null)
            ((Event)copy).setHobbyList(hobbyList);
    }
    
    @FXML
    private void onOccupations(ActionEvent event)
    {
        if(copy == null) copy = new Event();
        
        command = new ListButtonCommand(DataLoader.getLangResources().getString("key.event.occupations"));
        command.setController(occupationsController);
        command.setData(((Event)copy).getOccupationList());
        command.execute(event);
        ArrayList occupationList = (ArrayList) command.getData();
        if(occupationList != null)
            ((Event)copy).setOccupationList(occupationList);
    }
}
