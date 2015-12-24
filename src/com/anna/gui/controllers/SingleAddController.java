/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.MyCloneable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Gets String from TextArea
 * @author igor
 */
public class SingleAddController extends AbstractController
{
    @FXML
    private TextField           textTxt;

    /**
     * creates data structure according to it's type and fill with old data
     * requirements: data type must include Long id field String type, get/set methods:
     * class Data:
     *          Long id;
     *          String type;
     *          get/set methods
     * @param data 
     */
    @Override
    public void setData(Object data) {
        if(data == null)
        {
            this.original = null;
            this.copy = null;
            textTxt.clear();
        } 
        else 
        {
            this.original = data;
            copy = ((MyCloneable)original).clone();
            
            Method[] methods = copy.getClass().getMethods();
            boolean isSetText = false;
            for(Method m:methods)
            {
                String methodName = m.getName();
                if(methodName.startsWith("get"))
                {
                    Object sData = null;
                    try {
                        sData = m.invoke(copy);
                        if(sData instanceof String)
                        {
                            textTxt.setText((String) sData);
                            isSetText = true;
                            break;
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(SingleAddController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if(!isSetText)
                textTxt.clear();
        }
    }
    
    @Override
    public void onOk()
    {
        if(textTxt != null && textTxt.getText() != null && !textTxt.getText().isEmpty())
        {
            String data = textTxt.getText();

            /*change copy*/
            Method[] methods = copy.getClass().getMethods();
            for(Method m:methods)
            {
                String methodName = m.getName();
                if(methodName.startsWith("set") && m.getParameterCount() == 1 && m.getParameters()[0].getType().getTypeName().endsWith("String"))
                {
                    try {
                        m.invoke(copy, data);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(SingleAddController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    original = copy;
                    
                    break;
                }
            }
        }
        
        stage.hide();
    }
    
    @Override
    public void clearContext() {
        super.clearContext();
        textTxt.clear();
    }
    
    
}
