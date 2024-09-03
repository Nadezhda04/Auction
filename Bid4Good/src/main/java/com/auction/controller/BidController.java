package com.auction.controller;

import com.auction.model.Bid;
import com.auction.model.User;
import com.auction.service.AuctionService;
import com.auction.model.Auction;
import com.auction.service.BidService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bid")
public class BidController {
    private final BidService bidService;
    private final AuctionService auctionService;

    public BidController(BidService bidService, AuctionService auctionService) {
        this.bidService = bidService;
        this.auctionService = auctionService;
    }

    @GetMapping("/{id}")
    public List<Bid> bidsOnAuction(@PathVariable Long id) {
        Auction auction = auctionService.retrieveAuction(id);
        return auction.getBids();
    }

    @GetMapping("/won")
    public List<Auction> retrieveWonAuctions(HttpSession session) {

        User user = (User)session.getAttribute("loggedUser");

        return bidService.retrieveWonAuctions(user);
    }

    @PostMapping("/create")
    public Boolean createBid(@RequestParam Long auctionId,
                            @RequestParam BigDecimal bidValue,
                            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");
        Auction auction = auctionService.retrieveAuction(auctionId);

        return bidService.createBid(bidValue, user, auction);
    }

    @GetMapping("/bidByUser/{id}")
    public String getBidUsername(@PathVariable Long id) {
        return bidService.getBidUsername(id);
    }
}
