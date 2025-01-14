package com.example.board_project.domain.controller;

import com.example.board_project.domain.dto.request.PostPatchRequest;
import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.dto.response.PostResponse;
import com.example.board_project.domain.service.BoardService;
import com.example.board_project.global.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards")
@Tag(name = "onboarding project api", description = "온보딩 프로젝트 관련 API")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "글 등록 API", description = "글을 게시판에 등록하는 API입니다.")
    public ResponseDTO<String> createPost(@Valid @RequestBody PostRequest postRequest) {
        String boardId = boardService.createBoard(postRequest);
        return ResponseDTO.res(boardId, "게시판에 글 등록을 성공했습니다.");
    }

    @GetMapping("/post-history")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시글 전체 조회 API", description = "게시판의 글을 전체 조회하는 API입니다.")
    public ResponseDTO<List<PostResponse>> getAllPosts() {
        return ResponseDTO.res(boardService.getAllPosts(), "게시판 전체 조회에 성공했습니다.");
    }

    @GetMapping("/post-history/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "특정 게시글 조회 API", description = "게시판의 특정 글을 조회하는 API입니다.")
    public ResponseDTO<PostResponse> getPost(@PathVariable("post_id") String postId) {
        return ResponseDTO.res(boardService.getPost(postId), postId + "번 글 조회에 성공했습니다.");
    }

    @PatchMapping("/post-history/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "특정 게시글 수정 API", description = "게시판의 특정 글을 수정하는 API입니다.")
    public ResponseDTO<String> updatePost(@PathVariable("post_id") String postId, @Valid @RequestBody PostPatchRequest postPatchRequest) {
        return ResponseDTO.res(boardService.updatePost(postId, postPatchRequest), postId + "번 글 수정에 성공했습니다.");
    }

    @DeleteMapping("/post-history/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "특정 게시글 삭제 API", description = "게시판의 특정 글을 삭제하는 API입니다.")
    public ResponseDTO<String> deletePost(@PathVariable("post_id") String postId) {
        boardService.deletePost(postId);
        return ResponseDTO.res(postId + "번 글 삭제에 성공했습니다.");
    }

}

