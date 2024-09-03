package com.auction.service;

import com.auction.model.User;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;

public interface UserService {
    User saveUser(String firstName, String lastName,
                  String username, String email,
                  String password, String confirmPassword);

    User login(String email, String password, HttpSession session);

    BigDecimal getUserBalance(User user);
}
