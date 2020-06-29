package ru.saprykinav.familyhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.repository.RoleRepository;
import ru.saprykinav.familyhub.service.CustomerService;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(String name, String username, String password, String passwordConfirm, Model model) {
        if (!password.equals(passwordConfirm)){
            model.addAttribute("PasswordNotConfirm", "Password not confirm");
            return "registration";}
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setName(name);

        if(!customerService.saveCustomer(customer)){
            model.addAttribute("UserWithThatNameEnable", "User With That Name Enable");
            return "registration";
        };

        return "redirect:/login";
    }
}

