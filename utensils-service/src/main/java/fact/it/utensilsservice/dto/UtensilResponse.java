package fact.it.utensilsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UtensilResponse {

    private Long id;
    private String name;
    private boolean requiresElectricity;

}
