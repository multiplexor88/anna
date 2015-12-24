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
@Table(name="contact")
public class Contact implements MyCloneable
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="contact_id")
	private Long id;
	
	@Column(name="cell_number")
	private String cellNumber;

	@Column(name="work_number")
	private String workNumber;

	@Column(name="home_number")
	private String homeNumber;

	@Column(name="email")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Contact(Long id, String cellNumber, String workNumber, String homeNumber, String email) {
		super();
                this.id = id;
		this.cellNumber = cellNumber;
		this.workNumber = workNumber;
		this.homeNumber = homeNumber;
		this.email = email;
	}
        
        public Contact(Contact other)
        {
            this.id = other.getId();
            this.cellNumber = other.getCellNumber();
            this.workNumber = other.getWorkNumber();
            this.homeNumber = other.getHomeNumber();
            this.email = other.getEmail();
        }

	public Contact() {
	}

    @Override
    public Object clone() 
    {
        return new Contact(id, cellNumber, workNumber, homeNumber, email);
    }
	
    @Transient
    public void setData(Contact other)
    {
        id = other.getId();
        cellNumber = other.getCellNumber();
        workNumber = other.getWorkNumber();
        homeNumber = other.getHomeNumber();
        email = other.getEmail();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contact other = (Contact) obj;
        if( other.getCellNumber() == null &&
            other.getEmail() == null && 
            other.getHomeNumber() == null && 
            other.getWorkNumber() == null)
            return false;
            
        if (!Objects.equals(this.cellNumber, other.cellNumber)) {
            return false;
        }
        if (!Objects.equals(this.workNumber, other.workNumber)) {
            return false;
        }
        if (!Objects.equals(this.homeNumber, other.homeNumber)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
}
