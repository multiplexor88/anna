/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.data.Address;
import com.anna.data.Person;
import com.anna.gui.interfaces.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author igor
 */
public class AddressController extends AbstractController
{
    private Person              person;
    
    @FXML
    private TextField           countryTxt,
                                stateTxt,
                                cityTxt,
                                streetTxt;

    @Override
    public void setData(Object original) 
    {
        if(original == null)
        {
            clearContext();
            this.original = null;
            copy = null;
            return;
        }
        
        this.original = original;
        copy = ((Address)this.original).clone();
        
        countryTxt.setText(((Address)this.copy).getCountry());
        stateTxt.setText(((Address)this.copy).getState());
        cityTxt.setText(((Address)this.copy).getCity());
        streetTxt.setText(((Address)this.copy).getStreet());
    }
    
    public void showDialog(ActionEvent event)
    {
        if(stage == null)
        {
            stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        }
        stage.show();
    }

    @Override
    protected boolean saveData() 
    {
        if(copy == null)copy = new Address();
        
        String country, state, city, street;
        country = countryTxt.getText();
        state = stateTxt.getText();
        city = cityTxt.getText();
        street = streetTxt.getText();
        
       ((Address)copy).setCountry(country);
       ((Address)copy).setState(state);
       ((Address)copy).setCity(city);
       ((Address)copy).setStreet(street);
       
       return true;
    }
    
    
    @Override
    public void clearContext()
    {
        //address = new Address();
        countryTxt.clear();
        stateTxt.clear();
        cityTxt.clear();
        streetTxt.clear();
    }
    
}