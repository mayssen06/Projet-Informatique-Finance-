package tn.esprit.GestionZina.marchefinancier.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    int idUser;
    int credit;
    double demandeSolde;
    String firstName;
    String lasttName;
    String password;
    String verifPassword;
    long phone;
    @Column(unique = true)
    String email;
    double solde;
    boolean accountStatus;
    double highestsold;
    double lowestsold;
    String profession;
    String passwordResetToken;
    boolean Active;
    @Enumerated(EnumType.STRING)
    Role role;

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @ManyToOne
    RoleClasse roles;
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

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
