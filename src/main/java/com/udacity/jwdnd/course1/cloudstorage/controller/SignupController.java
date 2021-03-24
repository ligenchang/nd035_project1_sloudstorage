package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;
import java.util.Random;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;
    private final HashService hashService;


    public SignupController(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }



    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;
//        user.setSalt("123456");

//        String hashedPwd = hashService.getHashedValue(user.getPassword(), user.getSalt());
//        user.setPassword(hashedPwd);

        if (!userService.isUsernameAvailable(user.getUserName())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
