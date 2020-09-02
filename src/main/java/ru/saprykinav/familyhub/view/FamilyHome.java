package ru.saprykinav.familyhub.view;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.saprykinav.familyhub.entity.Family;

@Getter
@Setter
@EqualsAndHashCode
public class FamilyHome {
    private Family family;

    public FamilyHome(Family family) {
        this.family = family;
    }

}
