package com.arekproject.studiaprojekt.controller;

import ch.qos.logback.core.model.Model;
import com.arekproject.studiaprojekt.entity.UserDtls;
import com.arekproject.studiaprojekt.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Autowired
    private UserRepository userRepo;
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, Model m, HttpSession session)
    {

        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        UserDtls u=userRepo.save(user);
        if(u!=null){
            session.setAttribute("msg", "Register Succesfully");
        }else{
            session.setAttribute("msg", "Something wrong on server");
        }
        return "redirect:/signup";
    }
}
