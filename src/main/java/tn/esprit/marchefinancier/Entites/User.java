package tn.esprit.marchefinancier.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transaction;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.management.relation.Role;
//import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idUser;
    String firstName;
    String lasttName;
    String password;
    String verifPassword;
    long phone;
    @Column(unique = true)
    String email;
    double solde;

    double highestsold;
    double lowestsold;
    String profession;
    boolean Active;
   /* @ManyToOne
    Role roles;
    @OneToOne(cascade = CascadeType.ALL)
            @JsonIgnore
    Portefeuille portefeuille;
    @OneToMany(mappedBy = "acheteur")
    @JsonIgnore
    List<Transaction> transactionListA;
    @OneToMany(mappedBy = "vendeur")
    @JsonIgnore
    List<Transaction> transactionListV;
    @OneToMany(mappedBy = "users")
    @JsonIgnore
    List<Ordre> ordreList;
    @ElementCollection
    @CollectionTable(name = "user_double_map", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "map_date")
    @Column(name = "map_value")
    private Map<LocalDate, Double> doubleMap = new HashMap<>();
    int credit;
    double demandeSolde;*/
}
