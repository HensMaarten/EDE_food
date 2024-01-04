package fact.it.utensilsservice.service;

import fact.it.utensilsservice.dto.UtensilRequest;
import fact.it.utensilsservice.dto.UtensilResponse;
import fact.it.utensilsservice.model.Utensil;
import fact.it.utensilsservice.repository.UtensilsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtensilsService {

    private final UtensilsRepository utensilsRepository;

    @PostConstruct
    public void loadData() {
        if(utensilsRepository.count() <= 0){
            Utensil utensil = new Utensil();
            utensil.setName("oven");
            utensil.setRequiresElectricity(true);

            Utensil utensil1 = new Utensil();
            utensil1.setName("spatel");
            utensil1.setRequiresElectricity(false);

            utensilsRepository.save(utensil);
            utensilsRepository.save(utensil1);
        }

    }

    public List<UtensilResponse> getAllUtensils() {
        List<Utensil> utensils = utensilsRepository.findAll();

        return utensils.stream().map(this::mapToUtensilResponse).toList();
    }


    public List<UtensilResponse> getUtensilsById(List<Long> id) {
        List<Utensil> utensils = utensilsRepository.findByIdIn(id);

        return utensils.stream().map(this::mapToUtensilResponse).toList();
    }

    public Utensil getUtensilById(Long id) {
        Optional<Utensil> currentOptionalUtensil = utensilsRepository.findById(id);
        return currentOptionalUtensil.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensil with " + id + " not found"));
    }

    public Utensil createUtensil(UtensilRequest utensilRequest){
        Utensil utensil = Utensil.builder()
                .name(utensilRequest.getName())
                .requiresElectricity(utensilRequest.isRequiresElectricity())
                .build();

        Utensil savedUtensil = utensilsRepository.save(utensil);
        return  savedUtensil;
    }

    public Utensil editUtensil(Long utensilId, UtensilRequest utensilRequest){
        Utensil currentUtensil = getUtensilById(utensilId);

        currentUtensil.setName(utensilRequest.getName());
        currentUtensil.setRequiresElectricity(utensilRequest.isRequiresElectricity());

        Utensil savedUtensil = utensilsRepository.save(currentUtensil);
        return  savedUtensil;
    }
    public void deleteUtensil(Long utensilId){
        utensilsRepository.deleteById(utensilId);
    }

    private UtensilResponse mapToUtensilResponse(Utensil utensil) {
        return UtensilResponse.builder()
                .id(utensil.getId())
                .name(utensil.getName())
                .requiresElectricity(utensil.isRequiresElectricity())
                .build();
    }

}
