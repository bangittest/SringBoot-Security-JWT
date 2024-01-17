package com.ra.controller;

import com.ra.model.User;
import com.ra.repository.UserRepository;
import com.ra.service.email.EmailService;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
       try {
           User user = userService.findByEmail(email);
           if (user == null) {
               return ResponseEntity.badRequest().body("not found");
           }
           String resetToken = userService.generateResetToken(user);
           scheduleResetTask(resetToken);
           emailService.sendEmailPass(user, resetToken);
           return new ResponseEntity<>("Password reset email sent successfully", HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>("vui long nhap chinh xac",HttpStatus.BAD_REQUEST);
       }
    }

    private void scheduleResetTask(String resetToken) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            User user = userService.findByToken(resetToken);
            if (user != null) {
                user.setToken(null);
                userRepository.save(user);
            }
        }, 3, TimeUnit.MINUTES );//3p
//        ,30, TimeUnit.SECONDS 30s
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "token") String token, @RequestParam(name = "password") String newPassword) {
       try {
           User user = userService.findByToken(token);
           if (user == null) {
               return ResponseEntity.badRequest().body("Invalid or expired token");
           }
           user.setPassword(passwordEncoder.encode(newPassword));
           user.setToken(null);
           userRepository.save(user);

           return ResponseEntity.ok("Password reset successful");
       }catch (Exception e){
           return new ResponseEntity<>("vui long nhap chinh xac",HttpStatus.BAD_REQUEST);
       }
    }
}
