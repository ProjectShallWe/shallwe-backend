package com.project.board.infrastructure.like;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.domain.user.web.User;
import com.project.board.infrastructure.comment.CommentRepository;
import com.project.board.infrastructure.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class LikeCommentRepositoryTest {

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void findByUserIdAndPostId() {
        //given
        User user1 = User.builder()
                .email("google1234@gmail.com")
                .password("1234")
                .nickname("구글1234")
                .build();
        User user2 = User.builder()
                .email("google2345@gmail.com")
                .password("2345")
                .nickname("구글2345")
                .build();
        PostCategory postCategory = PostCategory.builder()
                .topic("국내 농구")
                .build();
        Post post = Post.builder()
                .title("농구 잘하는 법")
                .content("1. 농구공을 잘 던진다.")
                .user(user1)
                .postCategory(postCategory)
                .build();
        Comment comment = Comment.builder()
                .user(user1)
                .post(post)
                .parentCommentId(null)
                .content("농구공을 어떻게 잘 던지는데요?")
                .build();
        LikeComment likeComment = LikeComment.builder()
                .user(user2)
                .comment(comment)
                .build();
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Comment savedComment = commentRepository.save(comment);
        LikeComment savedLikeComment = likeCommentRepository.save(likeComment);

        //when
        Optional<LikeComment> findLikeComment = likeCommentRepository.findByUserIdAndCommentId(savedUser2.getId(), savedComment.getId());

        //then
        Assertions.assertThat(findLikeComment.get().getId()).isEqualTo(savedLikeComment.getId());
        Assertions.assertThat(findLikeComment.get().getUser()).isEqualTo(savedLikeComment.getUser());
        Assertions.assertThat(findLikeComment.get().getComment()).isEqualTo(savedLikeComment.getComment());
    }
}