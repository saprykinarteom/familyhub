package ru.saprykinav.familyhub.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.saprykinav.familyhub.entity.Customer;

@Controller
public class WelcomeController {
    @GetMapping("/welcome")
    public String welcome(@AuthenticationPrincipal Customer customer, Model model) {
        model.addAttribute("name", customer.getName());
        return "welcome";
    }
}
