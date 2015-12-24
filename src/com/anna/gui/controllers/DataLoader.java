/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.gui.interfaces.AbstractController;
import com.anna.service.DataBaseService;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;

/**
 * Loads controllers, language resources, database
 * @author igor
 */
public class DataLoader 
{
    private DataLoader(){}
    
    static private DataLoader                       instance;
    
    static private DataBaseService                  dataBaseService;

    public static DataBaseService getDataBaseService() {
        return dataBaseService;
    }
    
    /*load language resources*/
    static private ResourceBundle langResources = ResourceBundle.getBundle("com.anna.bundles.locale", new Locale("en"));

    public static ResourceBundle getLangResources() {
        return langResources;
    }

    public static DataLoader getInstance() 
    {
        if(instance == null)
        {
            /*load database*/
            dataBaseService = new DataBaseService();
            dataBaseService.initializeServices("application-context.xml");
            
            /*create loader*/
            instance = new DataLoader();
        }
        
        return instance;
    }
    
     /**
     * loads controller. Loader must be initialized outside (new FXMLLoader())
     * @param c
     * @param path 
     * @return  
     */
    public AbstractController loadController(Class c, String path)
    {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(c.getResource(path));
            loader.setResources(langResources);
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return loader.getController();
    }
}
