/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.builders;

import com.anna.data.Address;
import com.anna.gui.commands.SimpleButtonCommand;
import com.anna.gui.controllers.AddressController;
import com.anna.gui.controllers.DataLoader;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;
import com.anna.gui.interfaces.ButtonCommand;
import com.anna.gui.tables.TableFactory;
import com.anna.gui.tables.TableFactory.TableType;

/**
 * 
 * @author igor
 */
public class AddressesBuilder implements Builder
{
    @Override
    public AbstractController build() 
    {
        AbstractController mainController = (ListDataTableController) DataLoader.getInstance().loadController(ListDataTableController.class, "../fxml/ListDataTable.fxml");
        
        mainController.setTable(TableFactory.create(TableType.ADDRESSES));
        
        ButtonCommand<Address> addEditButtonCommand = new SimpleButtonCommand(DataLoader.getLangResources().getString("key.person.address"), Address.class);
        
        AbstractController addressController = DataLoader.getInstance().loadController(AddressController.class, "../fxml/Address.fxml");
        addressController.setParentController(mainController);
        addEditButtonCommand.setController(addressController);
        
        mainController.setCommand(addEditButtonCommand);
        
        return mainController;
    }
    
}
