package com.anna.gui.commands;

/*Some info
  
//add listeners
        Button homeBtn = new Button("Home");
        homeBtn.setMaxHeight(Double.MAX_VALUE);
        EventHandler<MouseEvent> homeBtnHandler = e->onHomeBtnClicked();
        homeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, homeBtnHandler);
        
        Button personsBtn = new Button("Persons");
        personsBtn.setMaxHeight(Double.MAX_VALUE);
        EventHandler<MouseEvent> personsBtnHandler = e->onPersonsBtnClicked();
        personsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, personsBtnHandler);
        
        Button holidaysBtn = new Button("Holidays");
        holidaysBtn.setMaxHeight(Double.MAX_VALUE);
        EventHandler<MouseEvent> holidayBtnHandler = e->onHolidayBtnClicked();
        holidaysBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, holidayBtnHandler);
        
        Button addressesBtn = new Button("Addresses");
        addressesBtn.setMaxHeight(Double.MAX_VALUE);
        EventHandler<MouseEvent> addressBtnHandler = e->onAddressBtnClicked();
        addressesBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, addressBtnHandler);
        
        Button contactsBtn = new Button("Contacts");
        contactsBtn.setMaxHeight(Double.MAX_VALUE);
        EventHandler<MouseEvent> contactBtnHandler = e->onContactBtnClicked();
        contactsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, contactBtnHandler);
        
        Button hobbiesBtn = new Button("Hobbies");
        hobbiesBtn.setMaxHeight(Double.MAX_VALUE);
        EventHandler<MouseEvent> hobbyBtnHandler = e->onHobbyBtnClicked();
        hobbiesBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, hobbyBtnHandler);
        
        Button occupationsBtn = new Button("Occupations");
        occupationsBtn.setMaxHeight(Double.MAX_VALUE);
        EventHandler<MouseEvent> occupationBtnHandler = e->onOccupationBtnClicked();
        occupationsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, occupationBtnHandler);
        
        basePane.getChildren().addAll(homeBtn, personsBtn, holidaysBtn, addressesBtn, contactsBtn, hobbiesBtn, occupationsBtn); 



/**
Composed table creation and using:
@FXML
    private void onHomePressed()
    {
        List<Person> lp = dataBaseService.getPersonService().getData();
        
        table = new Person_ShortTable(dataBaseService);
        table.create(   lp, 
                        new String[]{"First name", "Last name", "Cell number"}, 
                        new String[]{"firstName", "lastName", "cellNumber"});

        nestedListDataTableController.setTable(table);
        nestedListDataTableController.attachOnMouseClickedListenerForAddAndEdit();                  
    }

public void onAdd(Event event)
    {
        Person person = null;
        String tabName = table.getTypeName();
        
        if(tabName.equals("Home") || tabName.equals("Person"))
        {
            personFormController = initializeController(personFormLoader, "../fxml/Person.fxml");
            personFormController.clearContext();
            personFormController.showDialog(event, table);
            person = (Person)personFormController.getData();
            
            if(person != null && !person.isEmpty())
            {
                if(tabName.equals("Home"))
                {
                    Map<String, Object> map = new HashMap<>();
                    map.put("firstName", person.getFirstName());
                    map.put("lastName", person.getLastName());
                    String tmpContact = person.getContact() == null ? "no data" : person.getContact().getCellNumber();
                    map.put("cellNumber", tmpContact);
                
                    personFormController.getTable().getTableView().getItems().add(map);
                }
                else
                {
                    personFormController.getTable().getTableView().getItems().add(person);
                }
                table.getDataBaseService().getPersonService().getRepository().save(person);
            }  
        }
        else if(tabName.equals("Hobby") || tabName.equals("Occupation"))
        {
            singleAddFormController = initializeController(singleAddFormLoader, "../fxml/SingleAdd.fxml");
            singleAddFormController.clearContext();
            singleAddFormController.showDialog(event, "Add new " + table.getTypeName());
            
            JpaRepository repo = table.getDataBaseService().getServiceByName(tabName).getRepository();
            if(tabName.equals("Hobby"))
            {
                Hobby hobby = new Hobby((String) singleAddFormController.getData());
                if(hobby.getId() != null && !hobby.getId().isEmpty() && !repo.exists(hobby.getId()))
                {
                    repo.save(hobby);
                    table.getTableView().getItems().add(hobby);
                }
            }
            else
            {
                Occupation occupation = new Occupation((String) singleAddFormController.getData());
                if(occupation.getId() != null && !occupation.getId().isEmpty() && !repo.exists(occupation.getId()))
                {
                    repo.save(occupation);
                    table.getTableView().getItems().add(occupation);
                }
            }
        }
        table.getTableView().getSelectionModel().clearSelection();
    }

 @Override
    public void onDelete(Event event)
    {
        TableView tabView = table.getTableView();
        Object item = tabView.getSelectionModel().getSelectedItem();
        if(item != null)
        {
            if(table.getTypeName().equals("Home"))
            {
                Person person = table.getDataBaseService().getPersonService().
                        getRepository().findPersonByFirstNameAndLastName((String)((Map)item).
                                get("firstName"), (String) ((Map)item).get("lastName"));
                if(person != null)
                   table.getDataBaseService().getServiceByName(table.getTypeName()).getRepository().delete(person); 
            }
            else
            {
                table.getDataBaseService().getServiceByName(table.getTypeName()).getRepository().delete(item);
            }
            tabView.getItems().remove(item);
            tabView.getSelectionModel().clearSelection();
        }
    }


*/