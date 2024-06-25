package com.project.springbootblogapplication.config;

import com.project.springbootblogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String email = request.getParameter("email");
        boolean userExists = userService.findByEmail(email).isPresent();

        String errorMessage = "Incorrect password";

        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "Your account is disabled";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "Your account has expired";
        } else if (!userExists) {
            errorMessage = "Your Email is not registered. ";
        }

        System.out.println("error message inside CustomAuthenticationFailureHandler " + errorMessage);
        request.getSession().setAttribute("error_message", errorMessage);
        getRedirectStrategy().sendRedirect(request, response, "/login?error");
    }
}
