package com.example.board_project.domain.Board.repository.impl;

import com.example.board_project.domain.Board.dto.request.PostPatchRequest;
import com.example.board_project.domain.Board.entity.Board;
import com.example.board_project.domain.Board.enums.BoardSearchEnum;
import com.example.board_project.domain.Board.repository.CustomBoardRepository;
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

@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateBoardById(Board board, PostPatchRequest postPatchRequest) {
        Query query = new Query(Criteria.where("id").is(board.getId()).and("deletedAt").is(null));
        Update update = new Update()
                .set("title", postPatchRequest.title())
                .set("content", postPatchRequest.content())
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Board.class);
    }

    @Override
    public void deleteBoardById(Board board) {
        if(!board.isDeleted()){
            Query query = new Query(Criteria.where("id").is(board.getId()));
            Update update = new Update().set("deletedAt", LocalDateTime.now());
            mongoTemplate.updateFirst(query, update, Board.class);
        }
    }

    @Override
    public List<Board> findBoardByTypeAndKeyword(BoardSearchEnum type, String keyword) {
        Query query = new Query(buildKeywordCriteria(type, keyword).and("deletedAt").is(null));
        return mongoTemplate.find(query, Board.class);
    }

    @Override
    public Page<Board> findPagedBoardByKeyword(BoardSearchEnum type, String keyword, Pageable pageable) {
        Query query = new Query(buildKeywordCriteria(type, keyword)
                .and("deletedAt")
                .is(null)).with(pageable);
        List<Board> boardList = mongoTemplate.find(query, Board.class);
        long count = mongoTemplate.count(query, Board.class);
        return new PageImpl<>(boardList, pageable, count);
    }

    private Criteria buildKeywordCriteria(BoardSearchEnum type, String keyword){
        return switch (type) {
            case TITLE -> Criteria.where("title").regex(keyword);
            case CONTENT -> Criteria.where("content").regex(keyword);
            case WRITER -> Criteria.where("writer").regex(keyword);
        };
    }
}