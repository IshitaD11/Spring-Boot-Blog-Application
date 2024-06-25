package com.project.springbootblogapplication.controllers;

import org.springframework.stereotype.Controller;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> optionalUser = userService.findByEmail(currentPrincipalName);
        if(optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            model.addAttribute("showNewPostIcon",true);
            return "profile";
        }

        return "404";

    }

    @PostMapping("/profile")
    public String saveProfileChanges(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/profile";
    }

}
