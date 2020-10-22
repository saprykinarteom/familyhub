package ru.saprykinav.familyhub.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FamilyDTO {
    private Long id;
    private BigDecimal sumBuysAfterLastPayDay;
    private BigDecimal mandatorySpending;
    private LocalDate lastPayDay;

}
