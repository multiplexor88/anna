/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.service;

import com.anna.data.Event;
import com.anna.repository.AddressRepository;
import com.anna.repository.EventRepository;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author igor
 */
public class EventService extends AbstractService<Event>{

    private EventRepository repository;
    
    public EventService(ApplicationContext context) 
    {
        repository = context.getBean(EventRepository.class);
    }
    
    @Override
    public List<Event> retrieveData() {
        return repository.findAll();
    }

    @Override
    public EventRepository getRepository() {
        return repository;
    }
}
