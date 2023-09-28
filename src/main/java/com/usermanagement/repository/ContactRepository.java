package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

}
