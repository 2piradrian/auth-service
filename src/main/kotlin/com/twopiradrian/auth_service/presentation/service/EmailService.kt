package com.twopiradrian.auth_service.presentation.service

import org.slf4j.LoggerFactory
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender
) {

    private val logger = LoggerFactory.getLogger(EmailService::class.java)

    fun sendEmail(
        to: String,
        subject: String,
        text: String
    ) {
        try {
            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)

            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(text, true)

            mailSender.send(message)
        }
        catch (e: MailException) {
            logger.warn("SMTP server not available. Email to '$to' with subject '$subject' was logged instead: \n$text")
        }
    }

}
