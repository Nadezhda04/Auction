package com.auction.controller;

import com.auction.model.User;
import com.auction.model.Auction;
import com.auction.service.AuctionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/all")
    public List<Auction> retrieveAllAuctions() {
        return auctionService.getAllAuctions();
    }

    @GetMapping("/category/{category}")
    public List<Auction> retrieveCategoryAuctions(@PathVariable String category) {
        return auctionService.getCategoryAuctions(category);
    }

    @PostMapping("/create")
    public Auction createAuction(@RequestParam String title,
                                 @RequestParam BigDecimal initialBid,
                                 @RequestParam String description,
                                 @RequestParam String category,
                                 @RequestParam String pictureUrl,
                                 HttpSession session) {


        User user = (User) session.getAttribute("loggedUser");

        return auctionService.createAuction(title, initialBid, description, category, pictureUrl, user);
    }

    @GetMapping("/{id}")
    public Auction detailsAuction(@PathVariable Long id) {
        return auctionService.retrieveAuction(id);
    }

    @PostMapping("/close/{id}")
    public Auction closeAuction(@PathVariable Long id) {
        return auctionService.closeAuction(id);
    }
}
