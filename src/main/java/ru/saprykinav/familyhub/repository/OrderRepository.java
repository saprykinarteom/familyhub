package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saprykinav.familyhub.entity.Item;

public interface OrderRepository extends JpaRepository<Item, Long> {

}
