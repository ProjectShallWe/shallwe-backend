package com.project.board.domain.service;

import com.project.board.domain.post.dto.PostUpdateRequestDto;
import com.project.board.domain.post.dto.PostWriteRequestDto;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostReader;
import com.project.board.domain.post.web.PostStore;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserReader userReader;
    private final PostReader postReader;
    private final PostStore postStore;

    @Transactional
    public Long write(String email, PostWriteRequestDto writeDto) {
        User user = userReader.getUserBy(email);
        return postStore.store(writeDto.toEntity(user)).getId();
    }

    @Transactional
    public Long update(String email, Long id, PostUpdateRequestDto updateDto) {
        User user = userReader.getUserBy(email);
        Post post = postReader.getPostBy(id);
        if (isPostWriter(user, post)) {
           post.update(updateDto.getTitle(), updateDto.getContent());
           return id;
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long id) {
        User user = userReader.getUserBy(email);
        Post post = postReader.getPostBy(id);
        if (isPostWriter(user, post) || isAdmin(user)) {
            post.updateStatusToDisable();
            return id;
        }
        return -1L;
    }

    private Boolean isPostWriter(User user, Post post) {
        return user.getEmail().equals(post.getUser().getEmail());
    }

    private Boolean isAdmin(User user) {
        return user.getRole().equals(User.Role.ADMIN);
    }
}
