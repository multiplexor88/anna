/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.builders;

import com.anna.gui.controllers.DataLoader;
import com.anna.gui.controllers.PropertiesController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.Builder;

/**
 *
 * @author igor
 */
public class PropertiesBuilder implements Builder
{

    @Override
    public AbstractController build() 
    {
        PropertiesController controller = (PropertiesController) DataLoader.getInstance().loadController(PropertiesController.class, "../fxml/Properties.fxml");
        
        //set default data
        controller.getSmtpTxt().setText("smtp.ukr.net");
        controller.getFromAddressTxt().setText("multiplexor88@ukr.net");
        controller.getPortTxt().setText("465");
        controller.getPasswordTxt().setText("plya1955");
        controller.getSslCheckBox().setSelected(true);
        
        return controller;
    }
    
}
