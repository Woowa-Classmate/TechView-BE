package com.interview.techview.service.user;

import com.interview.techview.domain.category.Category;
import com.interview.techview.domain.skill.Skill;
import com.interview.techview.domain.user.Role;
import com.interview.techview.domain.user.User;
import com.interview.techview.dto.user.AdminUserCreateRequest;
import com.interview.techview.dto.user.AdminUserUpdateRequest;
import com.interview.techview.dto.user.UpdateUserProfileRequest;
import com.interview.techview.dto.user.UserListResponse;
import com.interview.techview.dto.user.UserProfileResponse;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.domain.post.Post;
import com.interview.techview.repository.auth.RefreshTokenRepository;
import com.interview.techview.repository.category.CategoryRepository;
import com.interview.techview.repository.comment.CommentRepository;
import com.interview.techview.repository.post.PostBookmarkRepository;
import com.interview.techview.repository.post.PostLikeRepository;
import com.interview.techview.repository.post.PostRepository;
import com.interview.techview.repository.skill.SkillRepository;
import com.interview.techview.repository.user.UserCategoryRepository;
import com.interview.techview.repository.user.UserRepository;
import com.interview.techview.repository.user.UserSkillRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SkillRepository skillRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final UserSkillRepository userSkillRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostBookmarkRepository postBookmarkRepository;

    // 내 정보 조회
    @Transactional(readOnly = true)
    public UserProfileResponse getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserProfileResponse.from(user);
    }

    // 내 정보 변경
    @Transactional
    public void updateMyInfo(Long userId, UpdateUserProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updateName(request.getName());
    }

    // 내 분야 변경
    @Transactional
    public void updateCategories(Long userId, List<Long> categoryIds) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 기존 매핑 제거
        userCategoryRepository.deleteByUserId(userId);
        userCategoryRepository.flush();

        // 존재하는 분야인지 검증
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // 새 매핑 생성
        user.setCategories(categories);
    }

    // 내 분야 조회
    public List<String> getCategories(Long userId) {
        return userCategoryRepository.findByUserIdWithCategory(userId)
                .stream()
                .map(uc -> uc.getCategory().getName())
                .toList();
    }

    // 내 기술스택 변경
    @Transactional
    public void updateSkills(Long userId, List<Long> skillIds) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 기존 매핑 제거
        userSkillRepository.deleteByUserId(userId);
        userSkillRepository.flush();

        // 존재하는 분야인지 검증
        List<Skill> skills = skillRepository.findAllById(skillIds);
        if (skills.size() != skillIds.size()) {
            throw new CustomException(ErrorCode.SKILL_NOT_FOUND);
        }

        user.setSkills(skills);
    }

    // 내 기술스택 조회
    public List<String> getSkills(Long userId) {
        return userSkillRepository.findByUserIdWithSkill(userId)
                .stream()
                .map(us -> us.getSkill().getName())
                .toList();
    }

    // 모든 사용자 조회 (관리자용)
    @Transactional(readOnly = true)
    public List<UserListResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserListResponse::from)
                .toList();
    }

    // 사용자 검색 (관리자용)
    @Transactional(readOnly = true)
    public List<UserListResponse> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllUsers();
        }
        
        return userRepository.findByEmailContainingOrNameContaining(keyword, keyword)
                .stream()
                .map(UserListResponse::from)
                .toList();
    }

    // 사용자 추가 (관리자용)
    @Transactional
    public UserListResponse createUser(AdminUserCreateRequest request) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        return UserListResponse.from(savedUser);
    }

    // 사용자 수정 (관리자용)
    @Transactional
    public UserListResponse updateUser(Long userId, AdminUserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 이메일 수정
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            // 이메일 중복 체크
            userRepository.findByEmail(request.getEmail())
                    .filter(existingUser -> !existingUser.getId().equals(userId))
                    .ifPresent(existingUser -> {
                        throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
                    });
            user.updateEmail(request.getEmail());
        }

        // 이름 수정
        if (request.getName() != null && !request.getName().isBlank()) {
            user.updateName(request.getName());
        }

        // 권한 수정
        if (request.getRole() != null) {
            user.updateRole(request.getRole());
        }

        return UserListResponse.from(user);
    }

    // 사용자 삭제 (관리자용)
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 1. 사용자가 작성한 모든 게시글 조회
        List<Post> userPosts = postRepository.findByUserId(userId);

        // 2. 각 게시글에 대해 관련 데이터 삭제 (외래 키 제약 조건 해결)
        for (Post post : userPosts) {
            Long postId = post.getPostId();
            
            // 댓글 삭제
            commentRepository.deleteAll(commentRepository.findByPost_PostId(postId));
            
            // 좋아요 삭제
            postLikeRepository.deleteByPost_PostId(postId);
            
            // 북마크 삭제
            postBookmarkRepository.deleteByPost_PostId(postId);
        }

        // 3. 게시글 삭제
        postRepository.deleteAll(userPosts);

        // 4. Refresh Token 삭제
        refreshTokenRepository.deleteByUserId(userId);

        // 5. 사용자 삭제 (UserCategory, UserSkill은 cascade로 자동 삭제됨)
        userRepository.delete(user);
    }
}

