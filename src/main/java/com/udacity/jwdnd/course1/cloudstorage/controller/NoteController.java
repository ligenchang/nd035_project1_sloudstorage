package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    NoteService noteService;
    UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }
//
//    @GetMapping
//    public String getNotes(Authentication authentication, NoteForm noteForm, Model model) {
//        model.addAttribute("notes", this.noteService.getNotes(authentication.getName()));
//        model.addAttribute("notes", this.noteService.getNotes(authentication.getName());
//        return "home";
//    }

    @PostMapping("/add")
    public String postNote(Authentication authentication, RedirectAttributes redirectAttributes, Note note, Model model) {
        note.setUserId(userService.getUser(authentication.getName()).getUserId());
        this.noteService.addNote(note);
        model.addAttribute("notes", this.noteService.getNotes(authentication.getName()));
        model.addAttribute("resultSuccess", true);
        return "result";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable("id") Integer id, Model model, Authentication authentication){
        noteService.deleteNote(id);
        model.addAttribute("files", this.noteService.getNotes(authentication.getName()));
        model.addAttribute("resultSuccess", true);
        return "result";
    }
}
