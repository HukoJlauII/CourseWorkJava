package main.Controllers;

import main.Entities.User;
import main.Services.EmailService;
import main.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/UserProfile")
    public String showUser() {
        return "UserProfile";
    }

    @GetMapping("/UserProfile_username")
    public String changeUsername(Model model) {
        model.addAttribute("username", userService.getUserAuth().getUsername());
        model.addAttribute("changeUsername", true);
        return "UserProfile";
    }

    @PostMapping("/UserProfile_username")
    public String changeUsername(@ModelAttribute("username") String username, Model model) {
        if (username.length() < 5) {
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Имя пользователя должно содержать не менее 5 символов");
            return "UserProfile";
        } else if (userService.findUserByUsername(username) != null) {
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пользователь с таким ником уже существует");
            return "UserProfile";
        }
        User changed_user = userService.findUserByUsername(userService.getUserAuth().getUsername());
        changed_user.setUsername(username);
        userService.resaveUser(changed_user);
        model.addAttribute("infoSetting", true);
        model.addAttribute("message", "Пароль успешно изменён");
        String message = "Здравствуйте, ваш никнейм был изменён с " + userService.getUserAuth().getUsername() + " на " + changed_user.getUsername() + "!";
        emailService.sendEditMessage(changed_user.getEmail(), message);
        return "UserProfile";
    }

    @GetMapping("/UserProfile_password")
    public String changePassword(Model model) {
        //Визуально отображаем поле для ввода
        model.addAttribute("changePassword", true);
        return "UserProfile";
    }

    @PostMapping("/UserProfile_password")
    public String changePassword(@ModelAttribute("newPassword") String newPassword,
                                 @ModelAttribute("newPasswordConfirm") String newPasswordConfirm,
                                 @ModelAttribute("oldPassword") String oldPassword,
                                 Model model) {
        User current_user = userService.getUserAuth();


        if (!bCryptPasswordEncoder.matches(oldPassword, current_user.getPassword())) {
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Неправильный старый пароль");
            return "userProfile";
        }

        if (newPassword.length() < 8) {
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пароль должен содержать не менее 8-ми символов");
            return "UserProfile";
        }
        if (!newPassword.equals(newPasswordConfirm)) {
            model.addAttribute("errorSetting", true);
            model.addAttribute("message", "Пароли не совпадают");
            return "UserProfile";
        }
        current_user.setPassword(newPassword);
        current_user.setPasswordConfirm(newPasswordConfirm);
        String message = "Здравствуйте, " + current_user.getUsername() + ". Ваш пароль был изменён. Убедитесь, что это сделали вы!";
        emailService.sendEditMessage(current_user.getEmail(), message);

        userService.resaveUserPassword(current_user);
        model.addAttribute("infoSetting", true);
        model.addAttribute("message", "Пароль успешно изменён");
        return "UserProfile";
    }


    //Смена почты
    @GetMapping("/UserProfile_email")
    public String changeEmail(Model model) {
        model.addAttribute("changeEmail", true);
        return "UserProfile";
    }

    @PostMapping("/UserProfile_email")
    public String changeEmail(@ModelAttribute("email") String email, Model model) {
        User current_user = userService.getUserAuth();
        String message = "Здравствуйте, ваша почта была изменена на " + email;
        emailService.sendEditMessage(current_user.getEmail(), message);
        current_user.setEmail(email);
        userService.resaveUser(current_user);
        model.addAttribute("infoSetting", true);
        model.addAttribute("message", "Почта успешно изменена");
        return "UserProfile";
    }
}
