/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.strategies;

import com.anna.gui.controllers.DataLoader;
import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.interfaces.TableSearchStrategy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;

/**
 *
 * @author igor
 */
public class CurrentEventsTableSearchStrategy extends TableSearchStrategy
{
    public CurrentEventsTableSearchStrategy(AbstractTable table, boolean searchInCurrentData)
    {
        super(table, searchInCurrentData);
    }
    
    @Override
    public void search(String existedDataInForm, String typedData) 
    {
        List dataList; 
        
        String ddMM_dateFormatString = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM"));
        
        if(typedData.equals("\b") && existedDataInForm.isEmpty())/*if user delete all data in form*/
                    dataList = DataLoader.getDataBaseService().getEventService().getRepository().findByDateLike(ddMM_dateFormatString + "%");
        else        dataList = DataLoader.getDataBaseService().getEventService().getRepository().findByDateLikeAndNameLike(ddMM_dateFormatString + "%", (existedDataInForm+typedData).trim() + "%");
        
        table.getTableView().setItems(FXCollections.observableArrayList(dataList));
    }
}