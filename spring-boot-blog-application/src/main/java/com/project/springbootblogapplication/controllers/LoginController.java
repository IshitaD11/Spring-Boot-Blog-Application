package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public String getLoginPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String errorMessage = (String) session.getAttribute("error_message");
            model.addAttribute("error", errorMessage);

            if (errorMessage != null && errorMessage.contains("Your Email is not registered")) {
                model.addAttribute("userNotFound", true);
            }

            session.removeAttribute("error_message");
        }
        return "login";
    }

}
