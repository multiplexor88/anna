package com.anna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anna.data.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

}
