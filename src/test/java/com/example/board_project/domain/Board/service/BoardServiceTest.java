package com.example.board_project.domain.Board.service;

import com.example.board_project.domain.Board.dao.BoardDAO;
import com.example.board_project.domain.Board.dto.request.PostRequest;
import com.example.board_project.domain.Board.dto.response.PostResponse;
import com.example.board_project.domain.Board.entity.Board;
import com.example.board_project.domain.Board.service.impl.BoardServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Transactional
@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardServiceImpl boardService;

    @Mock
    private BoardDAO boardDAO;

    @Nested
    @DisplayName("createBaord()는 ")
    class CreateBoardTest {

        @Test
        @DisplayName("게시글 작성에 성공했습니다.")
        void createBoard_willSuccess() {
            //given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();

            PostRequest postRequest = PostRequest.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();

            given(boardDAO.insertBoard(any(Board.class))).willReturn(board);

            //when
            PostResponse result = boardService.createBoard(postRequest);


            //then
            assertThat(result).isNotNull();
            assertThat(result.title()).isEqualTo("title");
            assertThat(result.content()).isEqualTo("content");
            assertThat(result.writer()).isEqualTo("writer");
        }
    }

    @Nested
    @DisplayName("createBaord()는 ")
    class GetBoardTest {

        @Test
        @DisplayName("게시글 작성에 성공했습니다.")
        void createBoard_willSuccess() {
            //given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();

            PostRequest postRequest = PostRequest.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();

            given(boardDAO.insertBoard(any(Board.class))).willReturn(board);

            //when
            PostResponse result = boardService.createBoard(postRequest);


            //then
            assertThat(result).isNotNull();
            assertThat(result.title()).isEqualTo("title");
            assertThat(result.content()).isEqualTo("content");
            assertThat(result.writer()).isEqualTo("writer");
        }
    }

}
