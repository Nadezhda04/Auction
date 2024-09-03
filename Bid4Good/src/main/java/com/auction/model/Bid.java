package com.auction.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "bids")
public class Bid implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal bid;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction;

    private Boolean winner = false;

    public Bid(BigDecimal bid, User user, Auction auction) {
        this.bid = bid;
        this.user = user;
        this.auction = auction;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", bid=" + bid +
                ", user=" + user +
                ", auction=" + auction +
                ", winner=" + winner +
                '}';
    }
}
