package org.reallume.controller.admin;

import org.reallume.domain.Category;
import org.reallume.domain.Label;
import org.reallume.domain.Note;
import org.reallume.domain.User;
import org.reallume.repos.CategoryRepo;
import org.reallume.repos.LabelRepo;
import org.reallume.repos.NoteRepo;
import org.reallume.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AdminStatisticController {

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private UserRepo userRepo;


    @GetMapping(value = "/admin/statistics")
    public String notesPage(Authentication authentication, Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        Integer currentPage = 0;
        model.addAttribute("currentPage", currentPage);

        List<Category> categories = categoryRepo.findAll();
        List<Note> notes = noteRepo.findAll();
        List<Label> labels = labelRepo.findAll();
        List<User> users = userRepo.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("notes", notes);
        model.addAttribute("labels", labels);
        model.addAttribute("users", users);

        Long loginPerDayOverall = loginPerDayOverall();
        Long loginPerMonthOverall = loginPerMonthOverall();
        Long loginPerYearOverall = loginPerYearOverall();

        model.addAttribute("loginPerDayOverall", loginPerDayOverall);
        model.addAttribute("loginPerMonthOverall", loginPerMonthOverall);
        model.addAttribute("loginPerYearOverall", loginPerYearOverall);

        Long notePerDayOverall = notePerDayOverall();
        Long notePerMonthOverall = notePerMonthOverall();
        Long notePerYearOverall = notePerYearOverall();

        model.addAttribute("notePerDayOverall", notePerDayOverall);
        model.addAttribute("notePerMonthOverall", notePerMonthOverall);
        model.addAttribute("notePerYearOverall", notePerYearOverall);

        Long registrationPerDay = registrationPerDay();
        Long registrationPerMonth = registrationPerMonth();
        Long registrationPerYear = registrationPerYear();

        model.addAttribute("registrationPerDay", registrationPerDay);
        model.addAttribute("registrationPerMonth", registrationPerMonth);
        model.addAttribute("registrationPerYear", registrationPerYear);

        Long usersActivatedCount = usersActivatedCount();
        Long usersDeactivatedCount = usersDeactivatedCount();

        model.addAttribute("usersActivatedCount", usersActivatedCount);
        model.addAttribute("usersDeactivatedCount", usersDeactivatedCount);

        return "admin/statistic-page";
    }

    public Long loginPerDayOverall(){
        List<User> users = userRepo.findAll();

        Long loginPerDayOverall = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin"))
            loginPerDayOverall += ituser.getUserStat().getLoginPerDay();
        }

        return loginPerDayOverall;
    }

    public Long loginPerMonthOverall(){
        List<User> users = userRepo.findAll();

        Long loginPerMonthOverall = 0L;


        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin"))
            loginPerMonthOverall += ituser.getUserStat().getLoginPerMonth();
        }

        return loginPerMonthOverall;
    }

    public Long loginPerYearOverall(){
        List<User> users = userRepo.findAll();

        Long loginPerYearOverall = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin"))
            loginPerYearOverall += ituser.getUserStat().getLoginPerYear();
        }

        return loginPerYearOverall;
    }

    public Long notePerDayOverall(){
        List<User> users = userRepo.findAll();

        Long notePerDayOverall = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin"))
            notePerDayOverall += ituser.getUserStat().getNotePerDay();
        }

        return notePerDayOverall;
    }

    public Long notePerMonthOverall(){
        List<User> users = userRepo.findAll();

        Long notePerMonthOverall = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin"))
            notePerMonthOverall += ituser.getUserStat().getNotePerMonth();
        }

        return notePerMonthOverall;
    }

    public Long notePerYearOverall(){
        List<User> users = userRepo.findAll();

        Long notePerYearOverall = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin"))
            notePerYearOverall += ituser.getUserStat().getNotePerYear();
        }

        return notePerYearOverall;
    }

    public Long registrationPerDay(){
        List<User> users = userRepo.findAll();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat("d MMMM yyyy");


        Long registrationPerDay = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin")) {
                if (dateFormat.format(currentDate).equals(dateFormat.format(ituser.getRegistDateOrigin()))) {
                    registrationPerDay++;
                }
            }
        }

        return registrationPerDay;
    }

    public Long registrationPerMonth(){
        List<User> users = userRepo.findAll();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat("MMMM yyyy");


        Long registrationPerMonth = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin")) {
                if (dateFormat.format(currentDate).equals(dateFormat.format(ituser.getRegistDateOrigin()))) {
                    registrationPerMonth++;
                }
            }
        }

        return registrationPerMonth;
    }

    public Long registrationPerYear(){
        List<User> users = userRepo.findAll();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat("yyyy");


        Long registrationPerYear = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin")) {
                if (dateFormat.format(currentDate).equals(dateFormat.format(ituser.getRegistDateOrigin()))) {
                    registrationPerYear++;
                }
            }
        }

        return registrationPerYear;
    }

    public Long usersActivatedCount(){
        List<User> users = userRepo.findAll();

        long usersActivatedCount = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin") && ituser.getActiveStatus().equals(true)){
                usersActivatedCount++;
            }
        }
        return usersActivatedCount;
    }

    public Long usersDeactivatedCount(){
        List<User> users = userRepo.findAll();

        long usersDeactivatedCount = 0L;

        for (User ituser: users) {
            if(!ituser.getUsername().equals("admin") && ituser.getActiveStatus().equals(false)){
                usersDeactivatedCount++;
            }
        }
        return usersDeactivatedCount;
    }






}
