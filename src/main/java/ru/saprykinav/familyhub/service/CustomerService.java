package ru.saprykinav.familyhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.repository.CustomerRepository;
import ru.saprykinav.familyhub.repository.RoleRepository;


import java.util.Collections;
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

    public Customer findCustomerById(Long userId) {
        Optional<Customer> userFromDb = customerRepository.findById(userId);
        //сюда можно ебануть роль
        return userFromDb.orElse(new Customer());
    }

    public boolean saveCustomer(Customer customer) {
        Customer customerFromDB = customerRepository.findByUsername(customer.getUsername());

        if (customerFromDB != null) {
            return false;
        }
        //это нахуй не нужно по идее, надо проверить
        customer.setRoles(Collections.singleton(roleRepository.findById(1).get()));
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (customerRepository.findById(userId).isPresent()) {
            customerRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}

