/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * Phonebook Test
 */
package com.livevox.phonebook.service;

import com.livevox.phonebook.domain.Contact;
import com.livevox.phonebook.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The Class ContactServiceImpl.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    /** The contact repository. */
    @Autowired
    private ContactRepository contactRepository;

    /**
     * Creates the contact.
     *
     * @param contact the contact
     * @return the contact
     * @throws URISyntaxException the URI syntax exception
     */
    public Contact createContact(Contact contact) throws URISyntaxException {
        log.debug("createContact contact  {}", contact);
        return contactRepository.save(contact);
    }

    /**
     * Update contact.
     *
     * @param contact the contact
     * @return the contact
     * @throws URISyntaxException the URI syntax exception
     */
    public Contact updateContact(Contact contact) throws URISyntaxException {
        log.debug("updateContact contact  {}", contact);
        return contactRepository.save(contact);
    }

    /**
     * Gets the all contacts.
     *
     * @param pageable the pageable
     * @return the all contacts
     */
    public Page<Contact> getAllContacts(Pageable pageable) {
        log.debug("findAll contact  {}", pageable);
        return contactRepository.findAll(pageable);
    }

    /**
     * Find contact by any field.
     *
     * @param anyField the any field
     * @return the sets the
     */
    @Override
    public Set<Contact> findContactByAnyField(String anyField) {

        List<Contact> contactList;
        Optional<Contact> contact;
        Set<Contact> contactAllList = new HashSet<>();

        if (ContactServiceImpl.isLong(anyField)) {
            contact = contactRepository.findById(Long.parseLong(anyField));

            if(contact.isPresent()){
                contactAllList.add(contact.get());
                return contactAllList;
            }
        }

        contactList = contactRepository.findByFirstNameIgnoreCaseContaining(anyField);
        if (contactList != null && contactList.size() > 0) {
            contactAllList.addAll(contactList);
        }

        contactList = contactRepository.findByLastNameIgnoreCaseContaining(anyField);
        if (contactList != null && contactList.size() > 0) {
            contactAllList.addAll(contactList);
        }

        contactList = contactRepository.findByphoneIgnoreCaseContaining(anyField);
        if (contactList != null && contactList.size() > 0) {
            contactAllList.addAll(contactList);
        }
        return contactAllList;
    }

    /**
     * Gets the contact.
     *
     * @param id the id
     * @return the contact
     */
    public Optional<Contact> getContact(Long id) {
        log.debug("getContact contact  {}", id);
        return contactRepository.findById(id);
    }

    /**
     * Delete contact.
     *
     * @param id the id
     */
    public void deleteContact(Long id) {
        log.debug("deleteContact contact  {}", id);
        contactRepository.deleteById(id);
    }

    private static boolean isLong(String strNum) {
        try {
            double d = Long.parseLong(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}
