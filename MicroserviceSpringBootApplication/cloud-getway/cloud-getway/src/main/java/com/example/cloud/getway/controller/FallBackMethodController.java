package com.example.cloud.getway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {


    @GetMapping("/userServiceFallBack")
    public String userServiceFallBackMethod(){

        return "UserService taking longer than expected"+" please try again later ";
    }
    @GetMapping("/departmentServiceFallBack")
    public String departmentServiceFallBackMethod(){

        return "DepartmentService taking longer than expected"+" please try again later ";
    }
}