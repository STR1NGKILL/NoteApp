package org.reallume.repos;

import org.reallume.domain.Category;
import org.reallume.domain.Label;
import org.reallume.domain.Note;
import org.reallume.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepo extends CrudRepository<Note, Long> {

    List<Note> findByCategory(Category category);

    void deleteById(Long note_id);

    void deleteByCategory(Long category_id);

    void deleteByIdAndAuthor(Long note_id, User author);

    Note findByIdAndAuthor(Long note_id, User author);

    List<Note> findAll();

    List<Note> findByAuthor(User author);

    List<Note> findByCategory_Id(Long category_id);

}
