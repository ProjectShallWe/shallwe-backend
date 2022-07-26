package com.project.board.controller;

import com.project.board.global.AmazonS3.AwsS3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileController {
    private final AwsS3Uploader awsS3Uploader;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return awsS3Uploader.upload(multipartFile, "static");
    }
}
