package tn.esprit.GestionZina.marchefinancier.Entites;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Titre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idTitre;
    @Enumerated(EnumType.STRING)
    TypeTitre typeTitre;
    double prix;
    int dureeValidite;
    double ouverture;
    double nominal;
    double dernier;
    double haut;
    double bas;

    @OneToOne(mappedBy = "titre")
    Societe societe;


}
