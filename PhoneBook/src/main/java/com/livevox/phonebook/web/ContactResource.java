/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * Phonebook Test
 */
package com.livevox.phonebook.web;

import com.livevox.phonebook.domain.Contact;
import com.livevox.phonebook.service.ContactService;
import com.livevox.phonebook.web.errors.BadRequestAlertException;

import com.livevox.phonebook.web.util.HeaderUtil;
import com.livevox.phonebook.web.util.PaginationUtil;
import com.livevox.phonebook.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.livevox.phonebook.domain.Contact}.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@RestController
@RequestMapping("/api")
public class ContactResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(ContactResource.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "contact";

    /** The application name. */
    @Value("${spring.application.name}")
    private String applicationName;

    /** The contact service. */
    private final ContactService contactService;

    /**
     * Instantiates a new contact resource.
     *
     * @param contactService the contact service
     */
    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * {@code POST  /contacts} : Create a new contact.
     *
     * @param contact the contact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contact, or with status {@code 400 (Bad Request)} if the contact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contacts")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) throws URISyntaxException {
        log.debug("REST request to save Contact : {}", contact);
        if (contact.getId() != null) {
            throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contact result = contactService.createContact(contact);
        return ResponseEntity.created(new URI("/api/contacts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /contacts} : Updates an existing contact.
     *
     * @param contact the contact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contact,
     * or with status {@code 400 (Bad Request)} if the contact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contacts")
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) throws URISyntaxException {
        log.debug("REST request to update Contact : {}", contact);
        if (contact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contact result = contactService.updateContact(contact);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contact.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /contacts} : get all the contacts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contacts in body.
     */
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts(Pageable pageable) {
        log.debug("REST request to get a page of Contacts");
        Page<Contact> page = contactService.getAllContacts(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contacts/:id} : get the "id" contact.
     *
     * @param id the id of the contact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable Long id) {
        log.debug("REST request to get Contact : {}", id);
        Optional<Contact> contact = contactService.getContact(id);
        return ResponseUtil.wrapOrNotFound(contact);
    }

    /**
     * {@code DELETE  /contacts/:id} : delete the "id" contact.
     *
     * @param id the id of the contact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        log.debug("REST request to delete Contact : {}", id);
        contactService.deleteContact(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    /**
     * Find contact by any.
     *
     * @param anyField the any field
     * @return the response entity
     */
    @GetMapping("/contacts/findByAny")
    public ResponseEntity<Set<Contact>> findContactByAny(@RequestParam(value = "anyField") String anyField){
        log.debug("REST request to get a page of Contacts");
        HttpHeaders headers = new HttpHeaders();
        Set<Contact> contactSet = contactService.findContactByAnyField(anyField);
        return ResponseEntity.ok().headers(headers).body(contactSet);
    }

}
