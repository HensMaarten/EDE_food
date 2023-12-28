package fact.it.utensilsservice.controller;

import fact.it.utensilsservice.dto.UtensilRequest;
import fact.it.utensilsservice.dto.UtensilResponse;
import fact.it.utensilsservice.model.Utensil;
import fact.it.utensilsservice.service.UtensilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public void createUtensil
            (@RequestBody UtensilRequest utensilRequest) {
        utensilsService.createUtensil(utensilRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Utensil updateUtensil
            (@PathVariable Long id,@RequestBody UtensilRequest utensilRequest) {
        utensilsService.editUtensil(id,utensilRequest);
        return utensilsService.getUtensilById(id);
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
