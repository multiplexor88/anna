/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.builders;

import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.data.Occupation;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.controllers.SingleAddController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.interfaces.TableSearchStrategy;
import com.anna.gui.strategies.OccupationsTableSearchStrategy;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.tables.TableFactory;
import com.anna.gui.tables.TableFactory.TableType;

/**
 *
 * @author igor
 */
public class FullModeOccupationsBuilder implements Builder
{
    @Override
    public AbstractController build() 
    {
        ListDataTableController mainController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        //mainController.disableOkBtn();
       // mainController.disableCancelBtn();
        
        AbstractTable table = TableFactory.create(TableType.OCCUPATIONS);
        mainController.setTable(table);
        
        //mainController.setData(DataLoader.getDataBaseService().getOccupationService().getRepository().findAll());
        
        ButtonCommand<Occupation> command = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.occupation.title"), Occupation.class);
        AbstractController controller = DataLoader.getInstance().loadController(SingleAddController.class, "../fxml/SingleAdd.fxml");
        command.setController(controller);
        mainController.setCommand(command);
        
        TableSearchStrategy strategy = new OccupationsTableSearchStrategy(table, false);
        mainController.setStrategy(strategy);
        
        return mainController;
    }
}
