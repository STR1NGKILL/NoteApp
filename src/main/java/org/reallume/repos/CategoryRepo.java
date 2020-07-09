package org.reallume.repos;

import org.reallume.domain.Category;
import org.reallume.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends CrudRepository<Category, Long> {

    Category findByName(String name);

    void deleteByIdAndAuthor(Long category_id, User author);

    Category findByIdAndAuthor(Long category_id, User author);

    Category findByNameAndAuthor(String category_name, User author);

    void deleteById(Long category_id);

    List<Category> findAll();

    Category findById(long category_id);

    List<Category> findByAuthor(User author);


}
