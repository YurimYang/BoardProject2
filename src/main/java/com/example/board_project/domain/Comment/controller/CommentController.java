package com.example.board_project.domain.Comment.controller;

import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
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
    @ApiResponse(responseCode = "201", description = "Created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
    })
    @Operation(summary = "댓글 등록 API", description = "댓글을 등록하는 API입니다.")
    public ResponseDTO<CommentResponse> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        return ResponseDTO.res(commentService.createComment(commentRequest), "댓글 등록을 성공했습니다.");
    }

    @GetMapping("/{post_id}")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
    })
    @Operation(summary = "댓글 전체 조회 API", description = "게시판의 댓글을 전체 조회하는 API입니다.")
    public ResponseDTO<List<CommentResponse>> getAllComments(@PathVariable("post_id") String postId) {
        return ResponseDTO.res(commentService.getAllComments(postId), "해당 게시글의 댓글 조회에 성공했습니다.");
    }

    @PatchMapping("/{comment_id}")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
    })
    @Operation(summary = "특정 댓글 수정 API", description = "특정 댓글을 수정하는 API입니다.")
    public ResponseDTO<CommentResponse> updateComment(@PathVariable("comment_id") String commentId,
                                             @Valid @RequestBody CommentPatchRequest commentPatchRequest) {
        return ResponseDTO.res(commentService.updateComment(commentId, commentPatchRequest), "댓글 수정에 성공했습니다.");
    }

    @DeleteMapping("/{comment_id}")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    })
    @Operation(summary = "특정 댓글 삭제 API", description = "특정 댓글을 삭제하는 API입니다.")
    public ResponseDTO<String> deleteComment(@PathVariable("comment_id") String commentId) {
        commentService.deleteComment(commentId);
        return ResponseDTO.res("댓글 삭제에 성공했습니다.");
    }
}