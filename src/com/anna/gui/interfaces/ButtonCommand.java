/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.interfaces;

import javafx.event.Event;

/**
 * ButtonCommand interface
 * Executes button action 
 * @author multiplexor88
 */
abstract public class ButtonCommand<T> 
{
    protected String                viewName;
    protected T                     data;
    protected Class<T>              classType;
    protected AbstractController    controller;
    
    
    /*some controllers do not have*/
    protected AbstractTable         table;
    
    abstract public void execute(Event event);

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        controller.setData(data);
    }

    public AbstractController getController() {
        return controller;
    }

    public void setController(AbstractController controller) {
        this.controller = controller;
    }
}
