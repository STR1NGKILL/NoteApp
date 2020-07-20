package org.reallume.controller.admin;

import org.reallume.domain.Category;
import org.reallume.domain.Label;
import org.reallume.domain.Note;
import org.reallume.domain.User;
import org.reallume.repos.CategoryRepo;
import org.reallume.repos.LabelRepo;
import org.reallume.repos.NoteRepo;
import org.reallume.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminUserListController {

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private UserRepo userRepo;


    @GetMapping(value = "/admin/user-list")
    public String notesPage(Authentication authentication, Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        Integer currentPage = 1;
        model.addAttribute("currentPage", currentPage);

        List<Category> categories = categoryRepo.findAll();
        List<Note> notes = noteRepo.findAll();
        List<Label> labels = labelRepo.findAll();
        List<User> users = userRepo.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("notes", notes);
        model.addAttribute("labels", labels);
        model.addAttribute("users", users);

        return "admin/user-list-page";
    }


}
