package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.DTOs.ResponseStatus;
import com.project.springbootblogapplication.models.PasswordResetToken;
import com.project.springbootblogapplication.services.PasswordResetTokenService;
import com.project.springbootblogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.springbootblogapplication.models.User;

import java.util.Optional;

@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot_password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordProcess(@RequestParam("email") String email, Model model) {
        Optional<User> optionalUser = userService.findByEmail(email);
        ResponseStatus response = null;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            response = userService.sendPasswordResetEmail(user);
        } else
            return "redirect:/forgot-password?error=Email is not registered with us!";

        if (response != null && response.equals(ResponseStatus.SUCCESS))
            return "redirect:/forgot-password?success";

        return "redirect:/forgot-password?error=Unknown error, please try again.";
    }


    @GetMapping("/reset-password/{token}")
    public String showResetPasswordPage(@PathVariable String token, Model model) {
        Optional<PasswordResetToken> optionalPasswordResetToken = passwordResetTokenService.findByToken(token);
        if (optionalPasswordResetToken.isPresent()) {
            PasswordResetToken passwordResetToken = optionalPasswordResetToken.get();
            if (!passwordResetToken.isExpired()) {
                model.addAttribute("email", passwordResetToken.getUser().getEmail());
                return "reset_password";
            }
            else
                return "redirect:/forgot-password?error=Token Expired. Please click on Reset Password again.";
        }

        return "redirect:/forgot-password?error=Unknown error, please try again.";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                Model model){
        Optional<User> optionalUser = userService.findByEmail(email);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setPassword(password);
            userService.save(user);
            return "redirect:/login";
        }
        return "redirect:/forgot-password?error=Unknown error, please try again.";
    }
}
