package ru.saprykinav.familyhub.bussineslogic;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExtraMoneyAfterPaycheckTest {

    ExtraMoneyAfterPaycheck setUp() {
        ExtraMoneyAfterPaycheck test = new ExtraMoneyAfterPaycheck(LocalDate.of(2020, 03, 10), LocalDate.of(2020, 03, 01), BigDecimal.valueOf(10000), BigDecimal.valueOf(13000));
        return test;
    }

    @Test
    void getAverageConsumptionInDay() {
        ExtraMoneyAfterPaycheck test = setUp();
        BigDecimal expected = test.getAverageConsumptionInDay();
        BigDecimal actual = BigDecimal.valueOf(1000);

        assertEquals(expected, actual);
    }

    @Test
    void getExpectedExpenses() {
        
    }

    @Test
    void getExtraMoneyAfterPaycheck() {
    }
}