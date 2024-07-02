package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.DTOs.ResponseStatus;
import com.project.springbootblogapplication.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactUsController {

    @Autowired
    private EmailService emailService;

    @Value("${EMAIL_ID}")
    private String toEmail;

    @GetMapping("/contact-us")
    public String showContactUsPage(Model model, Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            String userEmail = authentication.getName();
            model.addAttribute("userEmail", userEmail);
        }
        return "contact_us";
    }

    @PostMapping("contact-us")
    public String sendContactUsEmail(@RequestParam("subject") String subject,
                                     @RequestParam("message") String message,
                                     @RequestParam("userEmail") String userEmail,
                                     Model model){

        String msgBody = "Form: " + userEmail + "\n\n" + message;
        ResponseStatus response = emailService.sendEmail(toEmail,toEmail,subject,msgBody);

        if (response != null && response.equals(ResponseStatus.SUCCESS))
            model.addAttribute("successMessage", "Your message has been sent successfully!");
        else
            model.addAttribute("errorMessage", "An error occurred while sending your message. Please try again.");

        return "contact_us";
    }
}
