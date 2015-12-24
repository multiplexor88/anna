/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;

import com.anna.gui.interfaces.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 *
 * @author igor
 */
public class TextAreaController extends AbstractController
{
    @FXML
    private TextArea    textAreaTxt;

    @Override
    public Object getData() {
        return textAreaTxt.getText();
    }

    @Override
    public void setData(Object data) {
        textAreaTxt.setText((String)data);
    }
}
