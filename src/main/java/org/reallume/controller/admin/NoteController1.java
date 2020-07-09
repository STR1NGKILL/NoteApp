package org.reallume.controller.admin;

import org.reallume.domain.Category;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NoteController1 {

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private UserRepo userRepo;


    @GetMapping(value = "1/user/notes")
    public String notesPage(
                            Authentication authentication,

                            @ModelAttribute("categorySelected") Category categorySelected, Model model) {

        User user = userRepo.findByUsername(authentication.getName()).get();
        model.addAttribute("user", user);

        loadNotesToUser(user);

        List<Category> categories = user.getCategories();

        List<Note> notes;

        model.addAttribute("categories", categories);

        notes = user.getNotes();

        model.addAttribute("notes", notes);

        return "user/note/notes-page";
    }

    @RequestMapping(value = "/admin/category/{category_id}/notes/{note_id}/delete")
    public String deleteNote(Model model, @PathVariable long note_id, @PathVariable long category_id) {
        Category category = categoryRepo.findById(category_id);
        List<Note> notes = category.getNotes();

        notes.removeIf(item -> item.getId() == note_id);
        noteRepo.deleteById(note_id);

        return "redirect:/admin/notes?selectedCategory_id=0";
    }

    @GetMapping(value = "/admin/notes/add")
    public String addNotePage(Model model) {
        List<Category> categories = categoryRepo.findAll();
        Note note = new Note();

        model.addAttribute("categories", categories);
        model.addAttribute("note", note);

        return "add-note-page";
    }

    @PostMapping(value = {"/admin/notes/add"})
    public String adminAddNote(Model model, @ModelAttribute("note") Note note,
                               @RequestParam("category_id") Long category_id) throws IOException {
        Category ctg = categoryRepo.findById(category_id).get();

        note.setCategory(ctg);
        noteRepo.save(note);

        return "redirect:/admin/notes?selectedCategory_id=0";
    }


    @GetMapping(value = {"/admin/notes/{note_id}/edit"})
    public String adminEditItemPage(@PathVariable long note_id, Model model) {
        List<Category> categoryList = categoryRepo.findAll();

        model.addAttribute("categories", categoryList);
        model.addAttribute("item", noteRepo.findById(note_id));

        return "admin-notes-edit";
    }

    @PostMapping(value = {"/admin/notes/{note_id}/edit"})
    public String adminEditItem(Model model, @PathVariable long note_id, @ModelAttribute("item") Note note,
                                @RequestParam("category_id") long category_id) throws IOException {
        Category category = categoryRepo.findById(category_id);

        note.setId(note_id);
        note.setCategory(category);
        noteRepo.save(note);

        return "redirect:/admin/items?selcat=0";
    }

    void loadNotesToUser(User user) {

        List<Note> notes = noteRepo.findByAuthor(user);

        user.setNotes(notes);
    }

    List<Note> findByCategory(List<Note> notes, Long category_id){

        List<Note> selectedNotes = new ArrayList<Note>();

        for (Note note : notes) {
            if(note.getCategory().getId().equals(category_id)){
                selectedNotes.add(note);
            }
        }
        return selectedNotes;
    }

    Category findCategoryById(List<Category> categories, Long category_id){

        Category category = null;

        for (Category itCategory : categories) {
            if(itCategory.getId().equals(category_id)){
                category = itCategory;
                return category;
            }
        }
        return category;
    }

}