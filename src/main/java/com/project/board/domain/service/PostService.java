package com.project.board.domain.service;

import com.project.board.domain.post.dto.PostUpdateRequestDto;
import com.project.board.domain.post.dto.PostWriteRequestDto;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostRepository;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long write(String email, PostWriteRequestDto postWriteRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        return postRepository.save(postWriteRequestDto.toEntity(user)).getId();
    }

    @Transactional
    public Long update(String email, Long id, PostUpdateRequestDto postUpdateRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다. post_id : " + id));
        if (user.getEmail().equals(post.getUser().getEmail())) {
           post.update(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());
           return id;
        }

        return -1L;
    }

    @Transactional
    public Long delete(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다. post_id : " + id));
        if (user.getEmail().equals(post.getUser().getEmail())) {
            post.updateIsDeletedToTrue();
            return id;
        }

        return -1L;
    }
}
