package com.auction.service.impl;

import com.auction.model.Auction;
import com.auction.model.Bid;
import com.auction.model.User;
import com.auction.repository.AuctionRepository;
import com.auction.repository.UserRepository;
import com.auction.service.AuctionService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository, UserRepository userRepository) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAllByActive(true);
    }

    @Override
    public Auction createAuction(String title,
                                 BigDecimal initialBid,
                                 String description,
                                 String category,
                                 String pictureUrl,
                                 User user) {

        if(pictureUrl.isEmpty()) {
            pictureUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png";
        }

        if(category.isEmpty()) {
            category = "Other";
        }

        Auction auction = new Auction(title, initialBid, description, category, pictureUrl, user);

        return auctionRepository.saveAndFlush(auction);
    }

    public Auction retrieveAuction(Long id) {

        return auctionRepository.findById(id).get();
    }

    @Override
    public Auction closeAuction(Long id) {
        // When a user closes an auction:
        // - The highest bidder wins the auction and the user who posted the auctions gets the money.
        // - The winner can view the auction in their "Won Auctions" page and the seller can view the profits in the
        // "Balance" page.
        // - If there is no bid when the auction gets closed, the auction gets deleted.
        Auction auction = auctionRepository.findById(id).get();

        if(!auction.getBids().isEmpty()) {
            Bid highestBid = auction.getBids().get(auction.getBids().size() - 1);
            highestBid.setWinner(true);

            User user = auction.getUser();
            user.setBalance(user.getBalance().add(highestBid.getBid()));
            userRepository.saveAndFlush(user);

        } else {
            auctionRepository.delete(auction);
            return null;
        }

        auction.setActive(false);

        return auctionRepository.saveAndFlush(auction);
    }

    @Override
    public List<Auction> getCategoryAuctions(String category) {
        return auctionRepository.findAllByCategoryAndActive(category, true);
    }

}
