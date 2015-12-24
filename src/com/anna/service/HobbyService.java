/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.service;

import com.anna.data.Hobby;
import com.anna.repository.HobbyRepository;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author igor
 */
public class HobbyService extends AbstractService<Hobby>
{
    protected HobbyRepository repository;
    
    public HobbyService(ApplicationContext context) 
    {
        repository = context.getBean(HobbyRepository.class);
    //    retrieveData();
    }

    @Override
    public List<Hobby> retrieveData() {
        return repository.findAll();
    }

    @Override
    public HobbyRepository getRepository() {
        return repository;
    }
}