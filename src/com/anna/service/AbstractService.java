/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.service;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author igor
 */
abstract public class AbstractService<classType>
{
    //this data consists temporal data extracted from database by retrieveData()
    //currently deprecated
    protected List<classType> data;
    
    //retrieve data from data base
    abstract public List<classType> retrieveData();
    
    abstract public JpaRepository getRepository();

    public List<classType> getData() {
        return retrieveData();
    }
}
