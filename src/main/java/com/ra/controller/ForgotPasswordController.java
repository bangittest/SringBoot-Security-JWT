package com.ra.controller;

import com.ra.model.User;
import com.ra.repository.UserRepository;
import com.ra.service.email.EmailService;
import com.ra.service.user.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class ForgotPasswordController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgot(@RequestParam(name = "email", defaultValue = "", required = false) String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("not found");
        }
        String resetToken = userService.generateResetToken(user);
        emailService.sendEmailPass(user, resetToken);
        return new ResponseEntity<>("Password reset email sent successfully", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "token") String token, @RequestParam(name = "password") String newPassword) {
        User user = userService.findByToken(token);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successful");
    }
}
