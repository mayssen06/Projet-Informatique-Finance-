package tn.esprit.marchefinancier.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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
