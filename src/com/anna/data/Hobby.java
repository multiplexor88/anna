package com.anna.data;

import com.anna.gui.interfaces.MyCloneable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Hobby")
public class Hobby implements MyCloneable
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="hobby_id")
    private Long id;

    @Column(name="hobby_type")
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Hobby(Hobby other) {
        this.id = other.getId();
        this.type = other.getType();
    }

    public Hobby() {
    }

    public Hobby(Long id, String type)
    {
        this.id = id;
        this.type = type;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hobby other = (Hobby) obj;
        /*if (!Objects.equals(this.id, other.id)) {
            return false;
        }*/
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }
    
    @Transient
    @Override
    public Object clone()
    {
        Hobby other = new Hobby();
        other.setId(id);
        other.setType(type);
        return other;
    }
}
