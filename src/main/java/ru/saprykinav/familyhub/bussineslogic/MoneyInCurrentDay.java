package ru.saprykinav.familyhub.bussineslogic;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class MoneyInCurrentDay {
    private LocalDate finalPoint;
    private BigDecimal currentMoney;

    public MoneyInCurrentDay(LocalDate finalPoint, BigDecimal currentMoney) {
        this.finalPoint = finalPoint;
        this.currentMoney = currentMoney;
    }

    public LocalDate getFinalPoint() {
        return finalPoint;
    }


    public void setFinalPoint(LocalDate finalPoint) {
        this.finalPoint = finalPoint;
    }

    public BigDecimal getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(BigDecimal currentMoney) {
        this.currentMoney = currentMoney;
    }

    public Optional<BigDecimal> getMoneyInCurrentDayByFinalDate() {
        BigDecimal moneyInCurrentDay = currentMoney.divide(BigDecimal.valueOf(Period.between(finalPoint, LocalDate.now()).getDays()));
        return Optional.ofNullable(moneyInCurrentDay);
    }
    public Optional<BigDecimal> getMoneySeveralDaysByFinalDate(int quantityDays){
        if(getMoneyInCurrentDayByFinalDate().isEmpty()) return Optional.empty();
        return Optional.ofNullable(getMoneyInCurrentDayByFinalDate().get().multiply(BigDecimal.valueOf(quantityDays)));
    }
}
