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


    //Добавление метки
    @RequestMapping(value = "/user/notes/add/labels/add")
    public String userAddLabelToNewNote(Authentication authentication,
                                        @ModelAttribute("labels") List<Label> labels,
                                        @RequestParam String name,
                                        Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        Label label = new Label(name, user);
        labels.add(label);

        return "redirect:/user/notes/add";
    }


    //Удаление метки
    @GetMapping(value = "/user/notes/add/labels/{label_id}/delete")
    public String userDeleteLabel(Authentication authentication,
                                  @ModelAttribute("labels") List<Label> labels,
                                  @PathVariable long label_id,
                                  Model model) {
        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        labels.remove(findLabelById(labels, label_id));

        model.addAttribute("labels", labels);

        //labelRepo.deleteByIdAndAuthor(label_id, user);
        //loadLabelsToUser(user);


        return "redirect:/user/notes/add/labels/add";
    }


    Label findLabelById(List<Label> labels, Long label_id) {

        Label label = null;

        for (Label itLabel: labels) {
            if (itLabel.getId().equals(label_id)) {
                label = itLabel;
                return label;
            }
        }
        return label;
    }


    void loadLabelsToUser(User user) {

        List<Label> labels = labelRepo.findByAuthor(user);

        user.setLabels(labels);
    }


}