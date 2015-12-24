/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.interfaces;

import com.anna.service.DataBaseService;

/**
 * Strategy fro searching data in table table
 * @author igor
 */
abstract public class TableSearchStrategy 
{
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
            throw new NullPointerException("Table has not been set to strategy");
    };
}
