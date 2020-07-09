package org.reallume.controller.user;

import org.reallume.domain.*;
import org.reallume.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserPageController {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value="/user")
    public String userMain(Model model, Authentication authentication) {

        List<Category> categories = categoryRepo.findAll();
        List<Note> notes = noteRepo.findAll();
        List<Label> labels = labelRepo.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("notes", notes);
        model.addAttribute("labels", labels);

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        return "user/main-page";
    }

}
