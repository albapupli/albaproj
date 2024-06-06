package upt.albaproj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import upt.albaproj.dtos.UserRegistrationDto;

@Controller
public class ProfileController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }
}
