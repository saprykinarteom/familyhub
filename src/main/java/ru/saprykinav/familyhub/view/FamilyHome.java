package ru.saprykinav.familyhub.view;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.entity.Family;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class FamilyHome {
    private List<Customer> familyMembers;
    private Family family;

}
