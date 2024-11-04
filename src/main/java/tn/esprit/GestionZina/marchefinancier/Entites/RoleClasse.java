package tn.esprit.GestionZina.marchefinancier.Entites;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleClasse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idrole;
    String name;
    @OneToMany(mappedBy = "roles")
    @JsonIgnore
    List<User> userList;

}