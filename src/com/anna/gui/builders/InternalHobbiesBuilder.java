/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.builders;

import com.anna.data.Hobby;
import com.anna.gui.commands.ListButtonCommand;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.ControllerFactory;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.controllers.SingleAddController;
import com.anna.gui.tables.TableFactory;
import com.anna.gui.tables.TableFactory.TableType;

/**
 * Builds view in which by pressing button add/edit list of hobbies erises
 * @protocol:   main view: ListDataTableController
 *                  add/edit button: ListDataTableController
 *                      add/edit button: SingleAddController
 *                      search: HobbiesSearchStrategy
 *                  search: HobbiesSearchStrategy
 * @author igor
 */
public class InternalHobbiesBuilder implements Builder 
{
    @Override
    public AbstractController build() 
    {
        ListDataTableController mainController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        
        mainController.getEditBtn().setDisable(true);
        mainController.setTable(TableFactory.create(TableType.HOBBIES));
        
        ButtonCommand displayDataBaseHobbiesCommand = new ListButtonCommand(DataLoader.getLangResources().getString("key.event.hobbies"));
        ListDataTableController displayDataBaseHobbiesController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        displayDataBaseHobbiesController.getEditBtn().setDisable(true);
        displayDataBaseHobbiesController.getOkBtn().setVisible(true);
        displayDataBaseHobbiesController.getCancelBtn().setVisible(true);
        
        displayDataBaseHobbiesController.setTable(TableFactory.create(TableType.HOBBIES));
        displayDataBaseHobbiesController.setData(DataLoader.getDataBaseService().getHobbyService().getRepository().findAll());
        
        ButtonCommand<Hobby> command = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.hobby.title"), Hobby.class);
        AbstractController controller = DataLoader.getInstance().loadController(SingleAddController.class, "../fxml/SingleAdd.fxml");
        command.setController(controller);
        displayDataBaseHobbiesController.setCommand(command);

        /**
         * see ListDataTableController::isOnOkBySelection
         */
        displayDataBaseHobbiesController.setOnOkBySelection(true);
        displayDataBaseHobbiesCommand.setController(displayDataBaseHobbiesController);       
        mainController.setCommand(displayDataBaseHobbiesCommand);
        
        return mainController;
    }
}
