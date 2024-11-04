package tn.esprit.GestionZina.marchefinancier.Entites;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mail {
    String from;
    String to;
    String subject;
    String content;
}
