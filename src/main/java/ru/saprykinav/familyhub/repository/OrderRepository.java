package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saprykinav.familyhub.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
