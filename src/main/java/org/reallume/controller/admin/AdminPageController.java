package org.reallume.controller.admin;

import org.reallume.domain.*;
import org.reallume.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminPageController {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value="/admin")
    public String adminMain(Model model, Authentication authentication) {

        List<Category> categories = categoryRepo.findAll();
        List<Note> notes = noteRepo.findAll();
        List<Label> labels = labelRepo.findAll();
        List<User> users = userRepo.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("notes", notes);
        model.addAttribute("labels", labels);
        model.addAttribute("users", users);

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        return "redirect:/admin/statistics";
    }

}
