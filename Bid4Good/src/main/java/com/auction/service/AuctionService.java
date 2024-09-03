package com.auction.service;

import com.auction.model.User;
import com.auction.model.Auction;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionService {
    List<Auction> getAllAuctions();

    Auction createAuction(String title, BigDecimal initialBid,
                          String description, String category,
                          String pictureUrl, User user);

    Auction retrieveAuction(Long id);

    Auction closeAuction(Long id);

    List<Auction> getCategoryAuctions(String category);
}
