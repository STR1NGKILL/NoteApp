package org.reallume.repos;

import org.reallume.domain.Category;
import org.reallume.domain.Label;
import org.reallume.domain.Note;
import org.reallume.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface LabelRepo extends CrudRepository<Label, Long> {

    Label findByName(String name);

    void deleteById(long label_id);

    Label findByIdAndAuthor(Long label_id, User author);

    void deleteById(Long label_id);

    List<Label> findAll();

    Label findById(long label_id);

    void deleteByIdAndAuthor(Long label_id, User author);

    List<Label> findByAuthor(User author);
}