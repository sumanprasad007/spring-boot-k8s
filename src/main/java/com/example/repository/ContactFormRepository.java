package com.example.repository;

import com.example.model.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
    // Custom queries can be added here if needed
}