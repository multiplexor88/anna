/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.strategies;

import com.anna.gui.interfaces.TableSearchStrategy;
import com.anna.gui.tables.TableFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author igor
 */
public class CurrentEventsTableSearchStrategy extends TableSearchStrategy
{
    @Override
    public void search(String existedDataInForm, String typedData) 
    {
        super.search(existedDataInForm, typedData);
        
        List dataList = null; 
        
        String ddMM_dateFormatString = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM"));
        
        if(typedData.equals("\b") && existedDataInForm.isEmpty())/*if user delete all data in form*/
            dataList = dataBaseService.getEventService().getRepository().findByDateLike(ddMM_dateFormatString + "%");
        else
            dataList = dataBaseService.getEventService().getRepository().findByDateLikeAndNameLike(ddMM_dateFormatString + "%", (existedDataInForm+typedData).trim() + "%");
        
        TableFactory.getInstance().create(table.getTableId(), dataList);
    }
}