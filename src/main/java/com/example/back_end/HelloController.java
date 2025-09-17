package com.example.back_end;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/api/users")
    public List<String> getUsers() {
        return List.of("Alice", "Bob", "Charlie");
    }
}
