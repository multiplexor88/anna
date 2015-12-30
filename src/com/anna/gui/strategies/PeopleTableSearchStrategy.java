/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.strategies;

import com.anna.gui.controllers.DataLoader;
import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.interfaces.MyCloneable;
import com.anna.gui.interfaces.TableSearchStrategy;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javafx.collections.FXCollections;

/**
 *
 * @author igor
 */
public class PeopleTableSearchStrategy extends TableSearchStrategy
{
    public PeopleTableSearchStrategy(){}
    
    public PeopleTableSearchStrategy(AbstractTable table, boolean searchInCurrentData)
    {
        super(table, searchInCurrentData);
    }
    
    @Override
    public void search(String existedDataInForm, String typedData) 
    {
        List dataList; 
        
        if(typedData.equals("\b") && existedDataInForm.isEmpty())/*if user delete all data in form*/
            if(copyItems == null || !searchInCurrentData)//for clean field take data from DB or current table
                    dataList = DataLoader.getDataBaseService().getPersonService().getRepository().findAll();
            else    dataList = copyItems;   
        else        
        {
            //extract all data by criteria
            dataList = DataLoader.getDataBaseService().getPersonService().getRepository().findPersonByLastNameLike((existedDataInForm+typedData).trim() + "%");
            if(copyItems != null && searchInCurrentData)//filter dataList
                dataList = Arrays.asList(dataList.stream().filter((Object t) -> 
                {
                    return copyItems.stream().anyMatch((Object t1) -> {
                        return t1.equals(t);
                    });
                }).toArray());
        }
        
        table.getTableView().setItems(FXCollections.observableArrayList(dataList));
    }
}