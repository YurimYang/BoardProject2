package com.example.board_project.domain.Comment.service;

import com.example.board_project.domain.Board.dao.BoardDAO;
import com.example.board_project.domain.Board.entity.Board;
import com.example.board_project.domain.Comment.dao.CommentDAO;
import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.dto.request.CommentRequest;
import com.example.board_project.domain.Comment.dto.response.CommentResponse;
import com.example.board_project.domain.Comment.entity.Comment;
import com.example.board_project.domain.Comment.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentDAO commentDAO;

    @Mock
    private BoardDAO boardDAO;

    @Nested
    @DisplayName("CreateCommentTest()는 ")
    class CreateCommentTest {

        @Test
        @DisplayName("댓글 작성에 성공했습니다.")
        void createPost_willSuccess() {
            //given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();
            String postId = board.getId();

            Comment comment = Comment.builder()
                    .postId(postId)
                    .content("content")
                    .writer("writer")
                    .build();

            CommentRequest commentRequest = CommentRequest.builder()
                    .postId(postId)
                    .content("content")
                    .writer("writer")
                    .build();

            given(boardDAO.getBoardById(postId)).willReturn(Optional.of(board));
            given(commentDAO.insertComment(any(Comment.class))).willReturn(comment);


            //when
            CommentResponse result = commentService.createComment(commentRequest);

            //then
            assertThat(result).isNotNull();
            assertThat(result.postId()).isEqualTo(postId);
            assertThat(result.content()).isEqualTo("content");
            assertThat(result.writer()).isEqualTo("writer");
        }
    }

    @Nested
    @DisplayName("GetAllCommentsTest()는 ")
    class GetAllCommentsTest{

        @Test
        @DisplayName("해당 게시글의 댓글 조회에 성공했습니다.")
        void getAllComments_willSuccess(){
            //given

            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();
            String postId = board.getId();

            Comment comment = Comment.builder()
                    .postId(postId)
                    .content("content1")
                    .writer("writer1")
                    .build();

            Comment comment1 = Comment.builder()
                    .postId(postId)
                    .content("content2")
                    .writer("writer2")
                    .build();

            List<Comment> commentList = List.of(comment, comment1);

            given(boardDAO.getBoardById(postId)).willReturn(Optional.of(board));
            given(commentDAO.selectCommentsByPostId(any(String.class))).willReturn(commentList);

            //when
            List<CommentResponse> result = commentService.getAllComments(postId);

            //then
            assertThat(result).isNotNull();
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0).content()).isNotEqualTo(result.get(1).content());
        }
    }

    @Nested
    @DisplayName("UpdateCommentTest()는 ")
    class UpdateCommentTest{

        @Test
        @DisplayName("댓글 수정에 성공했습니다.")
        void updateComment_willSuccess(){
            //given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();
            String postId = board.getId();

            Comment comment = Comment.builder()
                    .postId(postId)
                    .content("content")
                    .writer("writer")
                    .build();
            String commentId = comment.getId();

            CommentPatchRequest commentPatchRequest = CommentPatchRequest.builder()
                    .content("update content")
                    .build();

            given(commentDAO.selectCommentById(commentId)).willReturn(Optional.of(comment));

            doAnswer(invocation -> {
                Comment commentArg = invocation.getArgument(0); // 첫 번째 인자로 Board 객체
                CommentPatchRequest requestArg = invocation.getArgument(1); // 두 번째 인자로 PostPatchRequest 객체
                commentArg.updateComment(requestArg.content()); // updatePost 메서드 호출
                return null;
            }).when(commentDAO).updateComment(comment, commentPatchRequest);

            //when
            CommentResponse result = commentService.updateComment(commentId, commentPatchRequest);

            //then
            assertThat(result).isNotNull();
            assertThat(result.postId()).isEqualTo(postId);
            assertThat(result.content()).isEqualTo("update content");
            assertThat(result.writer()).isEqualTo("writer");
        }

    }

    @Nested
    @DisplayName("DeleteCommentTest()는 ")
    class DeleteCommentTest{

        @Test
        @DisplayName("댓글 삭제에 성공했습니다.")
        void deleteComment_willSuccess() {
            //given
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .build();
            String postId = board.getId();

            Comment comment = Comment.builder()
                    .postId(postId)
                    .content("content")
                    .writer("writer")
                    .build();
            String commentId = comment.getId();

            given(commentDAO.selectCommentById(commentId)).willReturn(Optional.of(comment));
            doNothing().when(commentDAO).deleteComment(comment);

            //when
            commentService.deleteComment(commentId);

            //then
            verify(commentDAO, times(1)).deleteComment(comment);
        }
    }

}
