package com.auction.service.impl;

import com.auction.model.Comment;
import com.auction.model.Auction;
import com.auction.repository.CommentRepository;
import com.auction.model.User;
import com.auction.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> retrieveComments(Long id) {
        return commentRepository.findAllByAuctionId(id);
    }

    @Override
    public ResponseEntity<String> getCommentUser(Long id) {
        Comment comment = commentRepository.findById(id).get();
        return new ResponseEntity<>(comment.getUser().getUsername(), HttpStatus.OK);
    }

    @Override
    public Comment createComment(String comment, User user, Auction auction) {

        Comment newComment = new Comment(comment, user, auction);

        return commentRepository.saveAndFlush(newComment);
    }
}
