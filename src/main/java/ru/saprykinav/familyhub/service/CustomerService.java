package ru.saprykinav.familyhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.entity.Role;
import ru.saprykinav.familyhub.repository.CustomerRepository;
import ru.saprykinav.familyhub.repository.RoleRepository;


import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return customer;
    }

    public Customer findCustomerById(Long userId) throws NoSuchElementException {
        Optional<Customer> userFromDb = customerRepository.findById(userId);
        if (userFromDb.isEmpty()) {
            throw new NoSuchElementException("User not found") ;
        }
        return userFromDb.get();
    }
//добавление и обновление пользователя
    public boolean saveCustomer(Customer customer) {
        if (customer.getId() == null) {
        Customer customerFromDB = customerRepository.findByUsername(customer.getUsername());
        if (customerFromDB != null) {
            return false;
        }
        customer.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        } customerRepository.save(customer);
        return true;
    }

    public boolean deleteUser(Long userId) {
        customerRepository.deleteById(userId);
        return true;
    }
}

