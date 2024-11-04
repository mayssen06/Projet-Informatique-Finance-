package tn.esprit.GestionZina.marchefinancier.Entites;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Actualite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idActualite;
    String titre;
    String description;
    @ManyToOne
   Societe societe;
    @Column
    private String filename;
}
