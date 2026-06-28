package com.springarena.service;

import com.springarena.model.Contact;
import com.springarena.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        // TODO: Get all contacts and return them
        List<Contact> contacts = contactRepository.findAll();
        return contacts;
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        // TODO: Get contact by id, return Optional of contact
        Optional<Contact> contact = contactRepository.findById(id);
        return contact;
    }

    @Override
    public Contact createContact(Contact contact) {
        // TODO: Create a contact and return it
        Contact newContact = contactRepository.save(contact);
        return newContact;
    }

    @Override
    public Optional<Contact> updateContact(Long id, Contact contact) {
        // TODO: Update a contact and return Optional of updated contact
        return contactRepository.findById(id)
                .map(existingContact -> {
                    existingContact.setFirstName(contact.getFirstName());
                    existingContact.setLastName(contact.getLastName());
                    existingContact.setEmail(contact.getEmail());
                    existingContact.setPhone(contact.getPhone());
                    return contactRepository.save(existingContact);
                });
    }

    @Override
    public boolean deleteContact(Long id) {
        // TODO: Delete a contact and return true if existed, false otherwise
        Optional<Contact> foundContact = contactRepository.findById(id);
        if (foundContact.isPresent()) {
            contactRepository.delete(foundContact.get());
            return true;
        } else {
            return false;
        }
    }
}
