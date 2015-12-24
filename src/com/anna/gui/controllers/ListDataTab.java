/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.gui.controllers;
import com.anna.data.Hobby;
import com.anna.data.Occupation;
import com.anna.data.Person;
import com.anna.gui.tables.EventsTable;
import com.anna.gui.tables.PersonsTable;
import com.anna.gui.interfaces.AbstractController;
import com.anna.gui.interfaces.AbstractTable;
import com.anna.gui.interfaces._Cloneable;
import com.anna.service.DataBaseService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains list information, that can not be written as single cell
 * @author multiplexor88
 */
public class ListDataTab extends AbstractController 
{
    @FXML
    private Button              addBtn,
                                editBtn,
                                deleteBtn;

    public void disableAddBtn(){addBtn.setDisable(true);}
    public void disableEditBtn(){editBtn.setDisable(true);}
    public void disableDeleteBtn(){deleteBtn.setDisable(true);}
    
    
    @Override
    public List<_Cloneable> getData() {
        return original;
    }

    @Override
    public void setData(Object original) 
    {
        this.original = (List<_Cloneable>)original;
        
        copy = new ArrayList();
        //create copy
        for(_Cloneable c:this.original)
            copy.add((_Cloneable) c.clone());
    }
    
    private List<_Cloneable>        original, copy;
    
    private final FXMLLoader        singleAddFormLoader                         = new FXMLLoader(),
                                    personFormLoader                            = new FXMLLoader(),
                                    listDataTabLoader                           = new FXMLLoader(),
                                    eventLoader                                 = new FXMLLoader();
    
    private AbstractController      singleAddFormController,
                                    personFormController,
                                    listDataTabController,
                                    eventController;
    
    @FXML
    protected void initialize()
    {
        super.initialize();
    }
    
    @FXML
    private TextField           searchTxt;

    public TextField getSearchTxt() {
        return searchTxt;
    }
    
    @Override
    public void showDialog(Event event, AbstractTable table, DataBaseService dbs)
    {
        dataBaseService = dbs;
        copy = table.getTableView().getItems();
        this.table = table;
        attachOnOkBtnClicked();
        table.getTableView().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        loadStage(event);
        stage.setTitle(table.getTableName()); 
        
        ((Pane)parent).getChildren().set(0, table.getTableView());
        
        stage.showAndWait();
    }
    
    /**
     * ADD values to tables: Persons, Hobbies, Occupations, AllEvents, Persons, Current event
     * @param event 
     */
    @Override
    public void onAdd(Event event)
    {
        Person person = null;
        String id = table.getTableId();
        
        if(id.equals("Persons"))
        {
            //add person to data base
            personFormController = initializeController(personFormLoader, PersonController.class, "../fxml/Person.fxml");
            personFormController.clearContext();
            personFormController.showDialog(event, langResources.getString("key.persons.title"), dataBaseService);
            person = (Person)personFormController.getData();
            
            if(person != null && !person.isEmpty())
            {
                table.getTableView().getItems().add(person);
                dataBaseService.getPersonService().getRepository().save(person);
            }  
        }
        else if(id.equals("Hobbies") || id.equals("Occupations"))
        {
            //add hobby or occupation to data base
            singleAddFormController = initializeController(singleAddFormLoader, SingleAddController.class, "../fxml/SingleAdd.fxml");
            singleAddFormController.clearContext();
            JpaRepository repo = dataBaseService.getServiceByName(id).getRepository();
            
            if(id.equals("Hobbies"))
            {
                singleAddFormController.showDialog(event, langResources.getString("key.listDataTable.add") + " " + langResources.getString("key.hobby.title"), dataBaseService);
                Hobby hobby = new Hobby((String) singleAddFormController.getData());
                if(hobby.getId() != null && !hobby.getId().isEmpty() && !repo.exists(hobby.getId()))
                {
                    repo.save(hobby);
                    table.getTableView().getItems().add(hobby);
                }
            }
            else
            {
                singleAddFormController.showDialog(event, langResources.getString("key.listDataTable.add") + " " + langResources.getString("key.occupation.title"), dataBaseService);
                Occupation occupation = new Occupation((String) singleAddFormController.getData());
                if(occupation.getId() != null && !occupation.getId().isEmpty() && !repo.exists(occupation.getId()))
                {
                    repo.save(occupation);
                    table.getTableView().getItems().add(occupation);
                }
            }
        }
        else if(id.equals("AllEvents") || id.equals("CurrentEvents"))
        {
            //add new event to data base
            eventController = initializeController(eventLoader, EventController.class, "../fxml/Event.fxml");
            eventController.setDataBaseService(dataBaseService);
            eventController.clearContext();
            eventController.showDialog(event, langResources.getString("key.currentEvents.title"), dataBaseService);
            com.anna.data.Event evnt = (com.anna.data.Event) eventController.getData();
            if(!evnt.isEmpty())
            {
                dataBaseService.getEventService().getRepository().save(evnt);
                table.getTableView().getItems().add(evnt);
            }
        }
        else if(id.equals("Event_PersonList"))
        {
            //add persons to current event
            listDataTabController = initializeController(listDataTabLoader, ListDataTableController.class, "../fxml/listDataTable.fxml");
            List outList = dataBaseService.getPersonService().getData();
            AbstractTable targetTable = new PersonsTable(dataBaseService, langResources.getString("key.persons.title"), id);
            targetTable.create( outList,
                    new String[]{   langResources.getString("key.person.firstName"), 
                                    langResources.getString("key.person.lastName"), 
                                    langResources.getString("key.person.patronymic")},
                    new String[]{"firstName", "lastName", "patronymic"});
            
            //table.getTableView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            ((ListDataTableController)listDataTabController).disableAddBtn();
            ((ListDataTableController)listDataTabController).disableEditBtn();
            ((ListDataTableController)listDataTabController).disableDeleteBtn();
            
            listDataTabController.showDialog(event, targetTable, dataBaseService);
            List oList = targetTable.getTableView().getSelectionModel().getSelectedItems();
            
            /**
             * Check if selected items do not match existed items in table
             */
            validateOnExisting(oList, copy, false);
        }
        
        table.getTableView().getSelectionModel().clearSelection();
    }
    
    @Override
    public void onEdit(Event event)
    {
        Person person = null;
        String id = table.getTableId();
        int index = table.getTableView().getSelectionModel().getSelectedIndex();
        Object item = table.getTableView().getSelectionModel().getSelectedItem();
        
        if(index >= 0)
        {
            if(id.equals("Persons"))
            {
                personFormController = initializeController(personFormLoader, PersonController.class, "../fxml/Person.fxml");
                personFormController.setData(item);
                personFormController.setDataBaseService(dataBaseService);
                personFormController.showDialog(event, langResources.getString("key.person.title"), dataBaseService);
                person = (Person)personFormController.getData();
                if(person != null && !person.isEmpty())
                {
                    table.getTableView().getItems().set(index, person);
                    dataBaseService.getPersonService().getRepository().save(person);
                }  
            }
            else if(id.equals("AllEvents") || id.equals("CurrentEvents"))
            {
                eventController = initializeController(eventLoader, EventController.class, "../fxml/Event.fxml");
                eventController.setDataBaseService(dataBaseService);
                eventController.setData(item);
                eventController.showDialog(event, table, dataBaseService);
                com.anna.data.Event evnt = (com.anna.data.Event) eventController.getData();
                if(!evnt.isEmpty())
                {
                    dataBaseService.getEventService().getRepository().save(evnt);
                    table.getTableView().getItems().set(index, evnt);
                }
            }
            else if(id.equals("Hobbies") || id.equals("Occupations"))
            {
                singleAddFormController = initializeController(singleAddFormLoader, SingleAddController.class, "../fxml/SingleAdd.fxml");
                JpaRepository repo = dataBaseService.getServiceByName(id).getRepository();
                JpaRepository personRepo = dataBaseService.getPersonService().getRepository();
                if(id.equals("Hobbies"))
                {
                    Hobby oldHobby = ((Hobby)item);
                    singleAddFormController.setData(oldHobby.getId());
                    singleAddFormController.showDialog(event, langResources.getString("key.listDataTable.Edit") + langResources.getString("key.hobby.title"), dataBaseService);
                    Hobby newHobby = new Hobby((String) singleAddFormController.getData());
                    boolean b = repo.exists(newHobby.getId());
                    if(!b)
                    {
                        table.getTableView().getItems().set(index, newHobby);

                        //check if persons have oldValue, change on newValue
                        List<Person> persons = personRepo.findAll();

                        for(Person p:persons)
                        {
                            List<Hobby> hobbies = p.getHobbyList();
                            for(Hobby h:hobbies)
                            {
                                if(h.getId().equals(oldHobby.getId()))
                                {
                                    p.getHobbyList().add(newHobby);
                                    personRepo.save(p);
                                    break;
                                }
                            }
                        }
                        repo.save(newHobby);
                        //delete oldValue from data base
                        repo.delete(oldHobby);
                    }
                }
                else
                {
                    Occupation oldOccupation = ((Occupation)item);
                    singleAddFormController.setData(oldOccupation.getId());
                    singleAddFormController.showDialog(event, langResources.getString("key.listDataTable.Edit") + langResources.getString("key.occupation.title"), dataBaseService);
                    Occupation newOccupation = new Occupation((String) singleAddFormController.getData());
                    boolean b = repo.exists(newOccupation.getId());
                    if(!b)
                    {
                        table.getTableView().getItems().set(index, newOccupation);

                        //check if persons have oldValue, change on newValue
                        List<Person> persons = personRepo.findAll();

                        for(Person p:persons)
                        {
                            List<Occupation> occupations = p.getOccupationList();
                            for(Occupation o:occupations)
                            {
                                if(o.getId().equals(oldOccupation.getId()))
                                {
                                    p.getOccupationList().add(newOccupation);
                                    personRepo.save(p);
                                    break;
                                }
                            }
                        }
                        repo.save(newOccupation);
                        //delete oldValue from data base
                        repo.delete(oldOccupation);
                    }
                }
            }
            table.getTableView().getSelectionModel().clearSelection();
        }
    }
    
    @Override
    public void onDelete(Event event)
    {
        TableView tabView = table.getTableView();
        Object item = tabView.getSelectionModel().getSelectedItem();
        if(item != null)
        {
            if(!table.getTableId().equals("Event_PersonList"))//this case for ALLEvents->Person_List
                dataBaseService.getServiceByName(table.getTableId()).getRepository().delete(item);
            
            tabView.getItems().remove(item);
            tabView.getSelectionModel().clearSelection();
        }
    }
    
    @Override
    protected void onKeyPressed(KeyEvent event){}
    
    /**
     * Searching over current table
     * @param keyEvent 
     */
    @FXML
    public void onSearch(KeyEvent keyEvent)
    {
        //get existed text
        String txt = searchTxt.getText();
        
        //get typed character
        String word = keyEvent.getCharacter();
        
        //check if is not a backspace
    //    word = !word.equals("\b") ? word : "";
        
        //find current word in the table
        String id = table.getTableId();
        List dataList;
        if(id.equals("Persons") || id.equals("Event_PersonList"))
        {
            if(word.equals("\b") && txt.isEmpty())
            {
                dataList = dataBaseService.getPersonService().getData();
                table = new PersonsTable(dataBaseService, langResources.getString("key.persons.title"), id);
                table.create(   dataList, 
                                new String[]{   langResources.getString("key.person.firstName"), 
                                                langResources.getString("key.person.lastName"), 
                                                langResources.getString("key.person.patronymic"), 
                                                langResources.getString("key.person.gender"), 
                                                langResources.getString("key.person.address"), 
                                                langResources.getString("key.contact.title"),
                                                langResources.getString("key.hobby.title"),
                                                langResources.getString("key.occupation.title")}, 
                                new String[]{   "firstName", 
                                                "lastName", 
                                                "patronymic", 
                                                "gender", "addressList","contact","hobbyList","occupationList"});
                attachOnAddBtnClicked();
            }
            else//searching by last name
            {
                dataList = dataBaseService.getPersonService().getRepository().findPersonByLastNameLike((txt+word).trim() + "%");
                table = new PersonsTable(dataBaseService, langResources.getString("key.persons.title"), id);
                table.create(   dataList, 
                                new String[]{   langResources.getString("key.person.firstName"), 
                                                langResources.getString("key.person.lastName"), 
                                                langResources.getString("key.person.patronymic"), 
                                                langResources.getString("key.person.gender"), 
                                                langResources.getString("key.person.address"), 
                                                langResources.getString("key.contact.title"),
                                                langResources.getString("key.hobby.title"),
                                                langResources.getString("key.occupation.title")}, 
                                new String[]{"firstName", "lastName", "patronymic", "gender", "addressList","contact","hobbyList","occupationList"});
            }
            attachOnEditBtnClicked();
            
        }
        else if(id.equals("Hobbies"))
        {
            if(word.equals("\b") && txt.isEmpty())
            {
                dataList = dataBaseService.getHobbyService().getData();
                table = new Hobby_FullTable(dataBaseService, langResources.getString("key.event.hobbies"));
                table.create(  dataList, new String[]{langResources.getString("key.hobby.title")}, new String[]{"id"});
                attachOnAddBtnClicked();
            }
            else
            {
                dataList = dataBaseService.getHobbyService().getRepository().findHobbyByIdLike((txt+word).trim() + "%");
                table = new Hobby_FullTable(dataBaseService, langResources.getString("key.event.hobbies"));
                table.create(  dataList, new String[]{langResources.getString("key.hobby.title")}, new String[]{"id"});
            }
            attachOnEditBtnClicked();
            
        }
        else if(id.equals("Occupations"))
        {
            if(word.equals("\b") && txt.isEmpty())
            {
                dataList = dataBaseService.getOccupationService().getData();
                table = new Occupation_FullTable(dataBaseService,langResources.getString("key.event.occupations"));
                table.create(  dataList, new String[]{langResources.getString("key.occupation.title")}, new String[]{"id"});
                attachOnAddBtnClicked();
            }
            else
            {
                dataList = dataBaseService.getOccupationService().getRepository().findOccupationByIdLike((txt+word).trim() + "%");
                table = new Occupation_FullTable(dataBaseService,langResources.getString("key.event.occupations"));
                table.create(  dataList, new String[]{langResources.getString("key.occupation.title")}, new String[]{"id"});
            }
            attachOnEditBtnClicked();
        }
        else if(id.equals("AllEvents"))
        {
            if(word.equals("\b") && txt.isEmpty())
            {
                dataList = dataBaseService.getEventService().getData();
                table = new EventsTable(dataBaseService, langResources.getString("key.events.title"), id);
                table.create(   dataList, 
                                new String[]{   langResources.getString("key.event.date"),
                                                langResources.getString("key.event.name"),
                                                langResources.getString("key.event.description"), 
                                                langResources.getString("key.persons.title"), 
                                                langResources.getString("key.event.hobbies"), 
                                                langResources.getString("key.event.occupations")}, 
                                new String[]{"date", "name", "description", "personList", "hobbyList", "occupationList"});
                attachOnAddBtnClicked();
            }
            else
            {
                dataList = dataBaseService.getEventService().getRepository().findByNameLike((txt+word).trim() + "%");
                 table = new EventsTable(dataBaseService, langResources.getString("key.events.title"), id);
                table.create(   dataList, 
                                new String[]{   langResources.getString("key.event.date"),
                                                langResources.getString("key.event.name"),
                                                langResources.getString("key.event.description"), 
                                                langResources.getString("key.persons.title"), 
                                                langResources.getString("key.event.hobbies"), 
                                                langResources.getString("key.event.occupations")}, 
                                new String[]{"date", "name", "description", "personList", "hobbyList", "occupationList"});
            }
            attachOnEditBtnClicked();
            
        }
        else if(id.equals("CurrentEvents"))
        {
            String ddMM_dateFormatString = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM"));
            
            if(word.equals("\b") && txt.isEmpty())
            {
                dataList = dataBaseService.getEventService().getRepository().findByDateLike(ddMM_dateFormatString + "%");
                table = new EventsTable(dataBaseService, langResources.getString("key.currentEvents.title"), id);
                table.create(   dataList, 
                                new String[]{langResources.getString("key.event.date"),langResources.getString("key.event.name"),langResources.getString("key.event.description")}, 
                                new String[]{"date", "name", "description"});
                attachOnAddBtnClicked();
                
            }
            else
            {
                dataList = dataBaseService.getEventService().getRepository().findByDateLikeAndNameLike(ddMM_dateFormatString + "%", (txt+word).trim() + "%");
                table = new EventsTable(dataBaseService, langResources.getString("key.currentEvents.title"), id);
                table.create(   dataList, 
                                new String[]{langResources.getString("key.event.date"),langResources.getString("key.event.name"),langResources.getString("key.event.description")}, 
                                new String[]{"date", "name", "description"});
            }  
            attachOnEditBtnClicked();
        }
        //update data in outParent table
         ((Pane)parent).getChildren().set(0, table.getTableView());
    }

    @Override
    public void onCancel() 
    {
        original = null;
        stage.hide();
    }

    @Override
    public void onOk() 
    {
        original = copy;
        stage.hide();
    }
    
    
}


