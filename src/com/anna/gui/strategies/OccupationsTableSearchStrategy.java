/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.strategies;

import com.anna.gui.controllers.DataLoader;
import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.interfaces.TableSearchStrategy;
import java.util.List;
import javafx.collections.FXCollections;

/**
 *
 * @author igor
 */
public class OccupationsTableSearchStrategy extends TableSearchStrategy
{
    public OccupationsTableSearchStrategy(){}
    
    public OccupationsTableSearchStrategy(AbstractTable table)
    {
        super(table);
    }
    
    @Override
    public void search(String existedDataInForm, String typedData) 
    {   
        List dataList = null; 
        
        if(typedData.equals("\b") && existedDataInForm.isEmpty())/*if user delete all data in form*/
            dataList = DataLoader.getDataBaseService().getOccupationService().getRepository().findAll();
        else
            dataList = DataLoader.getDataBaseService().getOccupationService().getRepository().findOccupationByTypeLike((existedDataInForm+typedData).trim() + "%");
        
        table.getTableView().setItems(FXCollections.observableArrayList(dataList));
    }
}