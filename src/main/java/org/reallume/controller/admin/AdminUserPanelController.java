package org.reallume.controller.admin;

import org.reallume.domain.User;
import org.reallume.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminUserPanelController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/admin/users/{user_id}/stat")
    public String adminShowUser(Authentication authentication,
                               @PathVariable long user_id,
                               Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        Integer currentPage = 1;
        model.addAttribute("currentPage", currentPage);

        User thisuser = userRepo.findById(user_id);
        model.addAttribute("thisuser", thisuser);

        return "admin/user-panel-page";
    }

    @GetMapping(value = "/admin/users/{user_id}/stat/activate")
    public String adminActivateUser(Authentication authentication, @PathVariable long user_id, Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        User thisuser = userRepo.findById(user_id);
        thisuser.setActiveStatus(true);
        userRepo.save(thisuser);
        model.addAttribute("thisuser", thisuser);

        return "admin/user-panel-page";
    }

    @GetMapping(value = "/admin/users/{user_id}/stat/deactivate")
    public String adminDeactivateUser(Authentication authentication, @PathVariable long user_id, Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        User thisuser = userRepo.findById(user_id);
        thisuser.setActiveStatus(false);
        userRepo.save(thisuser);
        model.addAttribute("thisuser", thisuser);

        return "admin/user-panel-page";
    }


    @RequestMapping(value = "/admin/users/{user_id}/stat/delete", method = RequestMethod.GET)
    public String adminDeleteUser(@PathVariable long user_id) {

        userRepo.deleteById(user_id);

        return "redirect:/admin/user-list";
    }


}
