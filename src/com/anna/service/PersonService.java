/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.service;

import com.anna.data.Person;
import com.anna.repository.PersonRepository;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author igor
 */
public class PersonService extends AbstractService<Person>
{
    protected PersonRepository repository;
    
    @Override
    public PersonRepository getRepository() {
        return repository;
    }
    
    public PersonService(ApplicationContext context) 
    {
        repository = context.getBean(PersonRepository.class);
    }

    @Override
    public List<Person> retrieveData() {
        return repository.findAll();
    }
}
