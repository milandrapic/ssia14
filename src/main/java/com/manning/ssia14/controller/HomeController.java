
package com.manning.ssia14.controller;

import com.manning.ssia14.model.AppUser;
import com.manning.ssia14.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@RestController
public class HomeController {

    @Autowired
    private AppUserDetailsService appUserDetailsService;


    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    @GetMapping("/getUser")
    public String getUser(){
        return appUserDetailsService.loadAppUser("test").getRole().name();
    }

    @PostMapping("/registerUser")
    public String registerUser(
            @RequestBody AppUser user
            ){
        if(user == null) return "Null User";
//        System.out.println(String.format("Registering User: %s", user.getUsername()));
        appUserDetailsService.register(user);
        return user.getUsername();
    }

    @GetMapping("/registerUser")
    public String registerUserGet(){
        System.out.println("hello from register_user");
        return "test_user";
    }

    @GetMapping("/pkce")
    public String getChallengeAndVerifier() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] code = new byte[32];

        secureRandom.nextBytes(code);

        String codeVerifier = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(code);
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] digested = messageDigest.digest(codeVerifier.getBytes());
            String codeChallenge = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(digested);

            return String.format("Verifier: %s\n        |          Challenge: %s\n", codeVerifier, codeChallenge);
        }
        catch(Exception e) {
            return "error";
        }

    }
}
