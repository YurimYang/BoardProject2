package com.example.board_project.domain.controller;

import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.service.BoardService;
import com.example.board_project.global.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "cafe review api", description = "카페 리뷰 관련 API")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "글 등록 API", description = "글을 게시판에 등록하는 API입니다.")
    public ResponseDTO<Long> createPost(@Valid @RequestBody PostRequest postRequest) {
        Long boardId = boardService.createBoard(postRequest);
        return ResponseDTO.res(boardId, "게시판에 글 등록을 성공했습니다.");
    }




}


