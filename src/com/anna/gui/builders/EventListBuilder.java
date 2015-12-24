/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.builders;

import com.anna.data.Event;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.EventController;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.controllers.DataLoader;

/**
 *
 * @author igor
 */
public class EventListBuilder implements Builder
{
    @Override
    public AbstractController build() 
    {
        ListDataTableController mainController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        
        ButtonCommand<Event> addEditButtonCommand = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.event.title"), Event.class);
        
        addEditButtonCommand.setController(DataLoader.getInstance().loadController(EventController.class, "../fxml/Event.fxml"));
        
        mainController.setCommand(addEditButtonCommand);
        
        return mainController;
    }
    
}