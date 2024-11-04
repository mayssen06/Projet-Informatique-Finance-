package tn.esprit.GestionMortadha.marchefinancier.Entites;

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
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idTransaction;
    @Temporal(TemporalType.DATE)
    Date date;
    @ManyToOne
    Titre titre;
    @ManyToOne
    User acheteur;
    @ManyToOne
    User vendeur;
}
