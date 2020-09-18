package ru.saprykinav.familyhub.bot;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.saprykinav.familyhub.bot.service.BotBuyService;
import ru.saprykinav.familyhub.bot.service.BotFamilyService;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.entity.Family;
import ru.saprykinav.familyhub.service.CustomerService;
import ru.saprykinav.familyhub.service.FamilyService;

import java.math.BigDecimal;

@Service
public class BotService {

    private final CustomerService customerService;
    private final FamilyService familyService;
    private final BotFamilyService botFamilyService;
    private final BotBuyService botBuyService;

    public BotService(CustomerService customerService, FamilyService familyService, BotFamilyService botFamilyService, BotBuyService botBuyService){
        this.customerService = customerService;
        this.familyService = familyService;
        this.botFamilyService = botFamilyService;
        this.botBuyService = botBuyService;
    }

    public Customer authorization(Message inMessage) {
        Customer customerFromDb = customerService.loadCustomerByTgUsername(inMessage.getChat().getUserName()).orElseThrow(RuntimeException::new);
        return customerFromDb;
    }
    public String getFamilyInfo(Customer customer) throws NotFoundException {
        Family family = familyService.loadByFamilyId(customer.getFamily().getId());
        String text = "Состав семьи:\n" + family.getCustomers().toString() + "\n" + "Обязательные траты: " + family.getMandatorySpending().toString() + "\n" + "Траты за текущий месяц: " + family.getSumBuysAfterLastPayDay().toString();
        return text;
    }
    public String getPayInfo(Customer customer) throws NotFoundException {
        try {
            Family family = familyService.loadByFamilyId(customer.getFamily().getId());

            Customer familyMemberOne = family.getCustomers().get(0);
            Customer familyMemberTwo = family.getCustomers().get(1);

            BigDecimal sumBuysByMemberOne = botBuyService.getSumBuysByCustomerAfterLastPayDay(familyMemberOne);
            BigDecimal sumBuysByMemberTwo = botBuyService.getSumBuysByCustomerAfterLastPayDay(familyMemberTwo);

            BigDecimal temp1 = sumBuysByMemberOne.divide(BigDecimal.valueOf(2));
            BigDecimal temp2 = sumBuysByMemberTwo.divide(BigDecimal.valueOf(2));
            BigDecimal temp3 = customer.getFamily().getMandatorySpending().divide(BigDecimal.valueOf(2));

            BigDecimal debtMemberOne = temp3.subtract(temp1).subtract(temp2);
            BigDecimal debtMemberTwo = temp3.subtract(debtMemberOne);

            String text = "Нужно заплатить " + customer.getFamily().getMandatorySpending()
                    + "\nпокупки за месяц : \n"
                    + familyMemberOne.getName() + " -- " + sumBuysByMemberOne + "\n"
                    + familyMemberTwo.getName() + " -- " + sumBuysByMemberTwo + "\n"
                    + "платит за квартиру : \n"
                    + familyMemberOne.getName() + " -- " + debtMemberOne + "\n"
                    + familyMemberTwo.getName() + " -- " + debtMemberTwo + "\n";
            return text;
        }
        catch (NotFoundException e){
            e.printStackTrace();
            return "Family not found";
        }
    }

}
