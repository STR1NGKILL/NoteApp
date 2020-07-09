package org.reallume.controller.user;

import org.reallume.domain.*;
import org.reallume.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
//доделать!!!
@Controller
public class ProfileController {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private UserRepo userRepo;

    //Страница профиля
    @GetMapping(value="/user/categories-and-labels")
    public String categoriesAndLabelsPage(Authentication authentication, Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        loadCategoriesToUser(user);
        loadLabelsToUser(user);

        List<Category> categories = user.getCategories();
        List<Label> labels = user.getLabels();

        model.addAttribute("categories", categories);
        model.addAttribute("labels", labels);

        return "user/categories-and-labels-page";
    }


    void loadCategoriesToUser(User user){

        List<Category> categories = categoryRepo.findByAuthor(user);

        user.setCategories(categories);
    }

    void loadLabelsToUser(User user) {

        List<Label> labels = labelRepo.findByAuthor(user);

        user.setLabels(labels);
    }

}
