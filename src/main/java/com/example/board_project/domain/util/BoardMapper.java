package com.example.board_project.domain.util;

import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.entity.Board;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BoardMapper {

    public static Board toBoard(PostRequest postRequest) {
        return Board.builder()
                .title(postRequest.title())
                .content(postRequest.content())
                .writer(postRequest.writer())
                .build();
    }
}
