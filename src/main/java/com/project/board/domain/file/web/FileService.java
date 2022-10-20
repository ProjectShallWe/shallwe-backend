package com.project.board.domain.file.web;

import com.project.board.global.amazonS3.AwsS3Uploader;
import com.project.board.infrastructure.file.PostFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    private final PostFileRepository postFileRepository;
    private final AwsS3Uploader awsS3Uploader;


    @Transactional
    public String uploadPostImage(MultipartFile multipartFile) throws IOException {
        PostFile postFile = awsS3Uploader.upload(multipartFile, "static");
        postFileRepository.save(postFile);
        return postFile.getFileUrl();
    }
}
