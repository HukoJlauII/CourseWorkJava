package main.Controllers;

import main.Entities.User;
import main.Services.EmailService;
import main.Services.MaterialService;
import main.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/cart")
    public String showCart(Model model) {
        try {
            User userAuth = userService.getUserAuth();
            model.addAttribute("materials", userAuth.getList());
        } catch (Exception e) {

        }
        return "cart";
    }

    @GetMapping("clear")
    public String clearCart() {
        User userAuth = userService.getUserAuth();
        materialService.deleteMaterials(userAuth.getList());
        userAuth.getList().clear();
        return "cart";
    }

    @GetMapping("/order")
    public String GetOrder(Model model) {
        model.addAttribute("infoSetting", true);
        model.addAttribute("message", "Order created successfully");
        User userAuth = userService.getUserAuth();
        String price = userAuth.getOrderPrice();
        emailService.sendOrderMessage(userAuth, userAuth.getList(), price);
        materialService.deleteMaterials(userAuth.getList());
        userAuth.getList().clear();
        return "cart";
    }


}
