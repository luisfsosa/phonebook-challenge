/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook.repository;

import com.livevox.phonebook.domain.Contact;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Contact entity.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
