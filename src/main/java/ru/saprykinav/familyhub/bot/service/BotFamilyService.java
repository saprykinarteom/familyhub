package ru.saprykinav.familyhub.bot.service;

import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.service.FamilyService;

import java.time.LocalDate;

@Service
public class BotFamilyService {
    private final FamilyService familyService;

    public BotFamilyService(FamilyService familyService) {
        this.familyService = familyService;
    }

    public String setLastPayDay(Long familyId, LocalDate lastPayDay){
        familyService.setLastPayDay(familyId, lastPayDay);
        return "День последнего платежа " + lastPayDay.getDayOfMonth();
    }
}
