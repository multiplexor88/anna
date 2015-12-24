/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.data.Person;
import com.anna.service.DataBaseService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Deprecated
 * @author igor
 */
public class Person_ShortTable extends ComplexTable<Person>
{   

    public Person_ShortTable(DataBaseService dataBaseService) {
        super(dataBaseService, "Home");
    }

    @Override
    public void createAndSetRows()
    {
        Iterator<Person> itList = inputDataList.iterator();
        
        while(itList.hasNext())
        {
            Map<String, Object> map = new HashMap<>();
            Person p  = itList.next();
            map.put(fieldIdArr[0], p.getFirstName());
            map.put(fieldIdArr[1], p.getLastName());
            
            String tmpContact = p.getContact() == null ? "no data" : p.getContact().getCellNumber();
            map.put(fieldIdArr[2], tmpContact);
            items.add(map);
        }
        tableView.getItems().addAll(items);
        
    }
}
