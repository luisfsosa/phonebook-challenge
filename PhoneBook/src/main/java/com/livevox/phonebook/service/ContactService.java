/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook.service;

import com.livevox.phonebook.domain.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URISyntaxException;
import java.util.Optional;

/**
 * The Interface ContactService.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public interface ContactService {

    /**
     * Creates the contact.
     *
     * @param contact the contact
     * @return the contact
     * @throws URISyntaxException the URI syntax exception
     */
    Contact createContact(Contact contact) throws URISyntaxException;

    /**
     * Update contact.
     *
     * @param contact the contact
     * @return the contact
     * @throws URISyntaxException the URI syntax exception
     */
    Contact updateContact(Contact contact) throws URISyntaxException;

    /**
     * Gets the all contacts.
     *
     * @param pageable the pageable
     * @return the all contacts
     */
    Page<Contact> getAllContacts(Pageable pageable);

    /**
     * Gets the contact.
     *
     * @param id the id
     * @return the contact
     */
    Optional<Contact> getContact(Long id);

    /**
     * Delete contact.
     *
     * @param id the id
     */
    void deleteContact(Long id);
}
