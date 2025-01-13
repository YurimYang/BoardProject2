package com.example.board_project.domain.entity;

import com.example.board_project.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "board")
public class Board extends BaseEntity {

    @Transient
    public static final String SEQUENCE_NAME = "board_sequence";
    //자동 증가 시퀀스에 대한 참조를 하는 SEQUENCE_NAME을 static 필드로 선언하고, @Transient를 사용해서 영속 필드에서 제외

    @Id
    private Long id;
    private String title;
    private String content;
    private String writer;


    public void setId(Long id) {
        this.id = id;
    }

    @Builder
    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
