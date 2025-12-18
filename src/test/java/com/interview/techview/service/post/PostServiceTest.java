package com.interview.techview.service.post;

import com.interview.techview.domain.post.Post;
import com.interview.techview.dto.post.PostCreateRequest;
import com.interview.techview.dto.post.PostResponse;
import com.interview.techview.dto.post.PostUpdateRequest;
import com.interview.techview.exception.post.PasswordMismatchException;
import com.interview.techview.exception.post.PostNotFoundException;
import com.interview.techview.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * PostService 단위 테스트
 * 
 * FIRST 원칙 준수:
 * - Fast: Mock을 사용하여 빠르게 실행
 * - Independent: 각 테스트는 독립적
 * - Repeatable: 항상 같은 결과
 * - Self-Validating: 자체 검증
 * - Timely: 적시에 작성
 */
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PostService postService;

    @DisplayName("save: 게시글 작성에 성공한다")
    @Test
    void save() {
        // Given
        String title = "title";
        String content = "content";
        String name = "name";
        String password = "password123";
        String hashedPassword = "hashedPassword";

        PostCreateRequest dto = new PostCreateRequest(title, content, name, password);
        Post savedPost = Post.builder()
                .postId(1L)
                .title(title)
                .content(content)
                .name(name)
                .password(hashedPassword)
                .likeCount(0)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        when(passwordEncoder.encode(password)).thenReturn(hashedPassword);
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // When
        PostResponse result = postService.save(dto);

        // Then
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getName()).isEqualTo(name);

        verify(passwordEncoder).encode(password);
        verify(postRepository).save(any(Post.class));
    }

    @DisplayName("findAll: 게시글 전체 조회에 성공한다")
    @Test
    void findAll() {
        // Given
        Post post1 = Post.builder()
                .postId(1L)
                .title("title1")
                .content("content1")
                .name("name1")
                .password("password1")
                .build();

        Post post2 = Post.builder()
                .postId(2L)
                .title("title2")
                .content("content2")
                .name("name2")
                .password("password2")
                .build();

        List<Post> posts = Arrays.asList(post1, post2);
        when(postRepository.findAll()).thenReturn(posts);

        // When
        List<Post> result = postService.findAll();

        // Then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getTitle()).isEqualTo("title1");
        assertThat(result.get(1).getTitle()).isEqualTo("title2");

        verify(postRepository).findAll();
    }

    @DisplayName("findById: 게시글 단건 조회에 성공한다")
    @Test
    void findById() {
        // Given
        Long postId = 1L;
        Post post = Post.builder()
                .postId(postId)
                .title("title")
                .content("content")
                .name("name")
                .password("password")
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // When
        Post result = postService.findById(postId);

        // Then
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getTitle()).isEqualTo("title");

        verify(postRepository).findById(postId);
    }

    @DisplayName("findById: 게시글을 찾을 수 없으면 PostNotFoundException을 발생시킨다")
    @Test
    void findById_NotFound() {
        // Given
        Long postId = 999L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.findById(postId))
                .isInstanceOf(PostNotFoundException.class);

        verify(postRepository).findById(postId);
    }

    @DisplayName("delete: 게시글 삭제에 성공한다")
    @Test
    void delete() {
        // Given
        Long postId = 1L;
        String inputPassword = "password123";
        String hashedPassword = "hashedPassword";

        Post post = Post.builder()
                .postId(postId)
                .title("title")
                .content("content")
                .name("name")
                .password(hashedPassword)
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(passwordEncoder.matches(inputPassword, hashedPassword)).thenReturn(true);

        // When
        postService.delete(postId, inputPassword);

        // Then
        verify(postRepository).findById(postId);
        verify(passwordEncoder).matches(inputPassword, hashedPassword);
        verify(postRepository).delete(post);
    }

    @DisplayName("delete: 비밀번호가 일치하지 않으면 PasswordMismatchException을 발생시킨다")
    @Test
    void delete_PasswordMismatch() {
        // Given
        Long postId = 1L;
        String inputPassword = "wrongPassword";
        String hashedPassword = "hashedPassword";

        Post post = Post.builder()
                .postId(postId)
                .title("title")
                .content("content")
                .name("name")
                .password(hashedPassword)
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(passwordEncoder.matches(inputPassword, hashedPassword)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> postService.delete(postId, inputPassword))
                .isInstanceOf(PasswordMismatchException.class);

        verify(postRepository).findById(postId);
        verify(passwordEncoder).matches(inputPassword, hashedPassword);
        verify(postRepository, never()).delete(any(Post.class));
    }

    @DisplayName("delete: 게시글을 찾을 수 없으면 PostNotFoundException을 발생시킨다")
    @Test
    void delete_NotFound() {
        // Given
        Long postId = 999L;
        String inputPassword = "password123";

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.delete(postId, inputPassword))
                .isInstanceOf(PostNotFoundException.class);

        verify(postRepository).findById(postId);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(postRepository, never()).delete(any(Post.class));
    }

    @DisplayName("update: 게시글 수정에 성공한다")
    @Test
    void update() {
        // Given
        Long postId = 1L;
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        String password = "password123";
        String hashedPassword = "hashedPassword";

        Post post = Post.builder()
                .postId(postId)
                .title("title")
                .content("content")
                .name("name")
                .password(hashedPassword)
                .build();

        PostUpdateRequest dto = new PostUpdateRequest(updateTitle, updateContent, password);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);

        // When
        PostResponse result = postService.update(postId, dto);

        // Then
        assertThat(result.getTitle()).isEqualTo(updateTitle);
        assertThat(result.getContent()).isEqualTo(updateContent);

        verify(postRepository).findById(postId);
        verify(passwordEncoder).matches(password, hashedPassword);
    }

    @DisplayName("update: 비밀번호가 일치하지 않으면 PasswordMismatchException을 발생시킨다")
    @Test
    void update_PasswordMismatch() {
        // Given
        Long postId = 1L;
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        String wrongPassword = "wrongPassword";
        String hashedPassword = "hashedPassword";

        Post post = Post.builder()
                .postId(postId)
                .title("title")
                .content("content")
                .name("name")
                .password(hashedPassword)
                .build();

        PostUpdateRequest dto = new PostUpdateRequest(updateTitle, updateContent, wrongPassword);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(passwordEncoder.matches(wrongPassword, hashedPassword)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> postService.update(postId, dto))
                .isInstanceOf(PasswordMismatchException.class);

        verify(postRepository).findById(postId);
        verify(passwordEncoder).matches(wrongPassword, hashedPassword);
    }

    @DisplayName("update: 게시글을 찾을 수 없으면 PostNotFoundException을 발생시킨다")
    @Test
    void update_NotFound() {
        // Given
        Long postId = 999L;
        PostUpdateRequest dto = new PostUpdateRequest("title", "content", "password");

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.update(postId, dto))
                .isInstanceOf(PostNotFoundException.class);

        verify(postRepository).findById(postId);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }
}
