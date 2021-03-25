package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    CredentialService credentialService;
    UserService userService;
    EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/add")
    public String postCredential(Authentication authentication, RedirectAttributes redirectAttributes, Credential credential, Model model) {
        User user = userService.getUser(authentication.getName());
        credential.setUserId(user.getUserId());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getDecryptedPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);

        credentialService.addCredential(credential);
        model.addAttribute("credentials", this.credentialService.getCredentials(authentication.getName()));
        model.addAttribute("resultSuccess", true);
        return "result";
    }

    @GetMapping("/delete/{id}")
    public String deleteCredential(@PathVariable("id") Integer id, Model model, Authentication authentication){

        credentialService.deleteCredential(id);
        model.addAttribute("credentials", this.credentialService.getCredentials(authentication.getName()));
        model.addAttribute("resultSuccess", true);
        return "result";
    }
}
