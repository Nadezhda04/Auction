package com.auction.controller;

import com.auction.model.User;
import com.auction.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public User register(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "confirmPassword") String confirmPassword
    ) {
        return userService.saveUser(firstName, lastName, username, email, password, confirmPassword);
    }

    @PostMapping("/login")
    public Boolean login(@RequestParam(value = "email") String email,
                      @RequestParam(value = "password") String password,
                      HttpSession session) {

        User user = userService.login(email, password, session);

        return user != null;
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Boolean> logout(HttpSession session){

        User user = (User)session.getAttribute("loggedUser");

        if(user != null) {
            session.invalidate();
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(false, HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping("/requestUser")
    public ResponseEntity<String> requestUser(HttpSession session){

        User user = (User)session.getAttribute("loggedUser");

        if(user != null) {
            return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/isAuthenticated")
    public ResponseEntity<Boolean> kickMeOut(){
        Authentication au =
                SecurityContextHolder.getContext().getAuthentication();

        if( au != null && au.isAuthenticated() &&
                !(au instanceof AnonymousAuthenticationToken)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/getBalance")
    public BigDecimal getUserBalance(HttpSession session) {

        User user = (User)session.getAttribute("loggedUser");

        return userService.getUserBalance(user);
    }
}
