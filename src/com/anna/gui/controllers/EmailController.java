/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.gui.interfaces.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author igor
 */
public class EmailController extends AbstractController
{
    @FXML
    private TextField           toAddressTxt,
                                subjectTxt;
    
    @FXML
    private TextArea            message;
    
    
}
