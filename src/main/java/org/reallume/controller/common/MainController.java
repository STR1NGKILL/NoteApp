package org.reallume.controller.common;

import org.reallume.domain.User;
import org.reallume.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;

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

            userRepo.save(loginStatisticInit(user));
            model.addAttribute("user", user);
            user.setLastLogin();

            if (user.getRole().equals("ROLE_ADMIN"))
                return "redirect:/admin";
            if (user.getRole().equals("ROLE_USER"))
                return "redirect:/user";
        }

            return "guest/main-page";
        //-

    }

    public User loginStatisticInit(User user){

        Calendar calendar = Calendar.getInstance();
        Integer currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        Integer currentMonth = calendar.get(Calendar.MONTH) + 1;
        Integer currentYear = calendar.get(Calendar.YEAR);

        Integer userCurrentDay = user.getUserStat().getCurrentDay();
        Integer userCurrentMonth = user.getUserStat().getCurrentMonth();
        Integer userCurrentYear = user.getUserStat().getCurrentYear();

        if (currentDay.equals(userCurrentDay) && currentMonth.equals(userCurrentMonth) && currentYear.equals(userCurrentYear)) {
            user.getUserStat().setLoginPerDay();
        } else {
            user.getUserStat().setLoginPerDayReboot();
            user.getUserStat().setCurrentDay();
        }

        if (currentMonth.equals(userCurrentMonth) && currentYear.equals(userCurrentYear)) {
            user.getUserStat().setLoginPerMonth();
        } else {user.getUserStat().setLoginPerMonthReboot();
            user.getUserStat().setCurrentMonth();}

        if(currentYear.equals(userCurrentYear)){
            user.getUserStat().setLoginPerYear();
        } else {user.getUserStat().setLoginPerYearReboot();
            user.getUserStat().setCurrentYear();}

        return user;
    }

}
