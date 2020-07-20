package org.reallume.controller.admin;

import org.apache.tomcat.util.codec.binary.Base64;
import org.reallume.repos.CategoryRepo;
import org.reallume.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.reallume.domain.Category;
import org.reallume.domain.User;
import org.reallume.service.UserDetailsImpl;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminProfileController {

    @Autowired
    private CategoryRepo categoryRepository;

    @Autowired
    private UserRepo userRepo;


    @GetMapping(value = "/admin/profile")
    public String userProfileEditPage(Authentication authentication,
                                      Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        return "admin/profile-page";
    }

    @PostMapping(value = "/admin/profile")
    public String userProfileEdit(Authentication authentication,
                                  Model model,
                                  @ModelAttribute("user") User user,
                                  BindingResult bindingResult
    ) {
        User thisuser = userRepo.findByUsername(authentication.getName()).get();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        List<User> users = userRepo.findAll();

        for (int i = 0; i < users.size(); i++) {
            User usr = users.get(i);
            if (usr.getId().equals(thisuser.getId())) {
                users.remove(i);
            }
        }

        for (User ituser : users) {
            if (user.getUsername().equals(ituser.getUsername())) {
                bindingResult.rejectValue("username", "error.username", "Пользователь с таким именем уже существует!");
            }
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue("password", "error.password", "Пароли не совпадают!");
        }


        for (User ituser : users) {
            if (user.getEmail().toLowerCase().equals(ituser.getEmail().toLowerCase())) {
                bindingResult.rejectValue("email", "error.email", "Пользователь с такой эл. почтой уже существует!");
            }
        }

        if (bindingResult.hasErrors()) {
            return "user/profile-page";
        } else {

            user.setId(thisuser.getId());
            user.setActiveStatus(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(thisuser.getRole());

            userRepo.save(user);

            authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            userDetails.setUsername(user.getUsername());
            userDetails.setPassword(user.getPassword());
        }

        return "redirect:/main";
    }
}

