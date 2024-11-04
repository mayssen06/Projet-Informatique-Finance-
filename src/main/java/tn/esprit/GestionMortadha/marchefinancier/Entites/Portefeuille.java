package tn.esprit.GestionMortadha.marchefinancier.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Portefeuille implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idPortefeuille;
    @Temporal(TemporalType.DATE)
    Date date;
    double lowestsold ;
    double highestsold;
    @ManyToMany(fetch = FetchType.EAGER)
    List<Titre> titreListP= new ArrayList<>();
    @OneToOne(mappedBy = "portefeuille")
    User user;

}
