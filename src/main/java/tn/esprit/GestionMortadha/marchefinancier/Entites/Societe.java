package tn.esprit.GestionMortadha.marchefinancier.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Societe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idSociete;
    String name;
    int nbrTitre;
    double capitalBoursier;
    long numeroTel;
    String siegeSocial;
    String siteWeb;
    String objetSocial;
    String backtestingName;
    @OneToOne
    @JsonIgnore
    Titre titre;
    @OneToMany(mappedBy="societe")
    @JsonIgnore
    List<Actualite> actualitéList= new ArrayList<>();
}
