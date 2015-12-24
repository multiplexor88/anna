/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.data;

import com.anna.gui.interfaces.MyCloneable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.context.annotation.Lazy;

/**
 *
 * @author igor
 */
@Entity
@Table(name="Event")
public class Event implements MyCloneable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;
    
    @Column(name = "event_date")
    private String date;
    
    @Column(name = "event_name")
    private String name;
    
    @Column(name = "event_description")
    private String description;
    
    @Lazy
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "event_person", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> personList = new ArrayList();
    
    @Lazy
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "event_hobby", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "hobby_id"))
    private List<Hobby> hobbyList = new ArrayList();
    
    @Lazy
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "event_occupation", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "occupation_id"))
    private List<Occupation> occupationList = new ArrayList();

    public Event() 
    {
        date = LocalDate.now().toString();
    }

    public Event(Long id, String date, String name, String description, ArrayList<Person> personList, ArrayList<Hobby> hobbyList, ArrayList<Occupation> occupationList) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.description = description;
        this.personList = personList;
        this.hobbyList = hobbyList;
        this.occupationList = occupationList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(ArrayList<Person> personList) {
        this.personList = personList;
    }

    public List<Hobby> getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(ArrayList<Hobby> hobbyList) {
        this.hobbyList = hobbyList;
    }

    public List<Occupation> getOccupationList() {
        return occupationList;
    }

    public void setOccupationList(ArrayList<Occupation> occupationList) {
        this.occupationList = occupationList;
    }

    @Override
    public Object clone() 
    {
            Event evnt = new Event();
            evnt.setId(id);
            evnt.setDate(date);
            evnt.setName(name);
            evnt.setDescription(description);
            
            if(personList != null)
            {
                ArrayList a = new ArrayList();
                for(Person p:personList)
                    a.add(p.clone());
                evnt.setPersonList(a);
            }
            else
                evnt.setPersonList(new ArrayList());
            
            if(hobbyList != null)
            {
                ArrayList h = new ArrayList();
                for(Hobby p:hobbyList)
                    h.add(p.clone());
                evnt.setHobbyList(h);
            }
            else
                evnt.setHobbyList(new ArrayList());
            
            if(occupationList != null)
            {
                ArrayList o = new ArrayList();
                for(Occupation p:occupationList)
                    o.add(p.clone());
                evnt.setOccupationList(o);
            }
            else
                evnt.setOccupationList(new ArrayList());
            
            return evnt;
    }
    
    @Transient
    public boolean isEmpty()
    {
        return this.id == null && this.name == null && this.date == null;
    }
}
