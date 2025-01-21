package com.example.board_project.domain.Board.service.impl;

import com.example.board_project.domain.Board.dto.request.PostPatchRequest;
import com.example.board_project.domain.Board.dto.request.PostRequest;
import com.example.board_project.domain.Board.dto.response.PostResponse;
import com.example.board_project.domain.Board.entity.Board;
import com.example.board_project.domain.Board.enums.BoardSearchEnum;
import com.example.board_project.domain.Board.exception.PageNotFoundException;
import com.example.board_project.domain.Board.exception.PostNotFoundException;
import com.example.board_project.domain.Board.repository.BoardRepository;
import com.example.board_project.domain.Board.service.BoardService;
import com.example.board_project.domain.Board.util.BoardMapper;
import com.example.board_project.domain.Comment.entity.Comment;
import com.example.board_project.domain.Comment.repository.CommentRepository;
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

    //private final BoardDAO boardDAO;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final static int PAGE_LIMIT = 10;

    @Override
    @Transactional
    public PostResponse createPost(PostRequest postRequest) {
        Board board = BoardMapper.toBoard(postRequest);
        //Board savedBoard = boardDAO.insertBoard(board);
        Board savedBoard = boardRepository.save(board);
        return BoardMapper.toPostResponse(savedBoard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        //return BoardMapper.toAllPostListResponse(boardDAO.getAllBoard());
        return BoardMapper.toAllPostListResponse(boardRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPostsByPagination(Integer page){
        Page<Board> boardPage = getPagedBoards(page);
        return BoardMapper.toAllPostListResponse(boardPage.toList());
    }

    private Page<Board> getPagedBoards(int page) {
        validatePage(page);
        Pageable pageable = PageRequest.of(page, PAGE_LIMIT);
        //Page<Board> boardPage = boardDAO.getAllPagedBoard(pageable);
        Page<Board> boardPage = boardRepository.findAll(pageable);
        validatePageNotEmpty(boardPage);
        return boardPage;
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPost(String postId) {
        return BoardMapper.toPostResponse(findValidPost(postId));
    }

    @Override
    @Transactional
    public PostResponse updatePost(String postId, PostPatchRequest postPatchRequest) {
        Board searchedBoard = findValidPost(postId);
        //boardDAO.updateBoard(searchedBoard, postPatchRequest);
        boardRepository.updateBoardById(searchedBoard, postPatchRequest);
        return BoardMapper.toPostResponse(findValidPost(postId));
    }

    @Override
    @Transactional
    public void deletePost(String postId) {
        Board searchedBoard = findValidPost(postId);
        //boardDAO.deleteBoardById(searchedBoard);
        boardRepository.deleteBoardById(searchedBoard);
        List<Comment> commentList = commentRepository.getCommentsByPostId(postId);
        commentRepository.deleteComments(commentList);
    }

    private Board findValidPost(String postId) {
//        return boardDAO.getBoardById(postId)
//                .filter(p -> !p.isDeleted())
//                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        return boardRepository.findById(postId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> searchPost(BoardSearchEnum type, String keyword) {
        //List<Board> searchedBoards = boardDAO.findBoardByKeyword(type, keyword);
        List<Board> searchedBoards = boardRepository.findBoardByTypeAndKeyword(type, keyword);
        if(searchedBoards.isEmpty()){
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }
        return BoardMapper.toAllPostListResponse(searchedBoards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> searchPostByPagination(BoardSearchEnum type, String keyword, int page) {
        Page<Board> boardPage = getPagedBoards(type, keyword, page);
        return BoardMapper.toAllPostListResponse(boardPage.toList());
    }

    private Page<Board> getPagedBoards(BoardSearchEnum type, String keyword, int page) {
        validatePage(page);
        Pageable pageable = PageRequest.of(page, PAGE_LIMIT);
//        Page<Board> boardPage = boardDAO.findPagedBoardByKeyword(type, keyword, pageable);
        Page<Board> boardPage = boardRepository.findPagedBoardByKeyword(type, keyword, pageable);
        validatePageNotEmpty(boardPage);
        return boardPage;
    }

    private void validatePage(int page){
        if(page < 0){
            throw new PageNotFoundException(ErrorCode.PAGE_NOT_FOUND);
        }
    }

    private void validatePageNotEmpty(Page<Board> boardPage) {
        if(boardPage.isEmpty()){
            throw new PageNotFoundException(ErrorCode.PAGE_NOT_FOUND);
        }
    }
}