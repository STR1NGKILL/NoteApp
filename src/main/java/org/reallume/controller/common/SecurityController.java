package org.reallume.controller.common;

import org.reallume.domain.User;
import org.reallume.repos.UserRepo;
import org.reallume.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        List<User> userList = userRepository.findAll();



        for (User exuser : userList) {
            if (user.getUsername().equals(exuser.getUsername())) {
                bindingResult.rejectValue("username", "error.username", "Пользователь с таким никнейном уже зарегистрирован!");
            }
        }

        for (User exuser : userList) {
            if (user.getEmail().toLowerCase().equals(exuser.getEmail().toLowerCase())) {
                bindingResult.rejectValue("email", "error.email", "Пользователь с такой электронной почтой уже зарегистрирован!");
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
            emailService.sendConfirmationMail(user.getEmail(), user.getUsername(), "http://localhost:8080/users/confirm/" + user.getToken());
            userRepository.save(user);

            return "successful-registration";
        }
    }

    @RequestMapping(value = "/users/confirm/{token}")
    public String emailConfirmPage(Model model, @PathVariable String token) {
        if (userRepository.findByToken(token).isEmpty()) {
            return "unsuccessful-acc-confirm";
        } else {
            User user = userRepository.findByToken(token).get();
            user.setActiveStatus(true);
            user.setToken(null);
            userRepository.save(user);

            return "successful-acc-confirm";
        }
    }
}