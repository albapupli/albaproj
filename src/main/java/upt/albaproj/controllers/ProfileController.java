package upt.albaproj.controllers;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import upt.albaproj.dtos.UserProfileDto;
import upt.albaproj.dtos.UserRegistrationDto;
import upt.albaproj.services.UserService;

import java.util.Objects;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal(errorOnInvalidType = true) User loggedInUser, Model model) {
        UserProfileDto userProfile = userService.getUserProfile(loggedInUser.getUsername());
        model.addAttribute("user", userProfile);
        model.addAttribute("isAdmin", loggedInUser.getAuthorities().stream().anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN")));
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") UserProfileDto userProfileDto,
                                @RequestParam("profilePhoto") MultipartFile profilePhoto) {
        userService.updateUserProfile(userProfileDto, profilePhoto);
        return "redirect:/profile?success";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user_list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/user/edit")
    public String editUser(@RequestParam("id") Long userId, Model model) {
        UserProfileDto userProfile = userService.getUserProfileById(userId);
        model.addAttribute("user", userProfile);
        return "edit_user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/user/edit")
    public String updateUser(@ModelAttribute("user") UserProfileDto userProfileDto,
                             @RequestParam("profilePhoto") MultipartFile profilePhoto) {
        userService.updateUserProfile(userProfileDto, profilePhoto);
        return "redirect:/admin/users?success";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("id") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/users?deleted";
    }

    @PostMapping("/profile/remove-photo")
    public String removeProfilePhoto(@ModelAttribute("user") @Valid UserProfileDto userProfileDto) {
        userService.removeProfilePhoto(userProfileDto.getId());
        return "redirect:/profile";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/user/add")
    public String addUser(@ModelAttribute("user") UserProfileDto userProfileDto,
                             @RequestParam("profilePhoto") MultipartFile profilePhoto) {
        userService.updateUserProfile(userProfileDto, profilePhoto);
        return "redirect:/admin/users?success";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/findDevsBySkill")
    public String listDevsBySkill(Model model , String skill) {
        model.addAttribute("users", userService.getDevsBySkill(skill));
        return "devs_list_by_skills";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/findDevsByExeperience")
    public String listDevsByExperience(Model model , String experience) {
        model.addAttribute("users", userService.getDevsByExperience(experience));
        return "devs_list_by_experience";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/findDevsByName")
    public String listDevsByName(Model model , String name) {
        model.addAttribute("users", userService.getDevsByName(name));
        return "devs_list_by_name";
    }
}
