package com.oficial.C1739.Service.imp;

import com.oficial.C1739.Entity.Comment;
import com.oficial.C1739.Service.CommentService;
import com.oficial.C1739.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public Comment saveComment(CommentDTO newComment) {
        return null;
    }

    @Override
    public Comment getCommentById(Long idComment) {
        return null;
    }

    @Override
    public List<Comment> getAllComments() {
        return null;
    }

    @Override
    public Comment updateComment(Long idComment, CommentDTO newComment) {
        return null;
    }

    @Override
    public Comment deleteComment(Long idComment) {
        return null;
    }
}
