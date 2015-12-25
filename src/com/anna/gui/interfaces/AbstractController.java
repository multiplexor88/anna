/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.interfaces;

import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author igor
 */
abstract public class AbstractController 
{
    public int code;
    /*if this controller has parentController, then it is not responsible
    for saving data to data base
    */
    protected AbstractController parentController;

    public AbstractController getParentController() {
        return parentController;
    }

    public void setParentController(AbstractController parentController) {
        this.parentController = parentController;
    }
    
    /**
     * Stage
     */
    protected Stage                                         stage;
    public Stage getStage()                                 {return stage;}
    public void setStage(Stage st)                          {stage = st;}

    /*src table*/
    protected AbstractTable                                 table;
    
    public void setTable(AbstractTable table) {
        ((Pane)underTableParent).getChildren().set(0, table.getTableView());
        
        AnchorPane.setBottomAnchor(table.getTableView(), 0.0);
        AnchorPane.setLeftAnchor(table.getTableView(), 0.0);
        AnchorPane.setTopAnchor(table.getTableView(), 0.0);
        AnchorPane.setRightAnchor(table.getTableView(), 0.0);
        this.table = table;
    }
    public AbstractTable getTable() {
        return table;
    }
    
    /*Responsible for calling buttons: executes related command*/
    protected ButtonCommand                                 command;
    public ButtonCommand getCommand()                       {return command;}
    public void setCommand(ButtonCommand command)           {this.command = command;}
    
    /*Responsible for searching data when user types text in text field*/
    protected TableSearchStrategy                           strategy;
    public TableSearchStrategy getStrategy()                {return strategy;}
    public void setStrategy(TableSearchStrategy strategy)   {this.strategy = strategy;}
    
    /*original and copy data. Copy needs if user presses Cancel button*/
    protected Object                                         original, copy;
    
    /*src event*/
    protected Event                                         event;
    
    
    /*root pane*/
    @FXML
    protected Pane                                          parent;
    public void setParent(Pane parent) {
        this.parent = parent;
    }
    public Pane getParent() {
        return parent;
    }
    
    /*convinient for attaching table*/
    @FXML
    protected Pane                                          underTableParent;

    public Pane getUnderTableParent() {
        return underTableParent;
    }
    
    
    @FXML
    Button  okBtn, cancelBtn;
    public void hideOkBtn(){okBtn.setVisible(false);}
    public void hideCancelBtn(){cancelBtn.setVisible(false);}
    
    public void loadStage(Event event)
    {
        if(stage == null)
        {
/*          URL url = getClass().getResource("fxml/listDataTable.fxml");
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root));*/
            stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            //stage.setResizable(false);
         } 
    }
    
    public void showDialog(Event event, String title)
    {
        loadStage(event);
        stage.setTitle(title == null ? table.getTableName() : title);
        stage.showAndWait();
    }
    

    /**
     * If user clicks 2 times on current cell, it allows to add/edit new item
     */
    public void attachOnAddBtnClicked()
    {
        EventHandler<MouseEvent> handler = e->
        {
            int count = e.getClickCount();
            if(count == 2)
            {
                int index = table.getTableView().getSelectionModel().getSelectedIndex();
                if(index < 0)
                    onAdd(e);
            }
        };
        table.getTableView().addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }
    
     /**
     * If user clicks 2 times on current cell, it allows to add/edit new item
     */
    public void attachOnEditBtnClicked()
    {
        EventHandler<MouseEvent> handler = e->
        {
            int count = e.getClickCount();
            if(count == 2)
            {
                int index = table.getTableView().getSelectionModel().getSelectedIndex();
                if(index >= 0)
                    onEdit(e);
            }
        };
        table.getTableView().addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }
    
     /**
     * If user clicks 2 times on current cell, it calls onOk method
     */
    public void attachOnOkBtnClicked()
    {
        EventHandler<MouseEvent> handler = e->
        {
            int count = e.getClickCount();
            int index = table.getTableView().getSelectionModel().getSelectedIndex();
            if(count == 2 && index >= 0)
                onOk();
        };
        table.getTableView().addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }
    
    /**
     * Listeners for enter and esc buttons
     * @param event 
     */
    @FXML
    protected void onKeyPressed(KeyEvent event)
    {
        KeyCode keyCode = event.getCode();
        switch(keyCode)
        {
            case ENTER:
                onOk();
                break;
            case ESCAPE:
                onCancel();
                break;
        }
    }
        
    @FXML
    public void onOk()
    {
        if(!saveData())return;
        original = copy;
        stage.close();
    }

    @FXML
    public void onCancel() 
    {
        original = null;
        stage.close();
    }
    
    public void clearContext(){}
    
    public void setData(Object data){}
    
    public Object getData(){return original;}
    
    @FXML
    public void onAdd(Event event){}
    
    @FXML
    public void onEdit(Event event){}
    
    @FXML
    public void onDelete(Event event){}
    
    /**
     * Check if copy Collection has values of oList Collection
     * 
    * here was a problem occured with overriding hashCode and equals methods
    * within Occupation class
    * Therefore, in this case simple cycle is used to verify wether
    * elements are equal or not between each other
     * @param oList
    */
    protected void validateOnExisting(List oList, List copy, boolean isUpdateTable)
    {
        for(Object src:oList)
        {
            boolean answer = false;
            for(Object dst:copy)
            {
                if(src.equals(dst))
                {
                    answer = true;
                    break;
                }
            }
            if(!answer)
            {   
                if(isUpdateTable) table.getTableView().getItems().add(src);
                copy.add(src);
            }
        }
    }    

    /**
     * Fills copy object by data
     * Overrided in children according to data structure
     * @return true if data is valid
     */
    protected boolean saveData()
    {
        return true;/*some controllers do not need*/
    }
 /*   public void setCalledController(AbstractController aThis) 
    {
        mainWindowController = aThis;
    }*/
}
