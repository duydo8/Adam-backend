package com.example.adambackend.controller.website;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DemoWebsiteController {
    @GetMapping("/user")
    public String user() {
        return "User Board.";
    }
    @GetMapping("/auth")
    public String auth(){
        return "dont have any role";
    }

}
