package org.reallume.controller.user;

import org.reallume.domain.Label;
import org.reallume.domain.Note;
import org.reallume.domain.Category;
import org.reallume.domain.User;
import org.reallume.repos.CategoryRepo;
import org.reallume.repos.LabelRepo;
import org.reallume.repos.NoteRepo;
import org.reallume.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class NoteController {

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private UserRepo userRepo;


    @GetMapping(value = "/user/notes")
    public String notesPage(Authentication authentication, Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        loadNotesToUser(user);

        List<Category> categories = user.getCategories();
        List<Note> notes = user.getNotes();



        model.addAttribute("categories", categories);
        model.addAttribute("notes", notes);

        Integer currentPage = 0;
        model.addAttribute("currentPage", currentPage);

        return "user/note/notes-page";
    }


    //Добавление заметки - кнопка
    @GetMapping(value = "/user/notes/add")
    public String userAddNote(Authentication authentication, Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        Note note = new Note();

        note.setAuthor(user);

        //List<Label> labels = new ArrayList<>();

        //model.addAttribute("labels", labels);
        model.addAttribute("categories", user.getCategories());
        model.addAttribute("note", note);

        return "user/note/add-note-page";
    }


    //Добавление заметки - страница
    @PostMapping(value = "/user/notes/add")
    public String userAddNotePage(Authentication authentication,
                                  /*@RequestParam("labels") List<Label> labels*/
                                @RequestParam("selectedCategory") Long selectedCategory, @ModelAttribute("note") Note note, Model model) throws IOException {

        User user = userRepo.findByUsername(authentication.getName()).get();

        note.setAuthor(user);
        note.setTime();

        Category category = findCategoryById(user.getCategories(), selectedCategory);
        note.setCategory(category);

        Category defCategory = findCategoryById(user.getCategories(), 0L);

        if(note.getCategory() == null){
            note.setCategory(defCategory);
        }

        //note.setLabels(labels);

        if(note.getName().isEmpty() || note.getName() == null){
            note.setName("Без названия");
        }

        if(note.getText().isEmpty() || note.getText() == null){
            note.setText("");
        }

        noteRepo.save(note);

        loadNotesToUser(user);

        userRepo.save(noteStatisticInit(user));



        return "redirect:/user/notes";
    }

    //Показать заметки данной категории
    @RequestMapping(value = "/user/notes/{category_id}/show-notes-of-category")
    public String showNotesOfThisCategory(Authentication authentication,
                                          @PathVariable Long category_id,
                                          Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        List<Note> notes = findNotesByCategory(user.getNotes(), category_id);
        List<Category> categories = user.getCategories();

        model.addAttribute("notes",notes);
        model.addAttribute("categories",categories);

        Integer currentPage = 0;
        model.addAttribute("currentPage", currentPage);

        //loadLabelsToUser(user);

        return "user/note/selected-notes-page";

    }

    //Изменение заметки - кнопка
    @GetMapping(value = "/user/notes/{note_id}/edit")
    public String editEditNote(Authentication authentication,
                                  @PathVariable Long note_id,
                                  Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        Note note = noteRepo.findByIdAndAuthor(note_id, user);
        note.setAuthor(user);
        note.setId(note_id);

        List<Category> categories = user.getCategories();
        //List<Label> labels = note.getLabels();

        model.addAttribute("note", note);
        model.addAttribute("categories", categories);
        //model.addAttribute("labels", labels);
        model.addAttribute("note_id", note_id);

        return "user/note/edit-note-page";
    }


    //Изменение заметки - страница
    @PostMapping(value = "/user/notes/{note_id}/edit")
    public String userEditNotePage(Authentication authentication,
                                   @PathVariable long note_id,
                                   @ModelAttribute("note") Note note,
                                 /*  @RequestParam("labels") List<Label> labels,*/
                                   @RequestParam("selectedCategory") Long selectedCategory,
                                   Model model) throws IOException {

        User user = userRepo.findByUsername(authentication.getName()).get();

        note.setId(note_id);
        note.setTime();

        //note.setLabels(labels);

        Category category = findCategoryById(user.getCategories(), selectedCategory);
        note.setCategory(category);

        Category defCategory = findCategoryById(user.getCategories(), 0L);
        if(note.getCategory() == null){
            note.setCategory(defCategory);
        }

        if(note.getName().isEmpty() || note.getName() == null){
            note.setName("Без названия");
        }

        if(note.getText().isEmpty() || note.getText() == null){
            note.setText("");
        }

        noteRepo.save(note);

        loadNotesToUser(user);

        return "redirect:/user/notes";
    }

    //Удаление заметки
    @Transactional
    @GetMapping(value = "/user/notes/{note_id}/delete")
    public String userDeleteNote(Authentication authentication, @PathVariable long note_id){

        User user = userRepo.findByUsername(authentication.getName()).get();

        noteRepo.deleteByIdAndAuthor(note_id, user);

        loadNotesToUser(user);

        return "redirect:/user/notes";
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


    List<Note> findNotesByCategory(List<Note> notes, Long category_id) {

        List<Note> selectedNotes = new ArrayList<>();

        for (Note itNote: notes) {
            if (itNote.getCategory().getId().equals(category_id)) {
                selectedNotes.add(itNote);
            }
        }
        return selectedNotes;
    }


    void loadNotesToUser(User user) {

        List<Note> notes = noteRepo.findByAuthor(user);

        user.setNotes(notes);
    }

    public User noteStatisticInit(User user){

        Calendar calendar = Calendar.getInstance();
        Integer currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        Integer currentMonth = calendar.get(Calendar.MONTH) + 1;
        Integer currentYear = calendar.get(Calendar.YEAR);

        Integer userCurrentDay = user.getUserStat().getCurrentDay();
        Integer userCurrentMonth = user.getUserStat().getCurrentMonth();
        Integer userCurrentYear = user.getUserStat().getCurrentYear();

        if (currentDay.equals(userCurrentDay) && currentMonth.equals(userCurrentMonth) && currentYear.equals(userCurrentYear)) {
            user.getUserStat().setNotePerDay();
        } else user.getUserStat().setNotePerDayReboot();

        if (currentMonth.equals(userCurrentMonth) && currentYear.equals(userCurrentYear)) {
            user.getUserStat().setNotePerMonth();
        } else user.getUserStat().setNotePerMonthReboot();

        if(currentYear.equals(userCurrentYear)){
            user.getUserStat().setNotePerYear();
        } else user.getUserStat().setNotePerYearReboot();

        return user;
    }



}