package at.ahujaprinc.gk961.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail() {
        // Test data
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        // Call the method
        emailService.sendEmail(to, subject, body);

        // Verify that the send method was called with a SimpleMailMessage
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendEmailContent() {
        // Test data
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        // Call the method
        emailService.sendEmail(to, subject, body);

        // Verify that the send method was called with the correct parameters
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        verify(mailSender).send(message);
    }

    @Test
    void testSendEmailToNull() {
        // Test null email address
        String to = null;
        String subject = "Test Subject";
        String body = "Test Body";

        // Call the method (this should not throw an exception in this case, but you may handle it)
        emailService.sendEmail(to, subject, body);

        // Verify that the send method was not called when email is null
        verify(mailSender, Mockito.never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendEmailEmptySubject() {
        // Test empty subject
        String to = "test@example.com";
        String subject = "";
        String body = "Test Body";

        // Call the method
        emailService.sendEmail(to, subject, body);

        // Verify that the send method was called
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        verify(mailSender).send(message);
    }

    @Test
    void testSendEmailEmptyBody() {
        // Test empty body
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "";

        // Call the method
        emailService.sendEmail(to, subject, body);

        // Verify that the send method was called
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        verify(mailSender).send(message);
    }
}
