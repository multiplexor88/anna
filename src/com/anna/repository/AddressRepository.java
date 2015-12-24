package com.anna.repository;

import com.anna.data.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>
{
    Address findByCountryAndStateAndCity(String string, String string2, String string3);
	
}
