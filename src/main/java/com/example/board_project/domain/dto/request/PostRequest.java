package com.example.board_project.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequest(
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(min = 1, max = 50, message = "제목은 1자에서 30자 사이로 입력해주세요.")
        String title,
        @NotBlank(message = "게시글을 입력해주세요.")
        @Size(min = 1, message = "게시글은 1자 이상 입력해주세요.")
        String content,
        @NotBlank(message = "작성자를 입력해주세요.")
        @Size(min = 1, max = 15, message = "작성자 명은 1자에서 15자 사이로 입력해주세요.")
        String writer
) {
}
