package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login?logout=true";
    }



    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "secure";
    }
    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "/register";

    }
    @PostMapping("/register")
    public String processregister(@ModelAttribute("user")User user,Model model){
        model.addAttribute("user",user);
        model.addAttribute("message","New User account Created");
        user.setEnabled(true);
        userRepository.save(user);
        Role role=new Role(user.getUsername(), "ROLE_USER");
        roleRepository.save(role);
        return "index";


    }



}
