package ru.saprykinav.familyhub.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.saprykinav.familyhub.entity.Customer;

import java.time.LocalDateTime;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(@AuthenticationPrincipal Customer customer, Model model) {
        model.addAttribute("date", LocalDateTime.now());
        return "index";
    }
}
