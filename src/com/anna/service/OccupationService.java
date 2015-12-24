/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.service;

import com.anna.data.Occupation;
import com.anna.repository.OccupationRepository;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author igor
 */
public class OccupationService extends AbstractService<Occupation>
{
    protected OccupationRepository repository;

    public OccupationService(ApplicationContext context) 
    {
        repository = context.getBean(OccupationRepository.class);
    }

    @Override
    public OccupationRepository getRepository() {
        return repository;
    }

    @Override
    public List<Occupation> retrieveData() {
        return repository.findAll();
    }
}
