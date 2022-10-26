package com.project.board.domain.auth.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenReqDto {

    private String refreshToken;
}
