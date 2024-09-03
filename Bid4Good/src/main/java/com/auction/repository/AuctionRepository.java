package com.auction.repository;

import com.auction.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findAllByActive(Boolean active);
    List<Auction> findAllByCategoryAndActive(String category, Boolean active);
}
