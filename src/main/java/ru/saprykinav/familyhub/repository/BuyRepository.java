package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.saprykinav.familyhub.entity.Buy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BuyRepository extends JpaRepository<Buy, Long> {
    List<Buy> findAllByCustomerId (Long customerId);
    List<Buy> findAllByCustomerIdAndDateBetween (Long customerId, LocalDate dateFrom, LocalDate dateTo);

    @Query(value = "select sum(Buy.price) from Buy where (Buy.customer_id = :customer_id and (Buy.date between :date_from and :date_to))", nativeQuery = true)
    Optional<BigDecimal> findSumPriceAllByCustomerIdAndDateBetween (@Param("customer_id") Long customerId, @Param("date_from") LocalDate dateFrom,@Param("date_to") LocalDate dateTo);
}
