package com.example.board_project.domain.Comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CommentRequest(
        @NotBlank(message = "게시글을 입력해주세요.")
        @Size(min = 1, message = "댓글은 1자 이상 입력해주세요.")
        String content,
        @NotBlank(message = "작성자를 입력해주세요.")
        @Size(min = 1, max = 15, message = "작성자 명은 1자에서 15자 사이로 입력해주세요.")
        String writer,
        @NotBlank(message = "게시글 id를 입력해주세요.")
        String postId
) {
}