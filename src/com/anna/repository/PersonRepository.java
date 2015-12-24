package com.anna.repository;

import com.anna.data.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> 
{
//	@Query("select s.firstName from Person s where s.lastName='Kalinichenko'")
//	public String findPersonByLastName1();
	
	public Person findPersonById(Long id);
	
	public Person findPersonByFirstNameAndLastName(String fisrtName, String lastName);
        
        public List<Person> findPersonByFirstName(String fisrtName);
        
        public List<Person> findPersonByLastName(String fisrtName);
        
        public List<Person> findPersonByLastNameLike(String fisrtName);
}
