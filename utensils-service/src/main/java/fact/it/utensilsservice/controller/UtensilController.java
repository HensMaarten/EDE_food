package fact.it.utensilsservice.controller;

import fact.it.utensilsservice.dto.UtensilRequest;
import fact.it.utensilsservice.dto.UtensilResponse;
import fact.it.utensilsservice.model.Utensil;
import fact.it.utensilsservice.service.UtensilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utensil")
@RequiredArgsConstructor
public class UtensilController {

    private final UtensilsService utensilsService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUtensil(@PathVariable Long id){
        utensilsService.deleteUtensil(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Utensil> createUtensil
            (@RequestBody UtensilRequest utensilRequest) {
        Utensil savedUtensil = utensilsService.createUtensil(utensilRequest);
        return ResponseEntity.ok(savedUtensil);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Utensil> updateUtensil
            (@PathVariable Long id,@RequestBody UtensilRequest utensilRequest) {
        Utensil utensil = utensilsService.editUtensil(id,utensilRequest);
        return ResponseEntity.ok(utensil);
    }

    @GetMapping("/getUtensils")
    @ResponseStatus(HttpStatus.OK)
    public List<UtensilResponse> getUtensils
    (@RequestParam List<Long> id) {
        return utensilsService.getUtensilsById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UtensilResponse> getAllIngredients() {
        return utensilsService.getAllUtensils();
    }

}
