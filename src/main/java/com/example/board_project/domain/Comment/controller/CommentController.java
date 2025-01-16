package com.example.board_project.domain.Comment.controller;

import com.example.board_project.domain.Comment.dto.request.CommentRequest;
import com.example.board_project.domain.Comment.dto.response.CommentResponse;
import com.example.board_project.domain.Comment.service.CommentService;
import com.example.board_project.global.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/comments")
@Tag(name = "COMMENT API", description = "온보딩 프로젝트 - 댓글 API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
    })
    @Operation(summary = "댓글 등록 API", description = "댓글을 등록하는 API입니다.")
    public ResponseDTO<CommentResponse> createPost(@Valid @RequestBody CommentRequest commentRequest) {
        return ResponseDTO.res(commentService.createComment(commentRequest), "댓글 등록을 성공했습니다.");
    }

    @GetMapping("/comment-history")
    @ApiResponse(responseCode = "200", description = "ok", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
    })
    @Operation(summary = "댓글 전체 조회 API", description = "게시판의 글을 전체 조회하는 API입니다.")
    public ResponseDTO<List<CommentResponse>> getAllPosts(String postId) {
        return ResponseDTO.res(commentService.getAllComments(postId), "해당 게시글의 댓글 조회에 성공했습니다.");
    }
}