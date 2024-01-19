
package com.manning.ssia14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@RestController
public class HomeController {

    @GetMapping("/")
    public String hello(){
        return "hello";
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

            return String.format("Verifier: %s\nChallenge: %s\n", codeVerifier, codeChallenge);
        }
        catch(Exception e) {
            return "error";
        }

    }
}
