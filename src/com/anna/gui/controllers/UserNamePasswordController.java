/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.gui.interfaces.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author igor
 */
public class UserNamePasswordController extends AbstractController
{
    @FXML
    private TextField           userNameTxt;
    
    @FXML
    private PasswordField       passwordTxt;
    
    private String[] dataStr;

    @Override
    public void onOk() 
    {
        saveData();
        original = dataStr;
        stage.close();
    }

    @Override
    protected boolean saveData() 
    {
        dataStr = new String[2];
        dataStr[0] = userNameTxt.getText();
        dataStr[1] = passwordTxt.getText();

        return true;
    }

    @Override
    public void clearContext() 
    {
        userNameTxt.clear();
        passwordTxt.clear();
    }
}
