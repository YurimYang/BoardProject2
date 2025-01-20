package com.example.board_project.domain.Board.service;

import com.example.board_project.domain.Board.dao.BoardDAO;
import com.example.board_project.domain.Board.dto.request.PostPatchRequest;
import com.example.board_project.domain.Board.dto.request.PostRequest;
import com.example.board_project.domain.Board.dto.response.PostResponse;
import com.example.board_project.domain.Board.entity.Board;
import com.example.board_project.domain.Board.enums.BoardSearchEnum;
import com.example.board_project.domain.Board.service.impl.BoardServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardServiceImpl boardService;

    @Mock
    private BoardDAO boardDAO;

    @Nested
    @DisplayName("createPost()는 ")
    class CreateBoardTest {

        @Test
        @DisplayName("게시글 작성에 성공했습니다.")
        void createPost_willSuccess() {
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
            PostResponse result = boardService.createPost(postRequest);

            //then
            assertThat(result).isNotNull();
            assertThat(result.title()).isEqualTo("title");
            assertThat(result.content()).isEqualTo("content");
            assertThat(result.writer()).isEqualTo("writer");
        }
    }

    @Nested
    @DisplayName("getAllPosts()는 ")
    class GetBoardTest {

        @Test
        @DisplayName("게시글 전체 조회에 성공했습니다.")
        void getAllPosts_willSuccess() {
            //given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();

            Board board1 = Board.builder()
                    .title("title1")
                    .content("content1")
                    .writer("writer1")
                    .build();

            PostRequest postRequest = PostRequest.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();

            PostResponse postResponse = PostResponse.builder()
                    .id(UUID.randomUUID().toString())
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .createdAt(LocalDateTime.now())
                    .build();

            PostResponse postResponse1 = PostResponse.builder()
                    .id(UUID.randomUUID().toString())
                    .title("title1")
                    .content("content1")
                    .writer("writer1")
                    .createdAt(LocalDateTime.now())
                    .build();

            List<Board> responseList = List.of(board, board1);

            given(boardDAO.getAllBoard()).willReturn(responseList);

            //when
            List<PostResponse> result = boardService.getAllPosts();

            //then
            assertThat(result).isNotNull();
            assertThat(result.get(0).title()).isEqualTo("title");
            assertThat(result.get(0).content()).isEqualTo("content");
            assertThat(result.get(0).writer()).isEqualTo("writer");
        }
    }

    @Nested
    @DisplayName("getAllPostsByPagination()는 ")
    class GetPagedBoardTest {

        @Test
        @DisplayName("게시글 전체 조회에 성공했습니다.")
        void getAllPostsByPagination_willSuccess() {
            //given
            int page = 1;
            Pageable pageable = PageRequest.of(page, 10);

            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();

            Board board1 = Board.builder()
                    .title("title1")
                    .content("content1")
                    .writer("writer1")
                    .build();


            List<Board> responseList = List.of(board, board1);

            Page<Board> boardPage = new PageImpl<>(responseList, pageable, 1);

            given(boardDAO.getAllPagedBoard(pageable)).willReturn(boardPage);

            //when
            List<PostResponse> result = boardService.getAllPostsByPagination(page);

            //then
            assertThat(result).isNotNull();
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0).title()).isEqualTo("title");
            assertThat(result.get(0).content()).isEqualTo("content");
            assertThat(result.get(0).writer()).isEqualTo("writer");
        }
    }

    @Nested
    @DisplayName("getPost()는 ")
    class GetPostTest {

        @Test
        @DisplayName("특정 게시글 조회에 성공했습니다.")
        void getPost_willSuccess() {
            //given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();
            String postId = board.getId();

            given(boardDAO.getBoardById(postId)).willReturn(Optional.of(board));

            //when
            PostResponse result = boardService.getPost(postId);

            //then
            assertThat(result).isNotNull();
            assertThat(result.title()).isEqualTo("title");
            assertThat(result.content()).isEqualTo("content");
            assertThat(result.writer()).isEqualTo("writer");
        }
    }

    @Nested
    @DisplayName("updatePost()는 ")
    class UpdatePostTest {

        @Test
        @DisplayName("특정 게시글을 업데이트 했습니다.")
        void updatePost_willSuccess() {
            // given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();
            String postId = board.getId();

            PostPatchRequest postPatchRequest = PostPatchRequest.builder()
                    .title("title11")
                    .content("content11")
                    .build();

            given(boardDAO.getBoardById(postId)).willReturn(Optional.of(board));
            doAnswer(invocation -> {
                Board boardArg = invocation.getArgument(0); // 첫 번째 인자로 Board 객체
                PostPatchRequest requestArg = invocation.getArgument(1); // 두 번째 인자로 PostPatchRequest 객체
                boardArg.updatePost(requestArg.title(), requestArg.content()); // updatePost 메서드 호출
                return null;
            }).when(boardDAO).updateBoard(board, postPatchRequest);

            // when
            PostResponse result = boardService.updatePost(postId, postPatchRequest);

            // then
            assertThat(result).isNotNull();
            assertThat(result.title()).isEqualTo("title11");
            assertThat(result.content()).isEqualTo("content11");
            assertThat(result.writer()).isEqualTo("writer");
        }
    }

    @Nested
    @DisplayName("deletePost()는 ")
    class deletePostTest {

        @Test
        @DisplayName("특정 게시글을 삭제했습니다.")
        void deletePost_willSuccess() {
            //given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();
            String postId = board.getId();

            given(boardDAO.getBoardById(postId)).willReturn(Optional.of(board));
            doNothing().when(boardDAO).deleteBoardById(board);

            //when
            boardService.deletePost(postId);

            //then
            verify(boardDAO, times(1)).deleteBoardById(board);

        }
    }

    @Nested
    @DisplayName("searchPost()는 ")
    class searchPostTest {

        @Test
        @DisplayName("특정 게시글 검색에 성공했습니다.")
        void searchPost_willSuccess() {
            //given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();

            Board board1 = Board.builder()
                    .title("title1")
                    .content("content1")
                    .writer("writer1")
                    .build();

            BoardSearchEnum type = BoardSearchEnum.TITLE;

            String keyword = "title";

            given(boardDAO.findBoardByKeyword(type, keyword)).willReturn(List.of(board, board1));

            //when
            List<PostResponse> result = boardService.searchPost(type, keyword);

            //then
            assertThat(result).isNotNull();
            assertThat(result.get(0).title()).isEqualTo("title");
        }
    }


    @Nested
    @DisplayName("searchPostByPagination()는 ")
    class searchPagedPostTest {

        @Test
        @DisplayName("특정 게시글 검색에 성공했습니다.")
        void searchPostByPagination_willSuccess() {
            //given
            int page = 1;
            Pageable pageable = PageRequest.of(page, 10);

            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();

            Board board1 = Board.builder()
                    .title("title1")
                    .content("content1")
                    .writer("writer1")
                    .build();

            BoardSearchEnum type = BoardSearchEnum.TITLE;

            String keyword = "title";

            List<Board> responseList = List.of(board, board1);

            Page<Board> boardPage = new PageImpl<>(responseList, pageable, 1);

            given(boardDAO.findPagedBoardByKeyword(type, keyword, pageable)).willReturn(boardPage);

            //when
            List<PostResponse> result = boardService.searchPostByPagination(type, keyword, page);

            //then
            assertThat(result).isNotNull();
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0).title()).isEqualTo("title");
        }
    }
}
