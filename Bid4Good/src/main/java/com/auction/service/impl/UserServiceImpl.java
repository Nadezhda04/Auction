package com.auction.service.impl;

import com.auction.model.User;
import com.auction.repository.UserRepository;
import com.auction.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    public UserDetailsServiceImpl userDetailsService;
    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDetailsServiceImpl userDetailsService,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User saveUser(String firstName, String lastName,
                         String username, String email,
                         String password, String confirmPassword) {

        if(username.isBlank() || email.isBlank() ||
                password.isBlank() || !password.equals(confirmPassword)) {
            return null;
        }

        User user = new User(firstName, lastName, username, email, passwordEncoder.encode(password));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User login(String email, String password, HttpSession session) {
        User user = userRepository.findByEmail(email);

        if(user != null && passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("loggedUser", user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if(userDetails != null) {
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            }
        }
        return user;
    }

    @Override
    public BigDecimal getUserBalance(User user) {

        // user.getBalance() returns wrong values, so I get the data from the DB.

        return userRepository.findById(user.getId()).get().getBalance();
    }
}
