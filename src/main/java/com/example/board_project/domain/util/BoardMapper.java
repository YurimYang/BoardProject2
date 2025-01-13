package com.example.board_project.domain.util;

import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.dto.response.AllPostsResponse;
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

    public static List<AllPostsResponse> toAllPostListResponse(List<Board> boardList) {
        List<AllPostsResponse> allPostsResponses = new ArrayList<>();
        for(Board board : boardList) {
            allPostsResponses.add(toAllPostsResponse(board));
        }
        return allPostsResponses;
    }

    public static AllPostsResponse toAllPostsResponse(Board board) {
        return AllPostsResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
