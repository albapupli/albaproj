package upt.albaproj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import upt.albaproj.dtos.UserRegistrationDto;
import upt.albaproj.entities.User;
import upt.albaproj.services.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        userService.save(registrationDto);
        return "redirect:/register?success";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute("user") UserRegistrationDto userDto) {
        userService.save(userDto);
        return "redirect:/register?success";
    }
}