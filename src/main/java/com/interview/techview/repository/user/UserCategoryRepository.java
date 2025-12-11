package com.interview.techview.repository.user;

import com.interview.techview.domain.user.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

    List<UserCategory> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
