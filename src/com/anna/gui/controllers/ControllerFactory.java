/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.gui.builders.AddressesBuilder;
import com.anna.gui.builders.EventListBuilder;
import com.anna.gui.builders.FullModeHobbiesBuilder;
import com.anna.gui.builders.FullModeOccupationsBuilder;
import com.anna.gui.builders.InternalHobbiesBuilder;
import com.anna.gui.builders.InternalOccupationsBuilder;
import com.anna.gui.builders.PersonsBuilder;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains all controllers that represents different windows
 * @controller types:   INTERNAL_HOBBIES
 *                      MAIN_HOBBIES
 *                      INTERNAL_OCCUPATIONS
 *                      MAIN_OCCUPATIONS
 *                      EVENT
 *                      CURRENT_EVENTS
 *                      PERSON
 *                      ADDRESSES
 *                      CONTACT
 * @author igor
 */
public class ControllerFactory 
{
    public static enum ControllerType{  INTERNAL_HOBBIES,                       //LIST hobbies in internal tables
                                        FULL_MODE_HOBBIES,                      //LIST hobbies in full mode
                                        INTERNAL_OCCUPATIONS,                   //LIST occupations in internal table
                                        FULL_MODE_OCCUPATIONS,                  //LIST occupations in full mode
                                        EVENT,                                  //add event
                                        EVENT_LIST,                             //LIST of events
                                        SHORT_MODE,                             //current events window
                                        PERSON,                                 //add person
                                        ADDRESSES,                              //LIST of addresses
                                        CONTACT,                                //contact
                                        TEXT_AREA,                              //description of event
                                        PEOPLE,                                 //LIST of people related to event(s)
                                        FULL_MODE;                              //full mode window
    };
    
    /*contains controllers*/
    static private Map<ControllerType, AbstractController> controllerPool = new HashMap();
    
    /*contains builders that build controllers*/
    static private Map<ControllerType, Builder> builderPool = new HashMap<>();
    
    /*creates controller according to getting type*/
    public AbstractController create(ControllerType controllerType) 
    {
        Builder builder = builderPool.get(controllerType); 
        
        if(builder == null)
            throw new TypeNotPresentException(controllerType.toString(), null);
        
        AbstractController controller = controllerPool.get(controllerType);
        
        if(controller == null)
        {
            controller = builder.build();
            controllerPool.put(controllerType, controller);
        }
        
        return controller;
    }
    
    static private ControllerFactory instance;

    public static ControllerFactory getInstance() 
    {
        if(instance == null)
        {
            initializeBuilders();
            instance = new ControllerFactory();
        }
        return instance;
    }
    
    private ControllerFactory(){}
    
    /**
     * Loads builders to Map
     */
    static private void initializeBuilders()
    {
        builderPool.put(ControllerType.INTERNAL_HOBBIES, new InternalHobbiesBuilder());
        builderPool.put(ControllerType.FULL_MODE_HOBBIES, new FullModeHobbiesBuilder());
        builderPool.put(ControllerType.INTERNAL_OCCUPATIONS, new InternalOccupationsBuilder());
        builderPool.put(ControllerType.FULL_MODE_OCCUPATIONS, new FullModeOccupationsBuilder());
        builderPool.put(ControllerType.EVENT, (Builder) ()->DataLoader.getInstance().loadController(EventController.class, "../fxml/Event.fxml"));
        builderPool.put(ControllerType.EVENT_LIST, new EventListBuilder());
        builderPool.put(ControllerType.PERSON, (Builder) () -> DataLoader.getInstance().loadController(PersonController.class, "../fxml/Person.fxml"));
        builderPool.put(ControllerType.ADDRESSES, new AddressesBuilder());
        builderPool.put(ControllerType.CONTACT, (Builder) () -> DataLoader.getInstance().loadController(ContactController.class, "../fxml/Contact.fxml"));
        builderPool.put(ControllerType.TEXT_AREA, (Builder) () -> DataLoader.getInstance().loadController(TextAreaController.class, "../fxml/TextArea.fxml"));
        builderPool.put(ControllerType.PEOPLE, new PersonsBuilder());
        builderPool.put(ControllerType.SHORT_MODE, (Builder) () -> DataLoader.getInstance().loadController(ShortModeController.class, "../fxml/ShortMode.fxml"));
        builderPool.put(ControllerType.FULL_MODE, (Builder) () -> DataLoader.getInstance().loadController(FullModeController.class, "../fxml/FullMode.fxml"));
    }
}
