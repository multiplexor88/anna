/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.data.Contact;
import com.anna.gui.interfaces.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author igor
 */
public class ContactController extends AbstractController 
{
    @FXML
    private VBox                parentVBox;
    
    @FXML
    private TextField           cellNumberTxt,
                                workNumberTxt,
                                homeNumberTxt,
                                emailTxt;
    
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
        copy = ((Contact)original).clone();
        
        cellNumberTxt.setText(((Contact)this.copy).getCellNumber());
        homeNumberTxt.setText(((Contact)this.copy).getHomeNumber());
        workNumberTxt.setText(((Contact)this.copy).getWorkNumber());
        emailTxt.setText(((Contact)this.copy).getEmail());
        
    }

    @Override
    public void clearContext() 
    {
        cellNumberTxt.clear();
        homeNumberTxt.clear();
        workNumberTxt.clear();
        emailTxt.clear();
    }

    @Override
    protected boolean saveData() 
    {
        if(copy == null)
        copy = new Contact();
        
        /*get data*/
        String cell, home, work, email;
        cell = cellNumberTxt.getText();
        home = homeNumberTxt.getText();
        work = workNumberTxt.getText();
        email = emailTxt.getText();
        
        /*validate data*/
        Alert alert = new Alert(Alert.AlertType.ERROR, DataLoader.getLangResources().getString("key.err.dataFormat"));
        if(cell != null && cell.matches(".*\\D+.*") ||
           home != null && home.matches(".*\\D+.*") ||
           work != null && work.matches(".*\\D+.*") ||
           email != null && !email.isEmpty() && !email.matches(".*@+.*"))
        {
            alert.showAndWait();
            return false;
        }
        
        /*save data*/
        ((Contact)copy).setCellNumber(cell);
        ((Contact)copy).setHomeNumber(home);
        ((Contact)copy).setWorkNumber(work);
        ((Contact)copy).setEmail(email);
        
        return true;
    }
}
