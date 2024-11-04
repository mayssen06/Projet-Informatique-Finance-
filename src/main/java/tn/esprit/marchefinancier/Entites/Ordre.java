package tn.esprit.marchefinancier.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ordre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idOrdre;
    boolean status;
    @Temporal(TemporalType.DATE)
    Date dateEmission;
    @Temporal(TemporalType.DATE)
    Date datePeremption;
    int nbreOrdre;
    float limitPrice;
    double prixTitre;
    double prixPassage;
    @Enumerated(EnumType.STRING)
    TypeOrdre typeOrdre;

    @ManyToOne
    Titre titre;
    @ManyToOne
    User users;
    //@ManyToOne
   /* @JsonIgnore
    Carnet carnet;*/
}
