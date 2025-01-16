package com.example.board_project.domain.Board.service;

import com.example.board_project.domain.Board.dto.request.PostPatchRequest;
import com.example.board_project.domain.Board.dto.request.PostRequest;
import com.example.board_project.domain.Board.dto.response.PostResponse;
import com.example.board_project.domain.Board.enums.BoardSearchEnum;

import java.util.List;

public interface BoardService {
    PostResponse createBoard(PostRequest postRequest);
    List<PostResponse> getAllPosts();
    PostResponse getPost(String postId);
    List<PostResponse> getAllPostsByPagination(Integer page);
    String updatePost(String postId, PostPatchRequest postPatchRequest);
    void deletePost(String postId);
    List<PostResponse> searchPost(BoardSearchEnum type, String keyword);
    List<PostResponse> searchPostByPagination(BoardSearchEnum type, String keyword, int page);
}
