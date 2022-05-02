package main.Controllers;

import main.Entities.User;
import main.Services.MaterialService;
import main.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private UserService userService;

    @GetMapping("/add_material_{number}")
    public String addMaterialWithNumber(@PathVariable int number) {
        User userAuth = userService.getUserAuth();
        materialService.plusCountMaterials(userAuth, number);
        return "redirect:/index#{number}";
    }

    @GetMapping("/remove_material_{number}")
    public String removeMaterialWithNumber(@PathVariable int number) {
        User userAuth = userService.getUserAuth();
        materialService.minusCountMaterials(userAuth, number);
        return "redirect:/index#{number}";
    }


}
