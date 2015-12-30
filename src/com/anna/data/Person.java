package com.anna.data;

import com.anna.gui.interfaces.MyCloneable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.context.annotation.Lazy;

@Entity
@Table(name="Person")
public class Person implements MyCloneable, Serializable
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="person_id")
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="patronymic")
	private String patronymic;
	
	@Column(name="gender")
	private String gender;
	
        @OneToOne(cascade=CascadeType.ALL)
        @JoinColumn(name = "contact_id")
	private Contact contact;

	@Lazy
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="person_address", joinColumns=@JoinColumn(name="person_id"), inverseJoinColumns=@JoinColumn(name="address_id"))
	private List<Address> addressList = new ArrayList<>();
	
        @Lazy
	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinTable(name="person_occupation", joinColumns=@JoinColumn(name="person_id"), inverseJoinColumns=@JoinColumn(name="occupation_id"))
	private List<Occupation> occupationList = new ArrayList<>();
        
        @Lazy
        @ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinTable(name="person_hobby", joinColumns=@JoinColumn(name="person_id"), inverseJoinColumns=@JoinColumn(name="hobby_id"))
	private List<Hobby> hobbyList = new ArrayList<>();

    public List<Hobby> getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(ArrayList<Hobby> hobbyList) {
        this.hobbyList = hobbyList;
    }
	
	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(ArrayList<Address> addressList) {
		this.addressList = addressList;
	}

	public List<Occupation> getOccupationList() {
		return occupationList;
	}

	public void setOccupationList(ArrayList<Occupation> occupationList) {
		this.occupationList = occupationList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

	public Person() 
        {
            contact = new Contact();
            addressList = new ArrayList<>();
            occupationList = new ArrayList<>();
            hobbyList = new ArrayList<>();
            gender = "MALE";
	}

        @Transient
        public boolean isEmpty()
        {
            return firstName == null  && lastName == null;
        }
        
        @Transient
        @Override
        public Object clone()
        {
            Person p = new Person();
            p.setId(id);
            p.setFirstName(firstName);
            p.setLastName(lastName);
            p.setPatronymic(patronymic);
            p.setGender(gender);
            
            if(addressList != null)
            {
                ArrayList a = new ArrayList();
                for(Address adr:addressList)
                    a.add(adr.clone());       
                p.setAddressList(a);
            }
            else
                p.setAddressList(new ArrayList());
            
            if(occupationList != null)
            {
                ArrayList o = new ArrayList();
                for(Occupation occup:occupationList)
                    o.add(occup.clone());
                
                p.setOccupationList(o);
            }
            else
                p.setOccupationList(new ArrayList());

            if(hobbyList != null)
            {
                ArrayList hb = new ArrayList();
                for(Hobby h:hobbyList)
                    hb.add(h.clone());
                
                p.setHobbyList(hb);
            }
            else
                p.setHobbyList(new ArrayList());
            
            if(contact != null)
            {
                Contact c = new Contact();
                c.setId(contact.getId());
                c.setCellNumber(contact.getCellNumber());
                c.setEmail(contact.getEmail());
                c.setHomeNumber(contact.getHomeNumber());
                c.setWorkNumber(contact.getWorkNumber());
                p.setContact(c);
            }
            else
                p.setContact(new Contact());
            
            return p;
        }

    public Person(Person that) 
    {
        id = that.id;
        firstName = that.firstName;
        lastName = that.lastName;
        patronymic = that.patronymic;
        gender = that.gender;
        addressList = that.addressList;
        contact = that.contact;
        hobbyList = that.hobbyList;
        occupationList = that.occupationList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Person other = (Person) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.patronymic, other.patronymic)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        /*if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        if (!Objects.equals(this.addressList, other.addressList)) {
            return false;
        }
        if (!Objects.equals(this.occupationList, other.occupationList)) {
            return false;
        }
        if (!Objects.equals(this.hobbyList, other.hobbyList)) {
            return false;
        }*/
        return true;
    }



        
        
}
