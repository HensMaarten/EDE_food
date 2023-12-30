package fact.it.utensilsservice;

import fact.it.utensilsservice.dto.UtensilRequest;
import fact.it.utensilsservice.dto.UtensilResponse;
import fact.it.utensilsservice.model.Utensil;
import fact.it.utensilsservice.repository.UtensilsRepository;
import fact.it.utensilsservice.service.UtensilsService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UtensilsServiceUnitTest {

    @InjectMocks
    private UtensilsService utensilService;

    @Mock
    private UtensilsRepository utensilsRepository;

    @Test
    public void testGetAllUtensils() {
        Utensil utensil = new Utensil();
        utensil.setId(1L);
        utensil.setRequiresElectricity(true);
        utensil.setName("Mixer");

        // Mock the repository response
        Mockito.when(utensilsRepository.findAll()).thenReturn(List.of(utensil));

        // Call the service method
        List<UtensilResponse> utensils = utensilService.getAllUtensils();

        // Verify the result
        assertEquals(1, utensils.size());
        assertEquals(1L, utensils.get(0).getId());
        assertTrue(utensils.get(0).isRequiresElectricity());
        assertEquals("Mixer",utensils.get(0).getName());

        Mockito.verify(utensilsRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetUtensilsById() {
        Utensil utensil = new Utensil();
        utensil.setId(1L);
        utensil.setRequiresElectricity(true);
        utensil.setName("Mixer");

        Utensil secondUtensil = new Utensil();
        secondUtensil.setId(2L);
        secondUtensil.setRequiresElectricity(false);
        secondUtensil.setName("Houten lepel");

        List<Long> ids = Arrays.asList(1L, 2L);

        // Mock the repository response
        Mockito.when(utensilsRepository.findByIdIn(ids)).thenReturn(Arrays.asList(utensil,secondUtensil));

        // Call the service method
        List<UtensilResponse> utensils = utensilService.getUtensilsById(ids);

        // Verify the result
        assertEquals(2, utensils.size());

        assertEquals(1L, utensils.get(0).getId());
        assertTrue(utensils.get(0).isRequiresElectricity());
        assertEquals("Mixer",utensils.get(0).getName());

        assertEquals(2L, utensils.get(1).getId());
        assertFalse(utensils.get(1).isRequiresElectricity());
        assertEquals("Houten lepel",utensils.get(1).getName());

        Mockito.verify(utensilsRepository, Mockito.times(1)).findByIdIn(ids);
    }

    @Test
    public void testGetUtensilById() {
        Long utensilId = 1L;
        Utensil utensil = new Utensil();
        utensil.setId(utensilId);
        utensil.setRequiresElectricity(true);
        utensil.setName("Mixer");

        // Mock the repository response
        Mockito.when(utensilsRepository.findById(utensilId)).thenReturn(Optional.of(utensil));

        // Call the service method
        Utensil foundUtensil = utensilService.getUtensilById(utensilId);

        // Verify the result
        assertEquals(1L, foundUtensil.getId());
        assertTrue(foundUtensil.isRequiresElectricity());
        assertEquals("Mixer",foundUtensil.getName());

        Mockito.verify(utensilsRepository, Mockito.times(1)).findById(utensilId);
    }

    @Test
    public void testCreateUtensil() {
        UtensilRequest utensilRequest = new UtensilRequest("Knife", false);

        // Call the service method
        utensilService.createUtensil(utensilRequest);

        // Verify that save method is called once on the repository
        Mockito.verify(utensilsRepository, Mockito.times(1)).save(Mockito.any(Utensil.class));
    }

    @Test
    public void testEditUtensil() {
        Long utensilId = 1L;
        UtensilRequest utensilRequest = new UtensilRequest("Lepel", false);

        Utensil utensilToEdit = new Utensil();
        Mockito.when(utensilsRepository.findById(utensilId)).thenReturn(Optional.of(utensilToEdit));

        // Create ArgumentCaptor to capture the argument passed to save method
        ArgumentCaptor<Utensil> utensilCaptor = ArgumentCaptor.forClass(Utensil.class);

        // Call the service method
        utensilService.editUtensil(utensilId, utensilRequest);

        // Verify that save method is called once on the repository with the captured argument
        Mockito.verify(utensilsRepository, Mockito.times(1)).save(utensilCaptor.capture());

        // Retrieve the captured argument
        Utensil editedUtensil = utensilCaptor.getValue();

        // Assert that the data in the editedUtensil matches the data in the utensilRequest
        assertEquals(utensilRequest.getName(), editedUtensil.getName());
        assertEquals(utensilRequest.isRequiresElectricity(), editedUtensil.isRequiresElectricity());
    }

    @Test
    public void testDeleteUtensil() {
        Long utensilId = 1L;

        // Call the service method
        utensilService.deleteUtensil(utensilId);

        // Verify that deleteById method is called once on the repository
        Mockito.verify(utensilsRepository, Mockito.times(1)).deleteById(utensilId);
    }

    @Test
    public void testGetUtensilByIdNotFound() {
        Long utensilId = 1L;
        // Mock the repository response
        Mockito.when(utensilsRepository.findById(utensilId)).thenReturn(Optional.empty());

        // Verify that the service method throws a ResponseStatusException
        assertThrows(ResponseStatusException.class, () -> utensilService.getUtensilById(utensilId));
    }

}
