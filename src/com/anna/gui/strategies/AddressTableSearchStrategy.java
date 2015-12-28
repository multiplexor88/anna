/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.strategies;

import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.interfaces.TableSearchStrategy;

/**
 *
 * @author igor
 */
public class AddressTableSearchStrategy extends TableSearchStrategy
{

    public AddressTableSearchStrategy(AbstractTable table, boolean searchInCurrentData)
    {
        super(table, searchInCurrentData);
    }

    
    @Override
    public void search(String existedDataInForm, String typedData) {
        
    }
}
