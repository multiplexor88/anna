/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.service;

import com.anna.data.Contact;
import com.anna.repository.ContactRepository;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author igor
 */
public class ContactService extends AbstractService<Contact>
{
    
    protected ContactRepository repository;
    
    public ContactService(ApplicationContext context) 
    {
        repository = context.getBean(ContactRepository.class);
    }

    @Override
    public List<Contact> retrieveData() {
        return repository.findAll();
    }

    @Override
    public ContactRepository getRepository() {
        return repository;
    }
    
    
}