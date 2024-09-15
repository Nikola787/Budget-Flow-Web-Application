package com.nikola787.budget_flow_tracker.service;


import com.nikola787.budget_flow_tracker.model.authentication.EmailTemplateName;
import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException;



}
