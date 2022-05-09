package main.Controllers;

import main.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FeedbackController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/index")
    public String sendFeedback(@RequestParam(name = "name") String name, @RequestParam(name = "email") String email, @RequestParam(name = "messageText") String messageText, Model model) {
        emailService.sendFeedbackMessage(email, messageText, name);
        model.addAttribute("infoSetting", true);
        model.addAttribute("message", "Your feedback sent successfully");
        return "redirect:/index#contact";
    }
}
