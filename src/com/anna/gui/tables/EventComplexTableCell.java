/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.tables;

import com.anna.data.Event;
import com.anna.gui.controllers.ListDataTableController;
import com.anna.gui.controllers.SingleColumnController;
import com.anna.gui.controllers.TextAreaController;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.AbstractTable;
import com.anna.service.DataBaseService;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

/**
 *
 * @author igor
 */
public class EventComplexTableCell<S,T> extends TableCell<S, String> 
{
    private Button                              button                          = new Button("...");
    private String                              target;
    private DataBaseService                dataBaseService;
    
    private AbstractController                  listDataTabController,
                                                singleColumnController,
                                                textAreaController;
    
    private AbstractTable                       targetTable;
    
    //for loading dialog window
    private FXMLLoader                          listDataTabLoader               = new FXMLLoader(),
                                                singleColumnLoader              = new FXMLLoader(),
                                                textAreaLoader                  = new FXMLLoader();
    
    public EventComplexTableCell(final String target, DataBaseService dataBaseService)
    {
        this.target = target;
        this.dataBaseService = dataBaseService;
    }
    
    @Override
    protected void updateItem(String item, boolean empty) 
    {
        super.updateItem(item, empty);
        setGraphic(button);
        button.setOnAction((ActionEvent event) -> {
            ResourceBundle rb = ResourceBundle.getBundle("com.anna.bundles.locale", new Locale("en"));
            singleColumnController = AbstractController.initializeController(singleColumnLoader, SingleColumnController.class, "../fxml/SingleColumn.fxml");
            textAreaController = AbstractController.initializeController(textAreaLoader, TextAreaController.class, "../fxml/TextArea.fxml");
            listDataTabController = AbstractController.initializeController(listDataTabLoader, ListDataTableController.class, "../fxml/listDataTable.fxml");
            int i = getTableRow().getIndex();
            //List<Event> eventList = dataBaseService.getEventService().getData();
            List<Event> eventList = (List<Event>) getTableView().getItems();
            /*check out of bounds exception*/
            if(i >= eventList.size())
                return;
            Event evnt = eventList.get(i);
            List outList = null, inList = null;
            if(target.equals("description"))
            {
                textAreaController.setData(evnt.getDescription());
                textAreaController.showDialog(event, evnt.getName(), dataBaseService);
                String newDescription = (String) textAreaController.getData();
                evnt.setDescription(newDescription);
                dataBaseService.getEventService().getRepository().save(evnt);
            }
            else if(target.equals("personList"))
            {
                inList = evnt.getPersonList();
                targetTable = new PersonsTable(dataBaseService, rb.getString("key.event.people"), "Event_PersonList");
                targetTable.create( inList,
                        new String[]{   rb.getString("key.person.firstName"), 
                                        rb.getString("key.person.lastName"), 
                                        rb.getString("key.person.patronymic")},
                        new String[]{"firstName", "lastName", "patronymic"});
                
                //targetTable.getTableView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listDataTabController.showDialog(event, targetTable, dataBaseService);
                outList = (List) listDataTabController.getData();
                
            }
            else
            {
                if(target.equals("occupationList"))
                {
                    targetTable = new Occupation_FullTable(dataBaseService,rb.getString("key.event.occupations"));
                    inList = evnt.getOccupationList();
                    targetTable.create( inList,
                            new String[]{rb.getString("key.occupation.title")},
                            new String[]{"id"});
                }
                else if(target.equals("hobbyList"))
                {
                    targetTable = new Hobby_FullTable(dataBaseService, rb.getString("key.event.hobbies"));
                    inList = evnt.getHobbyList();
                    targetTable.create( inList,
                            new String[]{rb.getString("key.hobby.title")},
                            new String[]{"id"});
                }
                
                singleColumnController.setData(inList);
                singleColumnController.showDialog(event, targetTable, dataBaseService);
                outList = (List) singleColumnController.getData();
            }
            if(outList != null)
            {
                inList.clear();
                inList.addAll(outList);
                dataBaseService.getEventService().getRepository().save(evnt);
                getTableView().getItems().set(i, (S)evnt);
            }
        });
    }
}

