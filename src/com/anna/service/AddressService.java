/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.service;

import com.anna.data.Address;
import com.anna.repository.AddressRepository;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author igor
 */
public class AddressService extends AbstractService<Address>
{

    protected AddressRepository repository;
    
    public AddressService(ApplicationContext context) 
    {
        repository = context.getBean(AddressRepository.class);
    }

    @Override
    public List<Address> retrieveData() {
        return repository.findAll();
    }

    @Override
    public AddressRepository getRepository() {
        return repository;
    }
    
}