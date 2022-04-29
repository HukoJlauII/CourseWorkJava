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
    public void sendSimpleMessage(String To,String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(To);
        message.setText(text);
        message.setSubject("Приветственное письмо");
        mailSender.send(message);
    }
    @Async
    public void sendEditMessage(String To,String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(To);
        message.setText(text);
        message.setSubject("Уведомление об изменении информации об Аккаунте");
        mailSender.send(message);
    }
    @Async
    public void sendOrderMessage(User user, Set<Material> materials, String price) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        StringBuilder messageText= new StringBuilder("Thank you for the order!\n");

        for(Material material: materials){
            messageText.append(material.getItemName()).append(" | ")
                    .append(material.getMaterialCount())
                    .append("x | ")
                    .append(material.getPriceForManyMaterials());
        }
        messageText.append("\nTotal price: ").append(price);
        message.setText(messageText.toString());
        message.setSubject("Уведомление о создании заказа");
        mailSender.send(message);
    }
}
