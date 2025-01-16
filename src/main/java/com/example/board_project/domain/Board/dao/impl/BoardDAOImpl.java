package com.example.board_project.domain.Board.dao.impl;

import com.example.board_project.domain.Board.dao.BoardDAO;
import com.example.board_project.domain.Board.dto.request.PostPatchRequest;
import com.example.board_project.domain.Board.entity.Board;
import com.example.board_project.domain.Board.enums.BoardSearchEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardDAOImpl implements BoardDAO {

    private final MongoTemplate mongoTemplate;

    @Override
    public Board insertBoard(Board board) {
        return mongoTemplate.save(board);
    }

    @Override
    public List<Board> selectAllBoard() {
        Query query = new Query(Criteria.where("deletedAt").is(null));
        return mongoTemplate.find(query, Board.class);
    }

    @Override
    public Page<Board> selectAllBoardPage(Pageable pageable) {
        Query query = new Query(Criteria.where("deletedAt").is(null)).with(pageable);
        List<Board> boardList = mongoTemplate.find(query, Board.class);
        long count = mongoTemplate.count(query, Board.class);
        return new PageImpl<>(boardList, pageable, count);
    }

    @Override
    public Optional<Board> selectBoardById(String id) {
        Board board = mongoTemplate.findById(id, Board.class);
        return Optional.ofNullable(board);
    }

    @Override
    public void updateBoard(String postId, PostPatchRequest postPatchRequest) {
        Query query = new Query(Criteria.where("id").is(postId));
        Update update = new Update();
        update.set("title", postPatchRequest.title());
        update.set("content", postPatchRequest.content());
        update.set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Board.class);
    }

    @Override
    public void deleteBoardById(String postId, Board board) {
        Query query = new Query(Criteria.where("id").is(postId));

        if(!board.isDeleted()){
            Update update = new Update();
            update.set("deletedAt", LocalDateTime.now());

            mongoTemplate.updateFirst(query, update, Board.class);
        }
    }

    @Override
    public List<Board> findByKeyword(BoardSearchEnum type, String keyword) {
        List<Board> boardList = switch (type) {
            case TITLE -> {
                Query query = new Query(Criteria.where("title").regex(keyword));
                yield mongoTemplate.find(query, Board.class);
            }
            case CONTENT -> {
                Query query = new Query(Criteria.where("content").regex(keyword));
                yield mongoTemplate.find(query, Board.class);
            }
            case WRITER -> {
                Query query = new Query(Criteria.where("writer").regex(keyword));
                yield mongoTemplate.find(query, Board.class);
            }
        };
        return boardList;
    }

    @Override
    public Page<Board> findByKeywordWithPage(BoardSearchEnum type, String keyword, Pageable pageable) {
        List<Board> boardList = switch (type) {
            case TITLE -> {
                Query query = new Query(Criteria.where("title").regex(keyword)
                                                .and("deletedAt").is(null)).with(pageable);
                yield mongoTemplate.find(query, Board.class);
            }
            case CONTENT -> {
                Query query = new Query(Criteria.where("content").regex(keyword)
                        .and("deletedAt").is(null)).with(pageable);
                yield mongoTemplate.find(query, Board.class);
            }
            case WRITER -> {
                Query query = new Query(Criteria.where("writer").regex(keyword)
                        .and("deletedAt").is(null)).with(pageable);
                yield mongoTemplate.find(query, Board.class);
            }
        };
        return new PageImpl<>(boardList, pageable, boardList.size());
    }
}
