package fact.it.utensilsservice.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "utensil")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Utensil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean requiresElectricity;

}
