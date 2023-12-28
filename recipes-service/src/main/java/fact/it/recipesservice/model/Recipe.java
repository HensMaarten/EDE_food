package fact.it.recipesservice.model;

import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String instructions;

    private String recipeUrl;

    private String imageUrl;
    @ElementCollection
    private List<String> ingredients;

    @ElementCollection
    private List<Long> utensils;
}
