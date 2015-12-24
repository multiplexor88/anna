package com.anna.data;

import com.anna.gui.interfaces.MyCloneable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="occupation")
public class Occupation implements MyCloneable
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="occupation_id")
    private Long id;

    @Column(name="occupation_type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public Long getId() {
	return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Occupation(String type) {
        this.type = type;
    }

    public Occupation(Occupation other) {
        this.id = other.getId();
        this.type = other.getType();
    }

    public Occupation() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.type);
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
        final Occupation other = (Occupation) obj;
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
        Occupation other = new Occupation();
        other.setId(id);
        other.setType(type);
        return other;
    }
}
