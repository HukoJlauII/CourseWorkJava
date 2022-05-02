package main.Services;

import main.Entities.Material;
import main.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Set;

@EnableAsync
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendSimpleMessage(String To, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(To);
        message.setText(text);
        message.setSubject("Welcome message!");
        mailSender.send(message);
    }

    @Async
    public void sendEditMessage(String To, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(To);
        message.setText(text);
        message.setSubject("Notification about your account info");
        mailSender.send(message);
    }

    @Async
    public void sendOrderMessage(User user, Set<Material> materials, String price) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        StringBuilder messageText = new StringBuilder("Thank you for the order!\n");

        for (Material material : materials) {
            messageText.append(material.getItemName()).append(" | ")
                    .append(material.getMaterialCount())
                    .append("x | ")
                    .append(material.getPriceForManyMaterials());
        }
        messageText.append("\nTotal price: ").append(price);
        message.setText(messageText.toString());
        message.setSubject("Order creation");
        mailSender.send(message);
    }

    @Async
    public void sendFeedbackMessage(String to, String text, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setText("Hello, " + name + "!\nYou send feedback message to our site:\n\"" + text + "\"\nOur feedback is very important for us!");
        message.setSubject("Feedback message notification");
        mailSender.send(message);
    }
}
