package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.saprykinav.familyhub.entity.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
