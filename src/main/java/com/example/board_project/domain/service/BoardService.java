package com.example.board_project.domain.service;

import com.example.board_project.domain.dto.request.PostPatchRequest;
import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.dto.response.PostResponse;
import com.example.board_project.domain.enums.BoardSearchEnum;

import java.util.List;

public interface BoardService {
    String createBoard(PostRequest postRequest);
    List<PostResponse> getAllPosts();
    PostResponse getPost(String postId);
    List<PostResponse> getAllPostsByPagination(Integer page);
    String updatePost(String postId, PostPatchRequest postPatchRequest);
    void deletePost(String postId);
    List<PostResponse> searchPost(BoardSearchEnum type, String keyword);
}
