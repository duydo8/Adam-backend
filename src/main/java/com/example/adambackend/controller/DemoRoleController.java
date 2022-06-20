package com.example.adambackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class DemoRoleController {
    @GetMapping("/admin/demo")
    public String admin() {
        return "Welcome to admin";
    }

    @GetMapping("/user")
    public String user() {
        return "Welcome to user";
    }

    @GetMapping("/auth")
    public String auth() {
        return "dont have any role";
    }
}
