package ru.saprykinav.familyhub.bussineslogic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class ExtraMoneyAfterPaycheck {
    private LocalDate finalPoint;
    private LocalDate lastPoint;
    private BigDecimal currentMoney;
    private BigDecimal lastPointMoney;

    public ExtraMoneyAfterPaycheck(LocalDate finalPoint, LocalDate lastPoint, BigDecimal currentMoney, BigDecimal lastPointMoney) {
        this.finalPoint = finalPoint;
        this.lastPoint = lastPoint;
        this.currentMoney = currentMoney;
        this.lastPointMoney = lastPointMoney;
    }

    public LocalDate getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(LocalDate finalPoint) {
        this.finalPoint = finalPoint;
    }

    public LocalDate getLastPoint() {
        return lastPoint;
    }

    public void setLastPoint(LocalDate lastPoint) {
        this.lastPoint = lastPoint;
    }

    public BigDecimal getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(BigDecimal currentMoney) {
        this.currentMoney = currentMoney;
    }

    public BigDecimal getLastPointMoney() {
        return lastPointMoney;
    }

    public void setLastPointMoney(BigDecimal lastPointMoney) {
        this.lastPointMoney = lastPointMoney;
    }

    public BigDecimal getAverageConsumptionInDay() {
        BigDecimal AverageConsumptionInDay = ((currentMoney.subtract(lastPointMoney)).divide(BigDecimal.valueOf(Period.between(LocalDate.now(), lastPoint).getDays())));
        return AverageConsumptionInDay;
    }

    public BigDecimal getExpectedExpenses() {
        BigDecimal expectedExpenses = getAverageConsumptionInDay().multiply(BigDecimal.valueOf(Period.between(finalPoint, LocalDate.now()).getDays()).add(BigDecimal.valueOf(1)));
        return expectedExpenses;
    }

    public BigDecimal getExtraMoneyAfterPaycheck() {
        BigDecimal extraMoneyAfterPaycheck = currentMoney.subtract(getExpectedExpenses());
        return extraMoneyAfterPaycheck;
    }

}
