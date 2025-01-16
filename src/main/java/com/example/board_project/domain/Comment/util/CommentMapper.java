package com.example.board_project.domain.Comment.util;

import com.example.board_project.domain.Comment.dto.request.CommentRequest;
import com.example.board_project.domain.Comment.dto.response.CommentResponse;
import com.example.board_project.domain.Comment.entity.Comment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentMapper {

    public static Comment toComment(CommentRequest commentRequest){
        return Comment.builder()
                .postId(commentRequest.postId())
                .content(commentRequest.content())
                .writer(commentRequest.writer())
                .build();
    }

    public static List<CommentResponse> toCommentListResponse(List<Comment> commentList){
        List<CommentResponse> commentResponse = new ArrayList<>();
        for(Comment comment : commentList){
            commentResponse.add(toCommentResponse(comment));
        }
        return commentResponse;
    }

    public static CommentResponse toCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .content(comment.getContent())
                .writer(comment.getWriter())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}