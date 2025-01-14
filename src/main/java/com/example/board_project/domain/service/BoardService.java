package com.example.board_project.domain.service;

import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.dto.response.PostResponse;
import com.example.board_project.domain.entity.Board;
import com.example.board_project.domain.exception.PostNotFoundException;
import com.example.board_project.domain.repository.BoardRepository;
import com.example.board_project.domain.util.BoardMapper;
import com.example.board_project.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long createBoard(PostRequest postRequest) {
        return boardRepository.save(BoardMapper.toBoard(postRequest)).getId();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return BoardMapper.toAllPostListResponse(boardRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Board searchedBoard = boardRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        return BoardMapper.toPostResponse(searchedBoard);
    }
}
