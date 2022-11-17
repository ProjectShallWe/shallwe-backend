package com.project.board.domain.file.web;

import com.project.board.domain.file.dto.FileDto;
import com.project.board.domain.post.web.Post;
import com.project.board.global.amazonS3.AwsS3Uploader;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.infrastructure.file.PostFileRepository;
import com.project.board.infrastructure.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final AwsS3Uploader awsS3Uploader;
    private final static String dirName = "static";

    @Transactional
    public String uploadPostImage(MultipartFile multipartFile) throws IOException {
        FileDto filedto = awsS3Uploader.upload(multipartFile, dirName);
        PostFile postFile = PostFile.builder()
                .fileUrl(filedto.getFileUrl())
                .storeFileName(filedto.getStoreFileName())
                .originalFileName(filedto.getOriginalFileName())
                .build();
        postFileRepository.save(postFile);
        return postFile.getFileUrl();
    }

    @Transactional
    public void createPostImage(Long postId, List<String> fileUrls) {
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        for (String fileUrl : fileUrls) {
            PostFile postFile = postFileRepository.findByFileUrl(fileUrl)
                    .orElseThrow(EntityNotFoundException::new);
            postFile.updatePost(post);
        }
    }

    @Transactional
    public Long updatePostImage(Long postId, List<String> updatedFileUrls) {
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        List<String> originalPostFileUrlList = postFileRepository.findImageUrlsInPost(postId);

        // 새로 추가된 이미지를 차집합으로 추출한다.
        List<String> newImageList = getDifferenceTo(updatedFileUrls, originalPostFileUrlList);

        // 새로 추가된 이미지에 post값을 부여한다.
        for (String imageUrl : newImageList) {
            PostFile postFile = postFileRepository.findByFileUrl(imageUrl)
                    .orElseThrow(EntityNotFoundException::new);
            postFile.updatePost(post);
        }

        // 삭제된 이미지 리스트를 차집합으로 추출한다.
        List<String> deleteImageList = getDifferenceTo(originalPostFileUrlList, updatedFileUrls);

        // 삭제된 이미지는 post값을 null로 준다.
        for (String imageUrl : deleteImageList) {
            PostFile postFile = postFileRepository.findByFileUrl(imageUrl)
                    .orElseThrow(EntityNotFoundException::new);
            postFile.updatePost(null);
        }

        return post.getId();
    }

    private List<String> getDifferenceTo(List<String> A, List<String> B) {
        return A.stream()
                .filter(i -> !B.contains(i))
                .collect(Collectors.toList());
    }
}
