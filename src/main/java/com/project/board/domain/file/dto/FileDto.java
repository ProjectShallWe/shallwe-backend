package com.project.board.domain.file.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDto {

    // 전송 받은 파일 이름
    @NotNull
    private String originalFileName;
    // UUID가 적용된 서버 저장용 파일 이름
    @NotNull
    private String storeFileName;
    // 파일 저장 경로
    @NotNull
    private String fileUrl;

    @Builder
    public FileDto(String originalFileName, String storeFileName, String fileUrl) {
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
        this.fileUrl = fileUrl;
    }
}
