package org.reallume.controller.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.reallume.domain.Category;
import org.reallume.domain.User;
import org.reallume.domain.UserStat;
import org.reallume.repos.UserRepo;
import org.reallume.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class SecurityController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping(value = "/access-error")
    public String accessErrorPage(Model model) {
        return "access-denied-page";
    }

    @GetMapping(value = {"/registration"})
    public String registrationPage(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "registration";
    }

    @PostMapping(value = {"/registration"})
    public String registration(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) throws Exception {
        List<User> users = userRepository.findAll();


        for (User ituser : users) {
            if (user.getUsername().equals(ituser.getUsername()) && !user.getId().equals(ituser.getId())) {
                bindingResult.rejectValue("username", "error.username", "Пользователь с таким именем уже существует!");
            }
        }

        if ((user.getUsername().equals("Admin") || user.getUsername().equals("admin"))) {
            bindingResult.rejectValue("username", "error.username", "Запрещённое имя пользователя!");
        }

        for (User ituser : users) {
            if (user.getEmail().toLowerCase().equals(ituser.getEmail().toLowerCase()) && !user.getId().equals(ituser.getId())) {
                bindingResult.rejectValue("email", "error.email", "Пользователь с такой эл. почтой уже существует");
            }
        }

        if (bindingResult.hasErrors()) {
            return "registration";

        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setActiveStatus(false);
            user.setEmail(user.getEmail().toLowerCase());
            user.setRole("ROLE_USER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setToken(emailService.generateToken(user.getUsername()));
            emailService.sendConfirmationMail(user.getEmail(), user.getUsername(), "https://note-app-practice.herokuapp.com/users/confirm/" + user.getToken());
            userRepository.save(user);

            return "successful-registration";
        }
    }

    @RequestMapping(value = "/users/confirm/{token}")
    public String emailConfirmPage(Model model, @PathVariable String token) {
        if (!userRepository.findByToken(token).isPresent()) {
            return "unsuccessful-acc-confirm";
        } else {
            User user = userRepository.findByToken(token).get();
            user.setActiveStatus(true);
            user.setToken(null);

            List<Category> categories = new ArrayList<>();
            Category category = new Category("По-умолчанию", user);
            category.setId(0L);
            categories.add(category);
            user.setCategories(categories);
            user.setRegistDate(new Date());
            UserStat userStat = new UserStat();
            user.setUserStat(userStat);
            userRepository.save(user);

            return "successful-acc-confirm";
        }
    }

    @GetMapping(value = "/recovering")
    public String passwordRecovering(Model model) throws Exception {

        String loginOrEmail = new String("");

        model.addAttribute("loginOrEmail", loginOrEmail);

        return "password-recovering";
    }


    @PostMapping(value = "/recovering")
    public String passwordRecoveringPage(@RequestParam("loginOrEmail") String loginOrEmail) throws Exception {
        List<User> users = userRepository.findAll();

        String email = "";

        User user = null;

        for (User ituser : users) {
            if (ituser.getUsername().equals(loginOrEmail)) {
                user = ituser;
                email = user.getEmail();

                break;
            }

            if (ituser.getEmail().equals(loginOrEmail)) {
                user = ituser;
                email = ituser.getEmail();

                break;
            }
        }

        String newPassword = generatePassword();
        user.setPassword(newPassword);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(newPassword));
        emailService.sendRecoveryMail(email, user.getUsername(), newPassword);
        userRepository.save(user);

        return "successful-acc-recovery";

    }

    public String generatePassword() {
        String randomUUID = UUID.randomUUID().toString();

        String originalPassword = randomUUID;

        String ch = ".-!@#$%^&*()_+!№;%:?*/\\\"~";
        for (char c : ch.toCharArray()) {
            originalPassword = originalPassword.replace(c, ' ');
        }
        originalPassword = originalPassword.replaceAll(" ", "");

        originalPassword = originalPassword.substring(0, originalPassword.length()-25);

        return originalPassword.toUpperCase();
    }

    @GetMapping(value = "/admin/users/{user_id}/stat/recovering")
    public String passwordRecoveringAdmin(@PathVariable("user_id") long user_id, Model model) throws Exception {

        User user = userRepository.findById(user_id);

        String newPassword = generatePassword();
        user.setPassword(newPassword);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(newPassword));
        emailService.sendRecoveryMail(user.getEmail(), user.getUsername(), newPassword);
        userRepository.save(user);
        model.addAttribute("thisuser", user);

        return "admin/user-panel-page";

    }


}
