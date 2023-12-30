package fact.it.ingredientsservice;

import fact.it.ingredientsservice.dto.IngredientRequest;
import fact.it.ingredientsservice.dto.IngredientResponse;
import fact.it.ingredientsservice.model.Ingredient;
import fact.it.ingredientsservice.repository.IngredientRepository;
import fact.it.ingredientsservice.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class IngredientsServiceApplicationTests {

	@InjectMocks
	private IngredientService ingredientService;

	@Mock
	private IngredientRepository ingredientRepository;

	@Test
	public void testGetAllIngredients() {
		Ingredient ingredient = new Ingredient();
		ingredient.setId("1");
		ingredient.setName("kipfilet");
		ingredient.setMeasure("gram");
		ingredient.setAmount(500);
		// Mock the repository response

		Mockito.when(ingredientRepository.findAll()).thenReturn(List.of(ingredient));

		// Call the service method
		List<IngredientResponse> ingredients = ingredientService.getAllIngredients();

		// Verify the result
		assertEquals(1, ingredients.size());
		assertEquals("1", ingredients.get(0).getId());
		assertEquals("kipfilet",ingredients.get(0).getName());
		assertEquals("gram",ingredients.get(0).getMeasure());
		assertEquals(500,ingredients.get(0).getAmount());

		Mockito.verify(ingredientRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void testGetAllIngredientsById() {
		Ingredient ingredient = new Ingredient();
		ingredient.setId("1");
		ingredient.setName("kipfilet");
		ingredient.setMeasure("gram");
		ingredient.setAmount(500);

		Ingredient secondIngredient = new Ingredient();
		ingredient.setId("2");
		ingredient.setName("paprikas");
		ingredient.setMeasure("stuks");
		ingredient.setAmount(2);

		List<String> ids = Arrays.asList("1", "2");
		// Mock the repository response
		Mockito.when(ingredientRepository.findByIdIn(ids)).thenReturn(Arrays.asList(ingredient, secondIngredient));

		// Call the service method
		List<IngredientResponse> ingredients = ingredientService.getAllIngredientsById(ids);

		// Verify the result
		assertEquals(2, ingredients.size());

		assertEquals("1", ingredients.get(0).getId());
		assertEquals("kipfilet",ingredients.get(0).getName());
		assertEquals("gram",ingredients.get(0).getMeasure());
		assertEquals(500,ingredients.get(0).getAmount());

		assertEquals("2", ingredients.get(1).getId());
		assertEquals("paprikas",ingredients.get(1).getName());
		assertEquals("stuks",ingredients.get(1).getMeasure());
		assertEquals(2,ingredients.get(1).getAmount());

		Mockito.verify(ingredientRepository, Mockito.times(1)).findByIdIn(ids);

	}
	@Test
	public void testGetIngredientById() {
		String ingredientId = "1";
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ingredientId);
		ingredient.setName("kipfilet");
		ingredient.setMeasure("gram");
		ingredient.setAmount(500);
		// Mock the repository response
		Mockito.when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

		// Call the service method
		Ingredient foundIngredient = ingredientService.getIngredientById(ingredientId);

		// Verify the result
		assertEquals(ingredientId, foundIngredient.getId());
		assertEquals("kipfilet",foundIngredient.getName());
		assertEquals("gram",foundIngredient.getMeasure());
		assertEquals(500,foundIngredient.getAmount());

		Mockito.verify(ingredientRepository, Mockito.times(1)).findById(ingredientId);
	}

	@Test
	public void testCreateIngredient() {
		IngredientRequest ingredientRequest = new IngredientRequest("Suiker", "kg",2 );

		// Call the service method
		ingredientService.createIngredient(ingredientRequest);

		// Verify that save method is called once on the repository
		Mockito.verify(ingredientRepository, Mockito.times(1)).save(Mockito.any(Ingredient.class));
	}

	@Test
	public void testEditIngredient() {
		String ingredientId = "1";
		IngredientRequest ingredientRequest = new IngredientRequest("Bloem", "g", 500);

		// Mock the repository response
		Ingredient ingredientToEdit = new Ingredient();
		Mockito.when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredientToEdit));

		// Create ArgumentCaptor to capture the argument passed to save method
		ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);

		// Call the service method
		ingredientService.editIngredient(ingredientId, ingredientRequest);

		// Verify that save method is called once on the repository with the captured argument
		Mockito.verify(ingredientRepository, Mockito.times(1)).save(ingredientCaptor.capture());

		// Retrieve the captured argument
		Ingredient editedIngredient = ingredientCaptor.getValue();

		// Assert that the data in the editedUtensil matches the data in the utensilRequest
		assertEquals(ingredientRequest.getName(), editedIngredient.getName());
		assertEquals(ingredientRequest.getMeasure(), editedIngredient.getMeasure());
		assertEquals(ingredientRequest.getAmount(), editedIngredient.getAmount());
	}

	@Test
	public void testDeleteIngredient() {
		String ingredientId = "1";

		// Call the service method
		ingredientService.deleteIngredient(ingredientId);

		// Verify that deleteById method is called once on the repository
		Mockito.verify(ingredientRepository, Mockito.times(1)).deleteById(ingredientId);
	}

	@Test
	public void testGetIngredientByIdNotFound() {
		String ingredientId = "1";
		// Mock the repository response
		Mockito.when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

		// Verify that the service method throws a ResponseStatusException
		assertThrows(ResponseStatusException.class, () -> ingredientService.getIngredientById(ingredientId));
	}

}
