package com.project.board.domain.post.service;

import com.project.board.domain.post.dto.PostWriteRequestDto;
import com.project.board.domain.post.repository.PostRepository;
import com.project.board.domain.user.model.User;
import com.project.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
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
}
