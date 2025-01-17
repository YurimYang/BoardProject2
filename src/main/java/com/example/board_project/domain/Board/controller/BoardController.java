package com.example.board_project.domain.Board.controller;

import com.example.board_project.domain.Board.dto.request.PostPatchRequest;
import com.example.board_project.domain.Board.dto.request.PostRequest;
import com.example.board_project.domain.Board.dto.response.PostResponse;
import com.example.board_project.domain.Board.enums.BoardSearchEnum;
import com.example.board_project.domain.Board.service.BoardService;
import com.example.board_project.global.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/posts")
@Tag(name = "BOARD API", description = "온보딩 프로젝트 - 게시판 API")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
    })
    @Operation(summary = "게시글 등록 API", description = "글을 게시판에 등록하는 API입니다.")
    public ResponseDTO<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest) {
        return ResponseDTO.res(boardService.createPost(postRequest), "게시판에 글 등록을 성공했습니다.");
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
    })
    @Operation(summary = "게시글 전체 조회 API", description = "게시판의 글을 전체 조회하는 API입니다.")
    public ResponseDTO<List<PostResponse>> getAllPosts(@RequestParam(required = false) Integer page) {
        if (page == null) {
            return ResponseDTO.res(boardService.getAllPosts(), "게시판 전체 조회에 성공했습니다.");
        }
        return ResponseDTO.res(boardService.getAllPostsByPagination(page), "게시판 전체 조회에 성공했습니다.");
    }

    @GetMapping("/{post_id}")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
    })
    @Operation(summary = "특정 게시글 조회 API", description = "게시판의 특정 글을 조회하는 API입니다.")
    public ResponseDTO<PostResponse> getPost(@PathVariable("post_id") String postId) {
        return ResponseDTO.res(boardService.getPost(postId), "게시글 조회에 성공했습니다.");
    }

    @PatchMapping("/{post_id}")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
    })
    @Operation(summary = "특정 게시글 수정 API", description = "게시판의 특정 글을 수정하는 API입니다.")
    public ResponseDTO<PostResponse> updatePost(@PathVariable("post_id") String postId,
                                                @Valid @RequestBody PostPatchRequest postPatchRequest) {
        return ResponseDTO.res(boardService.updatePost(postId, postPatchRequest), "게시글 수정에 성공했습니다.");
    }

    @DeleteMapping("/{post_id}")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    })
    @Operation(summary = "특정 게시글 삭제 API", description = "게시판의 특정 글을 삭제하는 API입니다.")
    public ResponseDTO<String> deletePost(@PathVariable("post_id") String postId) {
        boardService.deletePost(postId);
        return ResponseDTO.res("게시글 삭제에 성공했습니다.");
    }

    @GetMapping("/search")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))
    })
    @Operation(summary = "게시글 검색 API", description = "게시판의 게시글을 검색하는 API입니다.")
    public ResponseDTO<List<PostResponse>> searchPost(
            @RequestParam BoardSearchEnum type,
            @RequestParam @NotBlank String keyword,
            @RequestParam(required = false) Integer page) {

        if (page == null) {
            return ResponseDTO.res(boardService.searchPost(type, keyword), "게시글 검색에 성공했습니다.");
        }
        return ResponseDTO.res(boardService.searchPostByPagination(type, keyword, page), "게시글 검색에 성공했습니다.");
    }
}