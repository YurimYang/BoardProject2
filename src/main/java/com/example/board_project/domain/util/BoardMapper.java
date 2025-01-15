package com.example.board_project.domain.util;

import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.dto.response.PostResponse;
import com.example.board_project.domain.entity.Board;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BoardMapper {

    public static Board toBoard(PostRequest postRequest) {
        return Board.builder()
                .title(postRequest.title())
                .content(postRequest.content())
                .writer(postRequest.writer())
                .build();
    }

    public static List<PostResponse> toAllPostListResponse(List<Board> boardList) {
        List<PostResponse> postResponse = new ArrayList<>();
        for(Board board : boardList) {
            if(!board.isDeleted()){
                postResponse.add(toPostResponse(board));
            }
        }
        return postResponse;
    }

    public static PostResponse toPostResponse(Board board) {
        return PostResponse.builder()
                .id(board.getId())
                .seq(board.getSeq())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
