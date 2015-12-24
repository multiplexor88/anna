/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.builders;

import com.anna.data.Hobby;
import com.anna.data.Occupation;
import com.anna.gui.commands.ListButtonCommand;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.controllers.SingleAddController;
import com.anna.gui.tables.TableFactory;
import com.anna.gui.tables.TableFactory.TableType;

/**
 *
 * @author igor
 */
public class InternalOccupationsBuilder implements Builder 
{
    @Override
    public AbstractController build() 
    {
        ListDataTableController mainController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        
        mainController.getEditBtn().setDisable(true);
        mainController.setTable(TableFactory.create(TableType.OCCUPATIONS));
        
        ButtonCommand displayDataBaseOccupationsCommand = new ListButtonCommand(DataLoader.getLangResources().getString("key.event.occupations"));
        ListDataTableController displayDataBaseOccupationsController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        displayDataBaseOccupationsController.getEditBtn().setDisable(true);
        displayDataBaseOccupationsController.getOkBtn().setVisible(true);
        displayDataBaseOccupationsController.getCancelBtn().setVisible(true);
        
        displayDataBaseOccupationsController.setTable(TableFactory.create(TableType.OCCUPATIONS));
        displayDataBaseOccupationsController.setData(DataLoader.getDataBaseService().getOccupationService().getRepository().findAll());
        
        ButtonCommand<Hobby> command = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.occupation.title"), Occupation.class);
        AbstractController controller = DataLoader.getInstance().loadController(SingleAddController.class, "../fxml/SingleAdd.fxml");
        command.setController(controller);
        displayDataBaseOccupationsController.setCommand(command);

        /**
         * see ListDataTableController::isOnOkBySelection
         */
        displayDataBaseOccupationsController.setOnOkBySelection(true);
        displayDataBaseOccupationsCommand.setController(displayDataBaseOccupationsController);
        mainController.setCommand(displayDataBaseOccupationsCommand);

        return mainController;
    }
}