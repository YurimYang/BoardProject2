package com.example.board_project.domain.Board.dto.request;

import jakarta.validation.constraints.Size;

public record PostPatchRequest(
        @Size(min = 1, max = 50, message = "제목은 1자에서 30자 사이로 입력해주세요.")
        String title,
        @Size(min = 1, message = "게시글은 1자 이상 입력해주세요.")
        String content
) {

}
