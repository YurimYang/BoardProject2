package com.example.board_project.domain.entity;

import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

@Getter
@Service
@Document(collection = "database_sequences")
/**
 * AUTO INCREMENT 연속성 정보를 저장하는 컬렉션
 */
public class DatabaseSequence {

    @Id
    private String id;
    private Long seq;
}
