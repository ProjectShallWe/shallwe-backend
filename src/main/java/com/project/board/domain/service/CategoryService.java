package com.project.board.domain.service;

import com.project.board.domain.category.dto.CategoryRequestDto;
import com.project.board.domain.category.web.Category;
import com.project.board.domain.category.web.CategoryRepository;
import com.project.board.domain.user.web.Role;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    public Long open(String email, CategoryRequestDto categoryRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        if (user.getRole().equals(Role.ADMIN)){
            return categoryRepository.save(categoryRequestDto.toEntity()).getId();
        }
        return -1L;
    }

    public Long update(String email, Long id, CategoryRequestDto categoryRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다. category_id : " + id));
        if (user.getRole().equals(Role.ADMIN)) {
            category.update(categoryRequestDto.getTopic());

            return id;
        }
        return -1L;
    }

    public Long delete(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Category category = categoryRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다. category_id : " + id));
        if (user.getRole().equals(Role.ADMIN)) {
            categoryRepository.delete(category);
            return id;
        }

        return -1L;
    }
}
