package org.example.apiservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test")
@RequestMapping("/front/v1.0.0/api/")
@RestController
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "Hello World <=======>";
    }
}
