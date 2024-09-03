package com.auction.service.impl;

import com.auction.model.Auction;
import com.auction.model.Bid;
import com.auction.model.User;
import com.auction.repository.BidRepository;
import com.auction.service.BidService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {
    BidRepository bidRepository;

    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public Boolean createBid(BigDecimal value, User user, Auction auction) {

        List<Bid> bidsOnAuction = auction.getBids();

//        If there are no bids on the auction, the user's bid must be equal or higher than the initial bid,
//        subsequent bids must be higher than the previous one.

        if(bidsOnAuction.isEmpty() && (value.compareTo(auction.getInitialBid()) >= 0) ||
                value.compareTo(bidsOnAuction.get(bidsOnAuction.size() - 1).getBid()) > 0) {
            Bid bid = new Bid(value, user, auction);
            bidRepository.saveAndFlush(bid);
            return true;
        }

        return false;
    }

    @Override
    public List<Auction> retrieveWonAuctions(User user) {
        List<Bid> wonAuctions = user.getBids().stream().filter(Bid::getWinner).toList();
        return wonAuctions.stream().map(Bid::getAuction).toList();
    }

    @Override
    public String getBidUsername(Long id) {
        Bid bid = bidRepository.findById(id).get();
        return bid.getUser().getUsername();
    }
}
