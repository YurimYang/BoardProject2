package com.example.board_project.domain.Board.util;

import com.example.board_project.domain.Board.dto.request.PostRequest;
import com.example.board_project.domain.Board.dto.response.PostResponse;
import com.example.board_project.domain.Board.entity.Board;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//정적 유틸리티로 설계 (객체로 생성되지 않도록 설계)
//공통적인 변환 로직만 포함함 -> 굳이 객체를 생성하거나, 의존선 주입 방식으로 사용 x
//단, 상태가 없고, 정적 메소드만 포함하므로 Bean으로 관리할 필요 x
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
        for (Board board : boardList) {
            postResponse.add(toPostResponse(board));
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
