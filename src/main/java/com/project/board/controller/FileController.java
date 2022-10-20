package com.project.board.controller;

import com.project.board.domain.file.web.FileService;
import com.project.board.global.amazonS3.AwsS3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return fileService.uploadPostImage(multipartFile);
    }
}
