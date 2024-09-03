package com.auction.service;

import com.auction.model.Auction;
import com.auction.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface BidService {
    Boolean createBid(BigDecimal value, User user, Auction auction);

    List<Auction> retrieveWonAuctions(User user);

    String getBidUsername(Long id);
}
