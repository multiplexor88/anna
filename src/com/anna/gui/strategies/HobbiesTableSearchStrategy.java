/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.strategies;

import com.anna.gui.interfaces.TableSearchStrategy;
import com.anna.gui.tables.TableFactory;
import java.util.List;

/**
 *
 * @author igor
 */
public class HobbiesTableSearchStrategy extends TableSearchStrategy
{
    @Override
    public void search(String existedDataInForm, String typedData) 
    {
        super.search(existedDataInForm, typedData);
        
        List dataList = null; 
        
        if(typedData.equals("\b") && existedDataInForm.isEmpty())/*if user delete all data in form*/
            dataList = dataBaseService.getHobbyService().getRepository().findAll();
        else
            dataList = dataBaseService.getHobbyService().getRepository().findHobbyByIdLike((existedDataInForm+typedData).trim() + "%");
        
        TableFactory.getInstance().create(table.getTableId(), dataList);
    }
}
