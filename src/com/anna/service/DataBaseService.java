/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.service;

import com.anna.data.Event;
import com.anna.data.Hobby;
import com.anna.data.Occupation;
import com.anna.data.Person;
import com.anna.gui.tables.TableFactory.TableType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author igor
 */
public class DataBaseService 
{
    private PersonService personService;
    
    private AddressService addressService;
    
    private ContactService contactService;
    
    private HobbyService hobbyService;
    
    private OccupationService occupationService;
    
    private EventService eventService;
    
    private ApplicationContext context = null;
    
    public DataBaseService()
    {
        
    }
    
    //create all services at once
    public void initializeServices(String applicationContext)
    {
        context = new ClassPathXmlApplicationContext(applicationContext);
        
        personService = new PersonService(context);
        
        addressService = new AddressService(context);
        
        contactService = new ContactService(context);
        
        hobbyService = new HobbyService(context);
        
        occupationService = new OccupationService(context);
        
        eventService = new EventService(context);
        
    }

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public ContactService getContactService() {
        return contactService;
    }

    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    public HobbyService getHobbyService() {
        return hobbyService;
    }

    public void setHobbyService(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    public OccupationService getOccupationService() {
        return occupationService;
    }

    public void setOccupationService(OccupationService occupationService) {
        this.occupationService = occupationService;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public EventService getEventService() {
        return eventService;
    }
    
    public AbstractService getServiceByTableType(TableType tabId)
    {
        switch(tabId)
        {
            case EVENTS:
            case EVENTS_NAME_DESCRIPT_PERSONS:      return eventService;
                
            case PERSONS_FULL:
            case PERSONS_FLP:
            case PERSONS_FL:                return personService;
                
            case HOBBIES:                   return hobbyService;
                
            case OCCUPATIONS:               return occupationService;
                
            default:                        return null;
        }
    }
    
    public AbstractService getServiceByObjectType(Object c)
    {
        if(c instanceof Event)  return eventService;
        if(c instanceof Person)  return personService;
        if(c instanceof Hobby)  return hobbyService;
        if(c instanceof Occupation)  return occupationService;
        return null;
    }
}
