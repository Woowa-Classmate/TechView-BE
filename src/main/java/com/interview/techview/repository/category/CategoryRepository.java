package com.interview.techview.repository.category;

import com.interview.techview.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    Optional<Category> findByName(String name);
    Optional<Category> findByNameIgnoreCase(String name);
}
