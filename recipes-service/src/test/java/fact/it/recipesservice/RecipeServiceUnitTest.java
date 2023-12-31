package fact.it.recipesservice;

import fact.it.recipesservice.dto.RecipeRequest;
import fact.it.recipesservice.dto.RecipeResponse;
import fact.it.recipesservice.model.Ingredient;
import fact.it.recipesservice.model.Recipe;
import fact.it.recipesservice.model.RecipeIngredientUtensil;
import fact.it.recipesservice.model.Utensil;
import fact.it.recipesservice.repository.RecipeRepository;
import fact.it.recipesservice.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceUnitTest {

    @InjectMocks
    private RecipeService recipeService;
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(recipeService, "ingredientServiceBaseUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(recipeService, "utensilServiceBaseUrl", "http://localhost:8082");
    }

    @Test
    public void testGetAllRecipes() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Croissant");
        recipe.setInstructions("Insert the instructions here");
        recipe.setRecipeUrl("www.recipeurl.com");
        recipe.setImageUrl("www.imageurl.com");
        recipe.setIngredients(new ArrayList<String>(Arrays.asList("1","2")));
        recipe.setUtensils(new ArrayList<Long>(Arrays.asList(1L,2L)));

        // Mock the repository response
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        // Call the service method
        List<RecipeResponse> recipes = recipeService.getAllRecipes();

        // Verify the result
        assertEquals(1, recipes.size());
        assertEquals(1L, recipes.get(0).getId());
        assertEquals("Croissant",recipes.get(0).getName());
        assertEquals("Insert the instructions here",recipes.get(0).getInstructions());
        assertEquals("www.recipeurl.com",recipes.get(0).getRecipeUrl());
        assertEquals("www.imageurl.com",recipes.get(0).getImageUrl());
        assertEquals("1",recipes.get(0).getIngredients().get(0));
        assertEquals("2",recipes.get(0).getIngredients().get(1));
        assertEquals(1L,recipes.get(0).getUtensils().get(0));
        assertEquals(2L,recipes.get(0).getUtensils().get(1));


        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetRecipeById() {
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setName("Croissant");
        recipe.setInstructions("Insert the instructions here");
        recipe.setRecipeUrl("www.recipeurl.com");
        recipe.setImageUrl("www.imageurl.com");
        recipe.setIngredients(new ArrayList<String>(Arrays.asList("1","2")));
        recipe.setUtensils(new ArrayList<Long>(Arrays.asList(1L,2L)));
        // Mock the repository response
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // Call the service method
        Recipe foundRecipe = recipeService.getRecipeById(recipeId);

        // Verify the result
        assertEquals(recipeId, foundRecipe.getId());
        assertEquals("Croissant",foundRecipe.getName());
        assertEquals("Insert the instructions here",foundRecipe.getInstructions());
        assertEquals("www.recipeurl.com",foundRecipe.getRecipeUrl());
        assertEquals("www.imageurl.com",foundRecipe.getImageUrl());
        assertEquals("1",foundRecipe.getIngredients().get(0));
        assertEquals("2",foundRecipe.getIngredients().get(1));
        assertEquals(1L,foundRecipe.getUtensils().get(0));
        assertEquals(2L,foundRecipe.getUtensils().get(1));

        Mockito.verify(recipeRepository, Mockito.times(1)).findById(recipeId);
    }

    @Test
    public void testGetCompleteRecipe() {
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setName("Croissant");
        recipe.setInstructions("Insert the instructions here");
        recipe.setRecipeUrl("www.recipeurl.com");
        recipe.setImageUrl("www.imageurl.com");
        recipe.setIngredients(new ArrayList<String>(Arrays.asList("1","2")));
        recipe.setUtensils(new ArrayList<Long>(Arrays.asList(1L,2L)));


        // Mock the repository response
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // Mock WebClientService response
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(),  any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        Utensil utensil1 = new Utensil(1L,"Lepel", false);
        Utensil utensil2 = new Utensil(2L,"Mixer", true);


        when(responseSpec.bodyToFlux(Utensil.class)).thenReturn(Flux.just(utensil1, utensil2));

        Ingredient ingredient1 = new Ingredient("1","Bloem", "g", 300);
        Ingredient ingredient2 = new Ingredient("2","Kipfilet", "gram", 500);

        when(responseSpec.bodyToFlux(Ingredient.class)).thenReturn(Flux.just(ingredient1, ingredient2));

        // Call the service method
        RecipeIngredientUtensil completeRecipe = recipeService.getCompleteRecipe(recipeId);

        // Verify the result
        assertNotNull(completeRecipe);
        assertEquals(recipeId, completeRecipe.getRecipe().getId());
        assertEquals("Croissant",completeRecipe.getRecipe().getName());
        assertEquals("Insert the instructions here",completeRecipe.getRecipe().getInstructions());
        assertEquals("www.recipeurl.com",completeRecipe.getRecipe().getRecipeUrl());
        assertEquals("www.imageurl.com",completeRecipe.getRecipe().getImageUrl());

        // Verify utensils
        assertEquals(2, completeRecipe.getUtensils().size());

        assertEquals(1L, completeRecipe.getUtensils().get(0).getId());
        assertFalse(completeRecipe.getUtensils().get(0).isRequiresElectricity());
        assertEquals("Lepel",completeRecipe.getUtensils().get(0).getName());

        assertEquals(2L, completeRecipe.getUtensils().get(1).getId());
        assertTrue(completeRecipe.getUtensils().get(1).isRequiresElectricity());
        assertEquals("Mixer",completeRecipe.getUtensils().get(1).getName());

        // verify ingredients
        assertEquals(2, completeRecipe.getIngredients().size());

        assertEquals("1", completeRecipe.getIngredients().get(0).getId());
        assertEquals("Bloem",completeRecipe.getIngredients().get(0).getName());
        assertEquals("g",completeRecipe.getIngredients().get(0).getMeasure());
        assertEquals(300,completeRecipe.getIngredients().get(0).getAmount());

        assertEquals("2", completeRecipe.getIngredients().get(1).getId());
        assertEquals("Kipfilet",completeRecipe.getIngredients().get(1).getName());
        assertEquals("gram",completeRecipe.getIngredients().get(1).getMeasure());
        assertEquals(500,completeRecipe.getIngredients().get(1).getAmount());

    }
    @Test
    public void testCreateRecipe() {
        RecipeRequest recipeRequest = new RecipeRequest("Pasta", "Boil pasta", "www.recipe.com", "www.image.com", new ArrayList<String>(), new ArrayList<Long>());

        // Call the service method
        recipeService.createRecipe(recipeRequest);

        // Verify that save method is called once on the repository
        verify(recipeRepository, Mockito.times(1)).save(any(Recipe.class));
    }

    @Test
    public void testEditRecipe() {
        Long recipeId = 1L;
        RecipeRequest recipeRequest = new RecipeRequest("Pasta", "Boil pasta", "www.recipe.com", "www.image.com", new ArrayList<String>(), new ArrayList<Long>());

        Recipe recipeToEdit = new Recipe();
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeToEdit));

        // Create ArgumentCaptor to capture the argument passed to save method
        ArgumentCaptor<Recipe> recipeCaptor = ArgumentCaptor.forClass(Recipe.class);

        // Call the service method
        recipeService.editRecipe(recipeId, recipeRequest);

        // Verify that save method is called once on the repository with the captured argument
        Mockito.verify(recipeRepository, Mockito.times(1)).save(recipeCaptor.capture());

        // Retrieve the captured argument
        Recipe editedRecipe = recipeCaptor.getValue();

        // Assert that the data in the editedUtensil matches the data in the utensilRequest
        assertEquals(recipeRequest.getName(), editedRecipe.getName());
        assertEquals(recipeRequest.getInstructions(),editedRecipe.getInstructions());
        assertEquals(recipeRequest.getRecipeUrl(),editedRecipe.getRecipeUrl());
        assertEquals(recipeRequest.getImageUrl(),editedRecipe.getImageUrl());

    }

    @Test
    public void testDeleteRecipe() {
        Recipe foundRecipe = new Recipe();
        Long recipeId = 1L;
        foundRecipe.setId(recipeId);
        foundRecipe.setIngredients(Arrays.asList("ingredientId1", "ingredientId2"));
        foundRecipe.setUtensils(Arrays.asList(1L, 2L));

        when(recipeRepository.findById(1L)).thenReturn(java.util.Optional.of(foundRecipe));

        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(),  any(Function.class))).thenReturn(requestHeadersSpec);

        // Call the service method
        recipeService.deleteRecipe(recipeId);

        verify(webClient, Mockito.times(4)).delete();
        // Verify that deleteById method is called once on the repository
        verify(recipeRepository, Mockito.times(1)).deleteById(recipeId);
    }

    @Test
    public void testGetRecipeByIdNotFound() {
        Long recipeId = 1L;
        // Mock the repository response
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        // Verify that the service method throws a ResponseStatusException
        assertThrows(ResponseStatusException.class, () -> recipeService.getRecipeById(recipeId));
    }

}
