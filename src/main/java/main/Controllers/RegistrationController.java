package main.Controllers;

import lombok.RequiredArgsConstructor;
import main.Entities.User;
import main.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute("user") @Valid User user,
                                   Model model) {
        System.out.println(user);
        if (user.getUsername().length() < 5) {
            model.addAttribute("errorLenUsername", true);
            return "registration";
        }
        if (!Objects.equals(user.getPassword(), user.getPasswordConfirm())) {
            model.addAttribute("errorConfPassword", true);
            return "registration";
        }
        if (user.getPassword().length() < 8) {
            model.addAttribute("errorLenPassword", true);
            return "registration";
        }
        if (userService.findUserByUsername(user.getUsername()) != null) {
            model.addAttribute("errorAlreadyExistsUsername", true);
            return "registration";
        }
        try {
            userService.saveUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorAnomaly", true);
            return "registration";
        }
    }
}
