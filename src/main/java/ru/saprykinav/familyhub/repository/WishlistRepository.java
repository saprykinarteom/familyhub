package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.saprykinav.familyhub.entity.Wishlist;
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
