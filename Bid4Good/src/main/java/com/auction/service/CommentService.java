package com.auction.service;

import com.auction.model.Comment;
import com.auction.model.User;
import com.auction.model.Auction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    List<Comment> retrieveComments(Long id);

    ResponseEntity<String> getCommentUser(Long id);

    Comment createComment(String comment, User user, Auction auction);
}
