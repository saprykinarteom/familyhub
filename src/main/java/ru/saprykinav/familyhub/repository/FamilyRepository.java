package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.saprykinav.familyhub.entity.Family;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


public interface FamilyRepository extends JpaRepository<Family, Long> {
    @Query(value = "select sum(Buy.price) from Buy where (Buy.customer_id IN (select Customer.id from Customer where Customer.family_id = :family_id) and (Buy.date between :date_from and :date_to))", nativeQuery = true)
    Optional<BigDecimal> findSumPriceAllByFamilyByCustomerIdAndDateBetween (@Param("family_id") Long familyId, @Param("date_from") LocalDate dateFrom, @Param("date_to") LocalDate dateTo);


}
