/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.builders;

import com.anna.data.Hobby;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.controllers.SingleAddController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.tables.TableFactory;
import com.anna.gui.tables.TableFactory.TableType;

/**
 * Builds controller via protocol.
 * @protocol:   main view: ListDataTableController(disable Ok, disable Cancel)
 *                  add/edit button: SingleAddController
 *                  search: HobbiesSearchStrategy
 * 
 * @author igor
 */
public class FullModeHobbiesBuilder implements Builder
{
    @Override
    public AbstractController build() 
    {
        ListDataTableController mainController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        //mainController.getOkBtn().setVisible(true);
       //mainController.getCancelBtn().setVisible(true);
        
        mainController.setTable(TableFactory.create(TableType.HOBBIES));
        //mainController.setData(DataLoader.getDataBaseService().getHobbyService().getRepository().findAll());
        
        ButtonCommand<Hobby> command = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.hobby.title"), Hobby.class);
        AbstractController controller = DataLoader.getInstance().loadController(SingleAddController.class, "../fxml/SingleAdd.fxml");
        command.setController(controller);
        mainController.setCommand(command);
        
        //TableSearchStrategy strategy = new HobbiesTableSearchStrategy();
        //mainController.setStrategy(strategy);
        
        return mainController;
    }
}
