package com.interview.techview.repository.user;

import com.interview.techview.domain.user.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

    @Query("""
        SELECT uc FROM UserCategory uc
        JOIN FETCH uc.category
        WHERE uc.user.id = :userId
    """)
    List<UserCategory> findByUserIdWithCategory(Long userId);

    List<UserCategory> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
