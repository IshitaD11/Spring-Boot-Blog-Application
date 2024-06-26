package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        // passing user object to html in th:object="${user}"
        User user = new User();
        model.addAttribute("user",user);
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes){
        if(userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error","This email is already registered! Please use a different email id or ");
            return "register";
        }

        userService.save(user);
        // after post is done, redirect to login page
        redirectAttributes.addFlashAttribute("message", "User registered successfully! Please login.");
        return "redirect:/login";
    }
}
