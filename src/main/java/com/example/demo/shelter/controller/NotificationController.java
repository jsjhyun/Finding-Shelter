package com.example.demo.shelter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationController {

    @GetMapping("/notification")
    public String showNotification(){
        return "notification";
    }
}
