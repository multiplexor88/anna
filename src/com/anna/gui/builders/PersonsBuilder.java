/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.builders;

import com.anna.data.Person;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.controllers.PersonController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;
import com.anna.gui.interfaces.ButtonCommand;

/**
 *
 * @author igor
 */
public class PersonsBuilder implements Builder
{
    @Override
    public AbstractController build() 
    {
        ListDataTableController mainController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        
        /*add table when need, because there are few types of Persons table*/
        
        ButtonCommand<Person> addEditButtonCommand = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.person.title"), Person.class);
        
        AbstractController addEditButtonController = DataLoader.getInstance().loadController(PersonController.class, "../fxml/Person.fxml");
        addEditButtonController.setParentController(mainController);
        addEditButtonCommand.setController(addEditButtonController);
        
        mainController.setCommand(addEditButtonCommand);
        
        return mainController;
    }
}