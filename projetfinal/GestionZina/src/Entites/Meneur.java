package tn.esprit.GestionZina.marchefinancier.Entites;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Meneur extends User {
    public void setImage(String uniqueFilename) {
    }
}
