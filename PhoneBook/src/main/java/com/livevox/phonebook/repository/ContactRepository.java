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

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Contact entity.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Contact> findById(Long id);

    /**
     * Find by first name ignore case containing.
     *
     * @param firstName the first name
     * @return the list
     */
    List<Contact> findByFirstNameIgnoreCaseContaining(String firstName);

    /**
     * Find by last name ignore case containing.
     *
     * @param lastName the last name
     * @return the list
     */
    List<Contact> findByLastNameIgnoreCaseContaining(String lastName);

    /**
     * Find byphone ignore case containing.
     *
     * @param phone the phone
     * @return the list
     */
    List<Contact> findByphoneIgnoreCaseContaining(String phone);
}
