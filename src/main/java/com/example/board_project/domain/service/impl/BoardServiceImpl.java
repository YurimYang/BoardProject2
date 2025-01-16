package com.example.board_project.domain.service.impl;

import com.example.board_project.domain.dao.BoardDAO;
import com.example.board_project.domain.dto.request.PostPatchRequest;
import com.example.board_project.domain.dto.request.PostRequest;
import com.example.board_project.domain.dto.response.PostResponse;
import com.example.board_project.domain.entity.Board;
import com.example.board_project.domain.enums.BoardSearchEnum;
import com.example.board_project.domain.exception.PageNotFoundException;
import com.example.board_project.domain.exception.PostNotFoundException;
import com.example.board_project.domain.service.BoardService;
import com.example.board_project.domain.util.BoardMapper;
import com.example.board_project.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardDAO boardDAO;
    private final static int PAGE_LIMIT = 10;

    @Override
    @Transactional
    public String createBoard(PostRequest postRequest) {
        return boardDAO.insertBoard(BoardMapper.toBoard(postRequest)).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return BoardMapper.toAllPostListResponse(boardDAO.selectAllBoard());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPost(String postId) {
        Board searchedBoard = findActivePostById(postId);
        return BoardMapper.toPostResponse(searchedBoard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPostsByPagination(Integer page){
        Pageable pageable = PageRequest.of(page, PAGE_LIMIT);
        Page<Board> boardPage = boardDAO.selectAllBoardPage(pageable);
        validatePage(boardPage);
        return BoardMapper.toAllPostListResponse(boardPage.toList());
    }

    private void validatePage(Page<Board> boardPage) {
        if(boardPage.isEmpty()){
            throw new PageNotFoundException(ErrorCode.PAGE_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public String updatePost(String postId, PostPatchRequest postPatchRequest) {
        Board searchedBoard = findActivePostById(postId);
        boardDAO.updateBoard(postId,postPatchRequest);
        return searchedBoard.getId();
    }

    @Override
    @Transactional
    public void deletePost(String postId) {
        Board searchedBoard = findActivePostById(postId);
        boardDAO.deleteBoardById(postId, searchedBoard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> searchPost(BoardSearchEnum type, String keyword) {
        List<Board> searchedBoards = boardDAO.findByKeyword(type, keyword);
        if(searchedBoards.isEmpty()){
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }
        return BoardMapper.toAllPostListResponse(searchedBoards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> searchPostByPagination(BoardSearchEnum type, String keyword, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_LIMIT);
        Page<Board> boardPage = boardDAO.findByKeywordWithPage(type, keyword, pageable);
        validatePage(boardPage);
        return BoardMapper.toAllPostListResponse(boardPage.toList());
    }

    private Board findActivePostById(String postId) {
        return boardDAO.selectBoardById(postId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
    }
}