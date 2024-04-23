package com.oficial.C1739.Service;


import com.oficial.C1739.Entity.Comment;
import com.oficial.C1739.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    Comment saveComment(CommentDTO newComment);

    Comment getCommentById(Long idComment);

    List<Comment> getAllComments();

    Comment updateComment(Long idComment, CommentDTO newComment);
    Comment deleteComment(Long idComment);
}
