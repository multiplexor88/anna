/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.interfaces;

import com.anna.gui.strategies.AddressTableSearchStrategy;
import com.anna.gui.strategies.CurrentEventsTableSearchStrategy;
import com.anna.gui.strategies.EventsTableSearchStrategy;
import com.anna.gui.strategies.HobbiesTableSearchStrategy;
import com.anna.gui.strategies.OccupationsTableSearchStrategy;
import com.anna.gui.strategies.PeopleTableSearchStrategy;
import com.anna.service.DataBaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Strategy fro searching data in table table
 * @author igor
 */
abstract public class TableSearchStrategy 
{
    protected ObservableList copyItems = FXCollections.observableArrayList();

    public ObservableList getCopyItems() {
        return copyItems;
    }
    
    public TableSearchStrategy(){}
    
    public TableSearchStrategy(AbstractTable table, boolean searchInCurrentData)
    {
        this.table = table;
        this.searchInCurrentData = searchInCurrentData;
        
        //copy data because if user erases data from searchTxt we need extract data from current table
        if(this.searchInCurrentData)
            table.getTableView().getItems().stream().forEach((Object t) ->{copyItems.add(((MyCloneable)t).clone());});
    }
    
    //search over current items in table or over entire database
    protected boolean searchInCurrentData;

    public boolean isSearchInCurrentData() {
        return searchInCurrentData;
    }

    public void setSearchInCurrentData(boolean searchInCurrentData) {
        this.searchInCurrentData = searchInCurrentData;
    }
    
    /*data for searching and searching result*/
    protected AbstractTable table;

    /*database*/
    protected DataBaseService dataBaseService;

    public DataBaseService getDataBaseService() {
        return dataBaseService;
    }
    
    public AbstractTable getTable() {
        return table;
    }

    public void setTable(AbstractTable table) {
        this.table = table;
    }
    
    /**incapsulates searching algorithm
     * 
     * @param existedDataInForm 
     * @param typedData 
     */
    public void search(String existedDataInForm, String typedData)
    {
        if(table == null)
            throw new NullPointerException("Table has not set to strategy");
    };
    
    public static TableSearchStrategy getStrategyByTableType(AbstractTable table)
    {
        switch(table.getTableId())
        {
            case PERSONS_FULL:                  
            case PERSONS_FLP:                   
            case PERSONS_FL:                    return new PeopleTableSearchStrategy(table, true);
            case OCCUPATIONS:                   return new OccupationsTableSearchStrategy(table, true);
            case HOBBIES:                       return new HobbiesTableSearchStrategy(table, true);
            case EVENTS:                        return new EventsTableSearchStrategy(table, true);
            case EVENTS_NAME_DESCRIPT_PERSONS:  return new CurrentEventsTableSearchStrategy(table, true);
            case ADDRESSES:                     return new AddressTableSearchStrategy(table, true);
        }
        
        return null;
    }
}
