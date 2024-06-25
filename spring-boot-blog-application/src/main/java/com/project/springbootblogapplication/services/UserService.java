package com.project.springbootblogapplication.services;

import com.project.springbootblogapplication.DTOs.ResponseStatus;
import com.project.springbootblogapplication.models.Authority;
import com.project.springbootblogapplication.models.AuthorityType;
import com.project.springbootblogapplication.models.PasswordResetToken;
import com.project.springbootblogapplication.models.User;
import com.project.springbootblogapplication.repositories.AuthorityRepository;
import com.project.springbootblogapplication.repositories.PasswordResetTokenRepository;
import com.project.springbootblogapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;


    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Value("${EMAIL_ID}")
    private String fromEmail;

    // save new account
    public User save(User user){

        if(user.getId()==null){
            // encode the password
            user.setPassword(encodedPassword(user.getPassword()));

            if(user.getAuthorities().isEmpty()) {
                // Ensure the user gets the ROLE_USER authority
                Authority authority = authorityRepository.findByAuthorityName(AuthorityType.ROLE_USER)
                        .orElseGet(() -> {
                            Authority newAuthority = new Authority();
                            newAuthority.setAuthorityName(AuthorityType.ROLE_USER);
                            return authorityRepository.save(newAuthority);
                        });
                user.getAuthorities().add(authority);
            }
        }
        else {
            User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
            // check if the password has changed
            if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                user.setPassword(encodedPassword(user.getPassword()));
            }
            else{
                user.setPassword(existingUser.getPassword());
            }
        }

        return userRepository.saveAndFlush(user);
    }

    private String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // get user by id
    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }

    // get user by name
    public Optional<User> findByFullName(String username){
        return userRepository.findOneByFullName(username);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findOneByEmail(email);
    }

    public ResponseStatus sendPasswordResetEmail(User user) {
//        try{
            String resetLink = generateResetTokenForUser(user);
            String emailBody = "Hello \n" + user.getFullName() + "\n\n" + "Please click on this link to Reset your Password :" + resetLink + ". \n\n"
                    + "Regards \n" + "AlgoBlog Team";
            return emailService.sendEmail(fromEmail,user.getEmail(),"Reset AlgoBlog Password",emailBody);

    }

    private String generateResetTokenForUser(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(30);

        // check if token already exists for the user
        Optional<PasswordResetToken> optionalPasswordResetToken = passwordResetTokenRepository.findByUser(user);
        PasswordResetToken passwordResetToken = null;
        if(optionalPasswordResetToken.isPresent()){
            passwordResetToken = optionalPasswordResetToken.get();
        }
        else{
            passwordResetToken = new PasswordResetToken();
            passwordResetToken.setUser(user);
        }

        passwordResetToken.setToken(uuid.toString());
        passwordResetToken.setExpiryDateTime(expiryTime);
        PasswordResetToken token = passwordResetTokenRepository.save(passwordResetToken);

        if (token != null) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            String endpointUrl = baseUrl + "/reset-password";
            return endpointUrl + "/" + token.getToken();
        }

        return "";
    }

}
