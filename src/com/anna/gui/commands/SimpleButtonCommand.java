/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.commands;

import com.anna.gui.interfaces.ButtonCommand;
import javafx.event.Event;

/**
 * Executes calling contact window
 * T data type
 * REQUIREMENTS!!!  Type T must have constructor: T(T other)
 * @author multiplexor88
 */
public class SimpleButtonCommand<T> extends ButtonCommand<T>
{
    public SimpleButtonCommand(String name, Class<T> c)
    {
        viewName = name;
        classType = c;
    }
    
    @Override
    public void execute(Event event) 
    {
        //controller.setData(data);
        controller.showDialog(event, viewName);
        Object dataObject = controller.getData();
        if(dataObject != null && !dataObject.equals(data))
        {
            /*Constructor constructor = classType.getConstructor(dataObject.getClass());
            data = (T)constructor.newInstance(dataObject);*/
            data = (T) dataObject;
        }
        else
            data = null;
    }
}
