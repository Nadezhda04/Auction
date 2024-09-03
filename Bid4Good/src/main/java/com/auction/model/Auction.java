package com.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "auctions")
public class Auction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 32, nullable = false)
    private String title;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal initialBid;
    private String description;
    private String category;
    private String picture;
    private Boolean active = true;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    @JsonIgnore
    private List<Bid> bids;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    @JsonIgnore
    private List<Comment> comments;

    public Auction(String title, BigDecimal initialBid, String description, String category, String picture, User user) {
        this.title = title;
        this.initialBid = initialBid;
        this.description = description;
        this.category = category;
        this.picture = picture;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", initial_bid=" + initialBid +
                '}';
    }
}
