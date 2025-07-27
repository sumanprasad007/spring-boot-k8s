package com.example.service;

import com.example.model.ContactForm;
import com.example.repository.ContactFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {
    
    private final ContactFormRepository contactFormRepository;
    
    public ContactForm saveContactSubmission(ContactForm contactForm) {
        return contactFormRepository.save(contactForm);
    }
    
    public List<ContactForm> getAllContactSubmissions() {
        return contactFormRepository.findAll();
    }
    
    public Optional<ContactForm> getContactSubmissionById(Long id) {
        return contactFormRepository.findById(id);
    }
    
    public void markAsReplied(Long id) {
        contactFormRepository.findById(id).ifPresent(contact -> {
            contact.setRepliedTo(true);
            contactFormRepository.save(contact);
        });
    }
}