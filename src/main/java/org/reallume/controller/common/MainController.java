package org.reallume.controller.common;

import org.reallume.domain.User;
import org.reallume.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String enter(Model model) {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Model model, Authentication authentication) {

        if (authentication != null) {
            User user = userRepo.findByUsername(authentication.getName()).get();
            model.addAttribute("user", user);

            if (user.getRole().equals("ROLE_ADMIN"))
                return "redirect:/admin";
            if (user.getRole().equals("ROLE_USER"))
                return "redirect:/user";
        }

            return "guest/main-page";

    }

}
