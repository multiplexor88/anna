/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.gui.interfaces.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 *
 * @author igor
 */
public class PropertiesController extends AbstractController
{
    @FXML
    private TextField           smtpTxt,
                                portTxt,
                                fromAddressTxt,
                                passwordTxt;
    
    @FXML
    private CheckBox            sslCheckBox;
    
    //default values
    private String              smtp, port, fromAddress, password;
    private boolean             ssl;

    public TextField getSmtpTxt() {
        return smtpTxt;
    }

    public void setSmtpTxt(TextField smtpTxt) {
        this.smtpTxt = smtpTxt;
    }

    public TextField getFromAddressTxt() {
        return fromAddressTxt;
    }

    public void setFromAddressTxt(TextField fromAddressTxt) {
        this.fromAddressTxt = fromAddressTxt;
    }

    public TextField getPortTxt() {
        return portTxt;
    }

    public void setPortTxt(TextField portTxt) {
        this.portTxt = portTxt;
    }

    public TextField getPasswordTxt() {
        return passwordTxt;
    }

    public void setPasswordTxt(TextField passwordTxt) {
        this.passwordTxt = passwordTxt;
    }

    public CheckBox getSslCheckBox() {
        return sslCheckBox;
    }

    public void setSslCheckBox(CheckBox sslCheckBox) {
        this.sslCheckBox = sslCheckBox;
    }

    @Override
    public void onOk() 
    {
       smtp = smtpTxt.getText();
       fromAddress = fromAddressTxt.getText();
       port = portTxt.getText();
       password = passwordTxt.getText();
       ssl = sslCheckBox.isSelected();
       
       stage.hide();
    }

    
    @Override
    public void onCancel() 
    {
        //set default data
        smtpTxt.setText(smtp);
        fromAddressTxt.setText(fromAddress);
        portTxt.setText(port);
        passwordTxt.setText(password);
        sslCheckBox.setSelected(ssl);
        
        stage.hide();
    }
    
    
}
