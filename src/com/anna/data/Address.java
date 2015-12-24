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
@Table(name="address")
public class Address implements MyCloneable
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="address_id")
	private Long id;
	
	@Column(name="country")
	private String country;
	
	@Column(name="state")
	private String state;
	
	@Column(name="city")
	private String city;
	
	@Column(name="street")
	private String street;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Address() {
		super();
	}

	public Address(String country, String state, String city, String street) {
		super();
		this.country = country;
		this.state = state;
		this.city = city;
		this.street = street;
	}
        
        @Transient
        public boolean isEmpty()
        {
            if(country != null && !country.trim().isEmpty())
                return false;
            else if(state != null && !state.trim().isEmpty())
                return false;
            else if(city != null && !city.trim().isEmpty())
                return false;
            else if(street != null && !street.trim().isEmpty())
                return false;
            else 
                return true;                  
        }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public Address(Address other)
    {
        this.id = other.getId();
        this.country = other.getCountry();
        this.state = other.getState();
        this.city = other.getCity();
        this.street = other.getStreet();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        
        if( other.getCountry()== null &&
            other.getState()== null && 
            other.getCity()== null && 
            other.getStreet()== null)
            return false;
        
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        return true;
    }
    
    @Transient
    @Override
    public Object clone()
    {
        Address dst = new Address();
        dst.setCity(city);
        dst.setCountry(country);
        dst.setId(id);
        dst.setState(state);
        dst.setStreet(street);
        return dst;
    }
	
}
