package org.reallume.controller.user;

import org.reallume.domain.Label;
import org.reallume.domain.User;
import org.reallume.repos.LabelRepo;
import org.reallume.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class LabelController {

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private UserRepo userRepo;


    //Редактирование метки
    @PostMapping(value = "/user/labels/edit")
    public String userEditALabel(Authentication authentication,
                                   @RequestParam long label_id,
                                   @RequestParam String name,
                                   Model model) throws IOException {

        User user = userRepo.findByUsername(authentication.getName()).get();

        Label label = new Label();
        label.setAuthor(user);
        label.setId(label_id);
        label.setName(name);

        labelRepo.save(label);
        loadLabelsToUser(user);

        return "redirect:/user/categories-and-labels";
    }

    //Добавление метки
    @PostMapping(value = "/user/labels/add")
    public String userAddLabel(Authentication authentication,
                                  @RequestParam String name,
                                  Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        Label label = new Label();

        label.setName(name);
        label.setAuthor(user);

        labelRepo.save(label);
        loadLabelsToUser(user);

        return "redirect:/user/categories-and-labels";
    }


    //Удаление метки - кнопка
    @RequestMapping(value = "/user/labels/delete")
    public String userDeleteLabel(Authentication authentication,
                                     @RequestParam long label_id,
                                     Model model) {
        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        labelRepo.deleteByIdAndAuthor(label_id, user);

        loadLabelsToUser(user);

        return "redirect:/user/categories-and-labels";
    }

    void loadLabelsToUser(User user) {

        List<Label> labels = labelRepo.findByAuthor(user);

        user.setLabels(labels);
    }


}