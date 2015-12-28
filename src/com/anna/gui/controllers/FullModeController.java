package com.anna.gui.controllers;
import com.anna.gui.controllers.ControllerFactory.ControllerType;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.TableSearchStrategy;
import com.anna.gui.strategies.CurrentEventsTableSearchStrategy;
import com.anna.gui.strategies.EventsTableSearchStrategy;
import com.anna.gui.strategies.PeopleTableSearchStrategy;
import com.anna.gui.tables.TableFactory;
import com.anna.gui.tables.TableFactory.TableType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * MainWindowController
 * Displays full mode
 */

/**
 *
 * @author Igor Dumchykov
 */
public class FullModeController extends AbstractController
{
    /**
     * parent pane is an instance of BorderPane
     */
    @FXML
    private TabPane                             tabPane;
    
    @FXML
    Button                                      displayTimeBtn;
    
    Service<String>                             displayTimeService;

    /*detects when initialize method is completed*/
    private boolean                             isInitializationCompleted;
    
    private AbstractController                  shortModeController;
    
    private ListDataTableController             curTabController;
    
    /**
     * Update table data when switch from short mode to full mode
     */
    public void updateData()
    {
        TableType tableId = table.getTableId();
        if(tableId.equals(TableType.EVENTS_NAME_DESCRIPT_PERSONS))
        {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            List items = DataLoader.getDataBaseService().getEventService().getRepository().findByDate(date); 
            table.getTableView().setItems(FXCollections.observableArrayList(FXCollections.observableArrayList(items)));
        }
        else
            table.getTableView().setItems(FXCollections.observableArrayList((List)DataLoader.getDataBaseService().getServiceByTableType(tableId).getRepository().findAll()));
    }
    
    @FXML
    protected void initialize()
    {
        isInitializationCompleted = true;   
    }
        
    /**
     * Displays time
     * Called from shortModeController, before fullMOdeController displaying
     */
    public void startTimeService()
    {
        if(displayTimeService == null)
        {
            /*update time using Service class*/
            displayTimeService = new Service<String>() {
                @Override
                protected Task<String> createTask() {
                    return new Task<String>() {
                        @Override
                        protected String call() throws Exception 
                        {
                            String timeStr;

                            while(true)
                            {
                                LocalDateTime time = LocalDateTime.now();
                                time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                                timeStr =    time.getYear() + "-" +
                                        time.getMonth().getValue() + "-" +
                                        time.getDayOfMonth() + " " +
                                        time.getDayOfWeek().name() + "  " +
                                        time.getHour() + ":" +
                                        time.getMinute() + ":" +
                                        time.getSecond();

                                updateValue(timeStr);
                                Thread.sleep(1000);
                            }
                        }
                    };
                }
            };
            displayTimeBtn.textProperty().bind(displayTimeService.valueProperty());
        }
        displayTimeService.start();
    }
    
    /**
     * Stops time displaying
     * Called from this controller, before shorModeController displaying
     */
    public void cancelTimeService()
    {
        displayTimeService.cancel();
        displayTimeService.reset();
    }
    
    @Override
    public void onOk(){System.exit(0);};
    
    @Override
    public void onCancel(){System.exit(0);};
    
    /**
     * Switch on short mode: constructs and calls CurrentEventsController instance
     * @param event 
     */
    @FXML
    private void onShortMode(ActionEvent event)
    {
        /*close current stage*/
        super.onOk();
        cancelTimeService();
        
        shortModeController = ControllerFactory.getInstance().create(ControllerType.SHORT_MODE);
        ((ShortModeController)shortModeController).onEvents();
        shortModeController.getStage().show();
    }
    
    /**
     * Set or update start view (CurrentEvents)
     */
    public void setStartView()
    {
         //attach default tab (current events)
        curTabController = (ListDataTableController) ControllerFactory.getInstance().create(ControllerType.EVENT_LIST);
        
        
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        List items = DataLoader.getDataBaseService().getEventService().getRepository().findByDate(date); 
        
        table = TableFactory.create(TableType.EVENTS_NAME_DESCRIPT_PERSONS);
        //table.getTableView().setItems(FXCollections.observableArrayList(items));
        curTabController.setTable(table);
        curTabController.setStrategy(TableSearchStrategy.getStrategyByTableType(table));
        curTabController.setData(FXCollections.observableArrayList(items));
        
        Pane pane = curTabController.getParent();
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        ((AnchorPane)tabPane.getTabs().get(0).getContent()).getChildren().add(curTabController.getParent());
        
        curTabController.getOkBtn().setVisible(false);
        curTabController.getCancelBtn().setVisible(false);
        /*clear searching space, because maybe there is some text there*/

        
         /*attach listener for add and edit commands*/
    }
    
    /**
     * Processes tab panle buttons
     * Construct listDataTableController by table
     * @param e: indicates which button was called
     */
    @FXML
    private void onSelectionChanged(Event e) 
    {
        if(!isInitializationCompleted)
            return;
        
        Tab srcTab = (Tab)e.getSource();
        boolean isSelected = srcTab.isSelected();
        
        if(!isSelected)      //detach child
        {
            ((AnchorPane)srcTab.getContent()).getChildren().clear();
            return;
        }
        
        List items = null;
        TableType tableId = TableType.valueOf(srcTab.getId());
        switch(tableId)
        {
            case EVENTS_NAME_DESCRIPT_PERSONS:
                curTabController = (ListDataTableController) ControllerFactory.getInstance().create(ControllerType.EVENT_LIST);
                String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                table = TableFactory.create(TableType.EVENTS);
                curTabController.setTable(table);
                curTabController.setStrategy(TableSearchStrategy.getStrategyByTableType(table));
                items = DataLoader.getDataBaseService().getEventService().getRepository().findByDate(date); 
                break;
            case EVENTS:
                curTabController = (ListDataTableController) ControllerFactory.getInstance().create(ControllerType.EVENT_LIST);
                table = TableFactory.create(TableType.EVENTS);
                curTabController.setTable(table);
                curTabController.setStrategy(TableSearchStrategy.getStrategyByTableType(table));
                items = DataLoader.getDataBaseService().getServiceByTableType(tableId).getRepository().findAll();
                break;
            case PERSONS_FULL:
                curTabController = (ListDataTableController) ControllerFactory.getInstance().create(ControllerType.PEOPLE);
                table = TableFactory.create(TableType.PERSONS_FULL);
                curTabController.setTable(table);
                curTabController.setStrategy(TableSearchStrategy.getStrategyByTableType(table));
                items = DataLoader.getDataBaseService().getServiceByTableType(tableId).getRepository().findAll();
                break;
            case HOBBIES:
                curTabController = (ListDataTableController) ControllerFactory.getInstance().create(ControllerType.FULL_MODE_HOBBIES);
                //table strategy was set in builder
                items = DataLoader.getDataBaseService().getServiceByTableType(tableId).getRepository().findAll();
                break;
            case OCCUPATIONS:
                curTabController = (ListDataTableController) ControllerFactory.getInstance().create(ControllerType.FULL_MODE_OCCUPATIONS);
                //table strategy was set in builder
                items = DataLoader.getDataBaseService().getServiceByTableType(tableId).getRepository().findAll();
                break;
        }
        
        ((ListDataTableController)curTabController).getSearchTxt().clear();
        curTabController.setData(FXCollections.observableArrayList(items));
        
        /*must save data immediately*/
        //curTabController.setParentController(null);
        
        curTabController.getOkBtn().setVisible(false);
        curTabController.getCancelBtn().setVisible(false);
        
        Pane pane = curTabController.getParent();
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        ((AnchorPane)srcTab.getContent()).getChildren().add(pane);
    }

    /**
     * The simplest way to update label in another thread
     * @param label 
     */
    private void updateTime(Labeled label) 
    {
        Thread dispTimeThread = new Thread(()->
        {
            System.err.println("START THREAD");
            while(true)
            {
                try {
                    LocalDateTime time = LocalDateTime.now();
                    time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    String timeStr =    time.getYear() + "-" +
                            time.getMonth().getValue() + "-" +
                            time.getDayOfMonth() + " " +
                            time.getDayOfWeek().name() + "  " +
                            time.getHour() + ":" +
                            time.getMinute() + ":" +
                            time.getSecond();
                    
                    Platform.runLater(()->label.setText(timeStr));
                    
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FullModeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        dispTimeThread.setDaemon(true);
        dispTimeThread.start();
    }
}

/**
 * Using Task class is appropriate for just one-time running
 * @author igor
 */
 class DisplayTimeTask extends Task<String>
 {
     private Boolean isRun;

     public DisplayTimeTask(Boolean isRun)
     {
         this.isRun = isRun;
     }

     @Override
     protected String call() throws Exception 
     {
         String timeStr = null;

         while(isRun)
         {
             LocalDateTime time = LocalDateTime.now();
             time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
             timeStr =    time.getYear() + "-" +
                     time.getMonth().getValue() + "-" +
                     time.getDayOfMonth() + " " +
                     time.getDayOfWeek().name() + "  " +
                     time.getHour() + ":" +
                     time.getMinute() + ":" +
                     time.getSecond();

             updateValue(timeStr);
             Thread.sleep(1000);
         }
         return timeStr;
     }
 }
//<fx:include source="listDataTable.fxml" fx:id="nestedListDataTable"/>
