package com.example.board_project.domain.events;

import com.example.board_project.domain.entity.Board;
import com.example.board_project.domain.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BoardModelListener extends AbstractMongoEventListener<Board> {
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Board> event) {
        event.getSource().setId(sequenceGeneratorService.generateSequence(Board.SEQUENCE_NAME));
    }
}
