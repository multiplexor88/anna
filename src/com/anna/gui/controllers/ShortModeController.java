/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.data.Event;
import com.anna.data.Hobby;
import com.anna.data.Occupation;
import com.anna.data.Person;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.tables.TableFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author igor
 */
public class ShortModeController extends AbstractController
{
    private AbstractController      fullModeController;
    
    private Stage                   childStage;
    
    private List<Event>             events                                      = new ArrayList();
    private Set<Person>             persons                                     = new HashSet();
    
    private Stage                   fullModeStage;
    
    
    /**
     * Load mainWindowController
     */
    @FXML
    protected void initialize()
    {
        /*load cntrollers: FULL_MODE, CURRENT_EVENTS, PEOPLE*/
        fullModeController = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.FULL_MODE);
        
        /*set start page*/
        onEvents();
    }
        
        
    /**
     * Show MainWinow
     * @param event 
     */
    @FXML
    private void onFullMode()
    {
        /*close current stage*/
        super.onOk();
        
        if(fullModeStage == null)
        {
            fullModeStage = new Stage();
        
            Pane pr = fullModeController.getParent();
            double w = Screen.getPrimary().getBounds().getWidth()/2;
            double h = Screen.getPrimary().getBounds().getHeight()/3;

            pr.setPrefSize(w, h);
            fullModeStage.setScene(new Scene(pr));
            fullModeController.setStage(fullModeStage);
            ((FullModeController)fullModeController).setStartView();
            
            /*call short mode controller*/
        }
        
        ((FullModeController)fullModeController).startTimeService();
        fullModeStage.show();
    }
    
    /**
     * Display who celebrates current holidays
     */
    @FXML
    private void onPeople()
    {
        /*Search over entire Person database for find persons relate to current events*/
        findEventsAndPersons();
        
        /*attach to table*/
        table = TableFactory.create(TableFactory.TableType.PERSONS_FL);
        table.getTableView().setItems(FXCollections.observableArrayList(persons));

        //finishCreation();
        localizeTableInParent();
    }

    /**
     * Display current events
     */
    @FXML
    public void onEvents()
    {
        findEvents();
        
        /*attach to table*/
        table = TableFactory.create(TableFactory.TableType.EVENTS_NAME_DESCRIPT);
        table.getTableView().setItems(FXCollections.observableArrayList(events));
        
        localizeTableInParent();
        //finishCreation();
    }
    
    /**
     * Find persons who has relation to current events 
     */
    public void findEventsAndPersons()
    {
        /*find events*/
        findEvents();
        
        /*find persons*/
        List<Person> personList = DataLoader.getDataBaseService().getPersonService().getRepository().findAll();
        persons.clear();
        for(Event e:events)
        {
            /*add persons from event*/
            persons.addAll(e.getPersonList());
            
            /*add persons by hobbies*/
            List<Hobby> h = e.getHobbyList();
            for(Hobby _h:h)
                for(Person p:personList)
                    if(p.getHobbyList().contains(_h))
                        persons.add(p);
            
            /*add persons by occupations*/
            List<Occupation> o = e.getOccupationList();
            for(Occupation _o:o)
                for(Person p:personList)
                    if(p.getOccupationList().contains(_o))
                        persons.add(p);
        }
    }
    
    /**
     * Find events
     */
    private void findEvents()
    {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM"));
        events = DataLoader.getDataBaseService().getEventService().getRepository().findByDateLike(date + "%");
    }
    
    /**
     * Help function
     * @Description: finishes view creation
     */
    private void localizeTableInParent()
    {        
        underTableParent.getChildren().clear();
        underTableParent.getChildren().add(table.getTableView());

        AnchorPane.setBottomAnchor(table.getTableView(), 0.0);
        AnchorPane.setLeftAnchor(table.getTableView(), 0.0);
        AnchorPane.setTopAnchor(table.getTableView(), 0.0);
        AnchorPane.setRightAnchor(table.getTableView(), 0.0);
    }
}