package com.example.board_project.domain.service;

import com.example.board_project.domain.dto.request.PostPatchRequest;
import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.dto.response.PostResponse;
import com.example.board_project.domain.entity.Board;
import com.example.board_project.domain.exception.PageNotFoundException;
import com.example.board_project.domain.exception.PostNotFoundException;
import com.example.board_project.domain.repository.BoardRepository;
import com.example.board_project.domain.util.BoardMapper;
import com.example.board_project.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public String createBoard(PostRequest postRequest) {
        return boardRepository.save(BoardMapper.toBoard(postRequest)).getId();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return BoardMapper.toAllPostListResponse(boardRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPostsByPagination(Integer page){
        Page<Board> boardPage;
        Pageable pageable = PageRequest.of(page, 10);
        boardPage = boardRepository.findAll(pageable);

        if(boardPage.isEmpty()){
            throw new PageNotFoundException(ErrorCode.PAGE_NOT_FOUND);
        }

        return BoardMapper.toAllPostListResponse(boardPage.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(String postId) {
        Board searchedBoard = boardRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        if(searchedBoard.isDeleted()){
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }
        return BoardMapper.toPostResponse(searchedBoard);

    }

    @Transactional
    public String updatePost(String postId, PostPatchRequest postPatchRequest) {
        Board searchedBoard = boardRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        if(searchedBoard.isDeleted()){
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }
        searchedBoard.updatePost(postPatchRequest.title(), postPatchRequest.content());
        boardRepository.save(searchedBoard);
        return searchedBoard.getId();
    }

    @Transactional
    public void deletePost(String postId) {
        Board searchedBoard = boardRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        searchedBoard.delete(LocalDateTime.now());
        boardRepository.save(searchedBoard);
    }

}
