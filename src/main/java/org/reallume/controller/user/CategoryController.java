package org.reallume.controller.user;

import org.reallume.domain.Category;
import org.reallume.domain.LitCategory;
import org.reallume.domain.Note;
import org.reallume.domain.User;
import org.reallume.repos.CategoryRepo;
import org.reallume.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping(value = {"/user/categories/{category_id}/edit"})
    public String adminEditCategoryPage(Authentication authentication, Model model, @PathVariable long category_id) {
        User user = userRepo.findByUsername(authentication.getName()).get();

        Category category = categoryRepo.findByIdAndAuthor(category_id, user);

        model.addAttribute("editCategory", category);

        return "/user/note/notes-page";
    }


    //Редактирование категории
    @PostMapping(value = "/user/categories/edit")
    public String userEditCategory(Authentication authentication,
                                  @RequestParam long category_id,
                                   @RequestParam String name,
                                  Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();

        Category category = categoryRepo.findByIdAndAuthor(category_id, user);

        category.setName(name);
        category.setAuthor(user);
        category.setId(category_id);

        categoryRepo.save(category);
        loadCategoriesToUser(user);

        return "redirect:/user/notes";
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

        return "redirect:/user/notes";
    }


    //Удаление категории - кнопка
    @Transactional
    @GetMapping(value = "/user/categories/delete")
    public String userDeleteCategory(Authentication authentication,
                                     long category_id) {
        User user = userRepo.findByUsername(authentication.getName()).get();

        categoryRepo.deleteByIdAndAuthor(category_id, user);

        loadCategoriesToUser(user);

        return "redirect:/user/notes";
    }

    void loadCategoriesToUser(User user){

        List<Category> categories = categoryRepo.findByAuthor(user);

        user.setCategories(categories);
    }

    Category findCategoryById(List<Category> categories, Long category_id) {

        Category category = null;

        for (Category itCategory : categories) {
            if (itCategory.getId().equals(category_id)) {
                category = itCategory;
                return category;
            }
        }
        return category;
    }

    @GetMapping(value = "/user/categories/find-category")
    @ResponseBody
    public LitCategory findCategory(Authentication authentication ,long category_id)
    {
        User user = userRepo.findByUsername(authentication.getName()).get();

        Category category = findCategoryById(user.getCategories(), category_id);

        LitCategory litCategory = new LitCategory(category_id, category.getName());

        return litCategory;
    }



}