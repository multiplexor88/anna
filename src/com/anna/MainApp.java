/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna;

import com.anna.gui.controllers.ControllerFactory;
import com.anna.gui.interfaces.AbstractController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

/**
 *
 * @author igor
 */
public class MainApp extends Application
{
    AbstractController controller;
    //private static final Logger LOG = LogManager.getLogger(MainApp.class);
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        //LOG.debug("Starts************************");
        controller = ControllerFactory.getInstance().create(ControllerFactory.ControllerType.SHORT_MODE);
        
        Pane root = controller.getParent();
        //render scene
        //double ww = controller.getParent().getPrefWidth();
        //double hh = controller.getParent().getPrefHeight();
        
        double ww = Screen.getPrimary().getBounds().getWidth()/4;
        double hh = Screen.getPrimary().getBounds().getHeight()/3;
        
        root.setPrefSize(ww, hh);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        
        controller.setStage(primaryStage);
        //updateTime(controller.getTimeLabel());
        //primaryStage.setResizable(false);
        //primaryStage.setResizable(value);
        primaryStage.show();
    }
        
    public static void main(String[] args) 
    {
        launch(args);
    }
}

