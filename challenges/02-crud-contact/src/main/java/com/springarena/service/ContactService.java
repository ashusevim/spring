package com.springarena.service;

import com.springarena.model.Contact;
import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> getAllContacts();
    Optional<Contact> getContactById(Long id);
    Contact createContact(Contact contact);
    Optional<Contact> updateContact(Long id, Contact contact);
    boolean deleteContact(Long id);
}
