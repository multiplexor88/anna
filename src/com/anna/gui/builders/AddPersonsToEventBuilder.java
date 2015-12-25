/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.builders;

import com.anna.data.Hobby;
import com.anna.data.Person;
import com.anna.gui.commands.ListButtonCommand;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.controllers.PersonController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.tables.TableFactory;

/**
 *
 * @author igor
 */
public class AddPersonsToEventBuilder implements Builder
{
    @Override
    public AbstractController build() 
    {
        ListDataTableController mainController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        //set any controller
        mainController.setParentController(DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml"));
        
        mainController.getEditBtn().setDisable(true);
        mainController.setTable(TableFactory.create(TableFactory.TableType.PERSONS_FLP));
        
        ButtonCommand displayDataBasePersonsCommand = new ListButtonCommand(DataLoader.getLangResources().getString("key.persons.title"));
        ListDataTableController displayDataBasePersonsController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        displayDataBasePersonsController.getEditBtn().setDisable(true);
        displayDataBasePersonsController.getAddBtn().setDisable(true);
        displayDataBasePersonsController.getDeleteBtn().setDisable(true);
        displayDataBasePersonsController.getOkBtn().setVisible(true);
        displayDataBasePersonsController.getCancelBtn().setVisible(true);
        displayDataBasePersonsController.setParentController(mainController);
        
        displayDataBasePersonsController.setTable(TableFactory.create(TableFactory.TableType.PERSONS_FLP));
        displayDataBasePersonsController.setData(DataLoader.getDataBaseService().getPersonService().getRepository().findAll());
        
        ButtonCommand<Hobby> command = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.person.title"), Person.class);
        AbstractController controller = DataLoader.getInstance().loadController(PersonController.class, "../fxml/Person.fxml");
        command.setController(controller);
        displayDataBasePersonsController.setCommand(command);

        /**
         * see ListDataTableController::isOnOkBySelection
         */
        displayDataBasePersonsController.setOnOkBySelection(true);
        displayDataBasePersonsCommand.setController(displayDataBasePersonsController);       
        mainController.setCommand(displayDataBasePersonsCommand);
        
        mainController.code = 0;
        displayDataBasePersonsController.code = 1;
        controller.code = 2;
        
        return mainController;
    }
}
