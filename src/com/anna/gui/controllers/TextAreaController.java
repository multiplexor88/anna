/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.gui.interfaces.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 *
 * @author igor
 */
public class TextAreaController extends AbstractController
{
    @FXML
    private TextArea    textAreaTxt;

    @Override
    public void setData(Object data) {
        if(data == null)
        {
            original = null;
            copy = null;
            clearContext();
            return;
        }
        
        original = data;
        copy = new String((String) original);
        
        textAreaTxt.setText((String)copy);
    }

    @Override
    public void clearContext() {
        textAreaTxt.clear();
    }

    @Override
    protected boolean saveData() {
        copy = textAreaTxt.getText();
        return true;
    }
    
    
    
}
