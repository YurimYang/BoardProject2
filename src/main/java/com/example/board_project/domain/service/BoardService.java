package com.example.board_project.domain.service;

import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.dto.response.AllPostsResponse;
import com.example.board_project.domain.repository.BoardRepository;
import com.example.board_project.domain.util.BoardMapper;
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
    public List<AllPostsResponse> getAllPosts(){
        return BoardMapper.toAllPostListResponse(boardRepository.findAll());
    }

}
