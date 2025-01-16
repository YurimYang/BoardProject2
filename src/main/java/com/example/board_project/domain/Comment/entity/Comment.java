package com.example.board_project.domain.Comment.entity;

import com.example.board_project.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "comment")
public class Comment extends BaseEntity {

    @Id
    private String id;
    private String postId;
    private String content;
    private String writer;

    @Builder
    public Comment(String postId,String content, String writer) {
        this.id = UUID.randomUUID().toString();
        this.postId = postId;
        this.content = content;
        this.writer = writer;
    }
}
