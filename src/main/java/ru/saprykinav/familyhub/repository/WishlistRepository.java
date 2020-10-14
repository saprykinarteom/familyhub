package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.saprykinav.familyhub.entity.Wishlist;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByOwnerId(Long ownerId);
}
