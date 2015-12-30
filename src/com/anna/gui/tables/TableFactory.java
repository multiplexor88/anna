/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.controllers.DataLoader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Creates AbstractTable according tableId
 * @author igor
 */
public class TableFactory 
{   
    /*reusable table pool*/
    /*Deprecated!!!*/
    static private Map<TableType, AbstractTable> tablePool = new HashMap();

    public static enum TableType{   PERSONS_FULL,
                                    PERSONS_FL,//also includes person list which celebrates current events
                                    PERSONS_FLP,
                                    HOBBIES,
                                    OCCUPATIONS,
                                    EVENTS,
                                    EVENTS_NAME_DESCRIPT_PERSONS,
                                    ADDRESSES,
                                    PERSONS_EMAIL};
    
    /*Singleton*/
    private TableFactory(){}
    
    static private ResourceBundle langResources = DataLoader.getLangResources();
    
    /**
     * Creates table with data
     * @param tableId
     * @param data
     * @return 
     */
    static public AbstractTable create(TableType tableId, Collection data)
    {
        AbstractTable table = null;/* = tablePool.get(tableId);*/
        
        //if(table == null)
        //{
        
            switch(tableId)
            {
                case PERSONS_FULL:                  table = createPersonsFull(tableId, data);break;
                case PERSONS_FLP:                   table = createPersonFLP(tableId, data);break;
                case PERSONS_FL:                    table = createPersonFL(tableId, data);break;
                case HOBBIES:                       table = createHobbies(tableId, data);break;
                case OCCUPATIONS:                   table = createOccupations(tableId, data);break;
                case EVENTS:                        table = createEvents(tableId, data);break;
                case EVENTS_NAME_DESCRIPT_PERSONS:  table = createEventsNameDescription(tableId, data);break;
                case ADDRESSES:                     table = createAddresses(tableId, data);break;
                case PERSONS_EMAIL:                 table = createPersonsEmail(tableId, data);break;
                default:                            throw new TypeNotPresentException(tableId.toString(), null);
            }
            
            /*tablePool.put(tableId, table);*/
        //}
        return table;
    }
    
    /**
     * Creates empty table
     * @param tableId
     * @return 
     */
    static public AbstractTable create(TableType tableId)
    {
        return create(tableId, null);
    }
    
    static private AbstractTable createPersonsFull(TableType tableId, Collection data)
    {
        AbstractTable table = new PersonsTable(tableId, langResources.getString("key.persons.title"));
        table.create(   data, 
                        new String[]{   langResources.getString("key.person.firstName"), 
                                        langResources.getString("key.person.lastName"), 
                                        langResources.getString("key.person.patronymic"), 
                                        langResources.getString("key.person.gender"), 
                                        langResources.getString("key.person.address"), 
                                        langResources.getString("key.contact.title"),
                                        langResources.getString("key.hobby.title"),
                                        langResources.getString("key.occupation.title")}, 
                        new String[]{   "firstName", 
                                        "lastName", 
                                        "patronymic", 
                                        "gender", 
                                        "addressList",
                                        "contact",
                                        "hobbyList",
                                        "occupationList"});
        return table;
    }

    static private AbstractTable createPersonFLP(TableType tableId, Collection data) 
    {
        AbstractTable table = new PersonsTable(tableId, langResources.getString("key.persons.title"));
        table.create(   data, 
                        new String[]{   langResources.getString("key.person.firstName"), 
                                        langResources.getString("key.person.lastName"), 
                                        langResources.getString("key.person.patronymic")}, 
                        new String[]{   "firstName", 
                                        "lastName", 
                                        "patronymic"});
        return table;
    }

    static private AbstractTable createPersonFL(TableType tableId, Collection data) 
    {
        AbstractTable table = new PersonsTable(tableId, langResources.getString("key.persons.title"));
        table.create(   data, 
                        new String[]{   langResources.getString("key.person.firstName"), 
                                        langResources.getString("key.person.lastName")}, 
                        new String[]{   "firstName", 
                                        "lastName"});
        return table;
    }

    static private AbstractTable createHobbies(TableType tableId, Collection data) 
    {
        AbstractTable table = new SimpleTable(  tableId,
                                                langResources.getString("key.event.hobbies"),
                                                new String[]{langResources.getString("key.hobby.title")},
                                                new String[]{"type"});
        table.create(data);
        return table;
    }

    static private AbstractTable createOccupations(TableType tableId, Collection data) 
    {
        AbstractTable table = new SimpleTable(  tableId,
                                                langResources.getString("key.occupation.title"),
                                                new String[]{langResources.getString("key.event.occupations")},
                                                new String[]{"type"});
        table.create(data);
        return table;
    }

    static private AbstractTable createEvents(TableType tableId, Collection data) 
    {
        AbstractTable table = new EventsTable(tableId, langResources.getString("key.events.title"));
        table.create(   data,
                        new String[]{   langResources.getString("key.event.date"),
                                        langResources.getString("key.event.name"),
                                        langResources.getString("key.event.description"), 
                                        langResources.getString("key.persons.title"), 
                                        langResources.getString("key.event.hobbies"), 
                                        langResources.getString("key.event.occupations")}, 
                        new String[]{   "date", 
                                        "name", 
                                        "description", 
                                        "personList", 
                                        "hobbyList", 
                                        "occupationList"});
        return table;
    }

    static private AbstractTable createEventsNameDescription(TableType tableId, Collection data) 
    {
        AbstractTable table = new CurrentEventsWithPersonsTable(tableId, langResources.getString("key.currentEvents.title"));
        table.create(   data,
                        new String[]{   langResources.getString("key.event.name"),
                                        langResources.getString("key.event.description"),
                                        langResources.getString("key.persons.title")}, 
                        new String[]{   "name", 
                                        "description",
                                        "personList"});
        return table;
    }
    
    static private AbstractTable createAddresses(TableType tableId, Collection data) 
    {
        AbstractTable table = new SimpleTable(  tableId, 
                                                langResources.getString("key.addresses.title"),
                                                new String[]{   langResources.getString("key.address.country"),
                                                                langResources.getString("key.address.state"),
                                                                langResources.getString("key.address.city"),
                                                                langResources.getString("key.address.street")},
                                                new String[]{   "country", 
                                                                "state", 
                                                                "city", 
                                                                "street"});
        table.create(data);
        return table;
    }
    
        private static AbstractTable createPersonsEmail(TableType tableId, Collection data) 
        {
            AbstractTable table = new PersonsEmail(tableId, langResources.getString("key.persons.title"));
            table.create(   data, 
                            new String[]{   langResources.getString("key.person.firstName"), 
                                            langResources.getString("key.person.lastName"),
                                            langResources.getString("key.persons.send_message")}, 
                            new String[]{   "firstName", 
                                            "lastName"});
            return table;
        }
}
