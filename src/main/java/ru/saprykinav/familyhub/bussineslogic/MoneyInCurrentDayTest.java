package ru.saprykinav.familyhub.bussineslogic;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MoneyInCurrentDayTest {

    MoneyInCurrentDay setUp() {
        MoneyInCurrentDay test = new MoneyInCurrentDay(LocalDate.of(2020, 2, 23), BigDecimal.valueOf(3000));
        return test;
    }

    @Test
    void getMoneyInCurrentDay() {
        MoneyInCurrentDay test = setUp();
        BigDecimal expected = test.getMoneyInCurrentDayByFinalDate().get() ;
        BigDecimal actual = new BigDecimal(1000);

        assertEquals(expected, actual);
    }

    @Test
    void getMoneySeveralDaysByFinalDate() {
        MoneyInCurrentDay test = setUp();
        BigDecimal expected = test.getMoneySeveralDaysByFinalDate(3).get();
        BigDecimal actual = new BigDecimal(3000);
    }
}