package org.reallume.controller.user;

import org.reallume.domain.Category;
import org.reallume.domain.User;
import org.reallume.repos.CategoryRepo;
import org.reallume.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    //Редактирование категории
    @PostMapping(value = "/user/categories/edit")
    public String userEditCategory(Authentication authentication,
                                  @RequestParam long category_id,
                                  @RequestParam String name,
                                  Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();

        Category category = new Category();
        category.setAuthor(user);
        category.setId(category_id);
        category.setName(name);

        categoryRepo.save(category);
        loadCategoriesToUser(user);

        return "redirect:/user/categories-and-labels";
    }

    //Добавление категории
    @PostMapping(value = "/user/categories/add")
    public String userAddCategory(Authentication authentication,
                                @RequestParam String name,
                                Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        Category category = new Category();

        category.setName(name);
        category.setAuthor(user);

        categoryRepo.save(category);
        loadCategoriesToUser(user);

        return "redirect:/user/categories-and-labels";
    }


    //Удаление категории - кнопка
    @Transactional
    @RequestMapping(value = "/user/categories/delete")
    public String userDeleteCategory(Authentication authentication,
                                     @RequestParam long category_id,
                                     Model model) {
        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        categoryRepo.deleteByIdAndAuthor(category_id, user);

        loadCategoriesToUser(user);

        return "redirect:/user/categories-and-labels";
    }

    void loadCategoriesToUser(User user){

        List<Category> categories = categoryRepo.findByAuthor(user);

        user.setCategories(categories);
    }

}