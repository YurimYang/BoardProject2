package com.example.board_project.domain.Board.service;

import com.example.board_project.domain.Board.dto.request.PostPatchRequest;
import com.example.board_project.domain.Board.dto.request.PostRequest;
import com.example.board_project.domain.Board.dto.response.PostResponse;
import com.example.board_project.domain.Board.enums.BoardSearchEnum;

import java.util.List;

public interface BoardService {
    PostResponse createPost(PostRequest postRequest);
    List<PostResponse> getAllPosts();
    List<PostResponse> getAllPostsByPagination(Integer page);
    PostResponse getPost(String postId);
    PostResponse updatePost(String postId, PostPatchRequest postPatchRequest);
    void deletePost(String postId);
    List<PostResponse> searchPost(BoardSearchEnum type, String keyword);
    List<PostResponse> searchPostByPagination(BoardSearchEnum type, String keyword, int page);
}
