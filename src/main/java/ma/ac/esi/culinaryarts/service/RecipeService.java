package ma.ac.esi.culinaryarts.service;

import ma.ac.esi.culinaryarts.model.Recipe;
import ma.ac.esi.culinaryarts.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class RecipeService {

    private RecipeRepository recipeRepository = new RecipeRepository();

    // ── Retourne toutes les recettes (Catalogue complet) ──────────
    public List<Recipe> getAllRecipes() {
        return recipeRepository.getAllRecipes();
    }

    // ── Retourne une recette précise par son ID ──────────────────
    public Recipe getRecipeById(int id) {
        return recipeRepository.getRecipeById(id);
    }

    // ── Filtre les recettes par catégorie (Entrée, Plat, Dessert...) ──
    public List<Recipe> getRecipesByCategory(String category) {
        List<Recipe> allRecipes = recipeRepository.getAllRecipes();
        List<Recipe> filtered = new ArrayList<>();
        
        for (Recipe recipe : allRecipes) {
            if (recipe.getCategory() != null && 
                recipe.getCategory().equalsIgnoreCase(category)) {
                filtered.add(recipe);
            }
        }
        return filtered;
    }
    public boolean submitRecipe(Recipe recipe) {
        
        if (recipe.getTitle() == null || recipe.getTitle().trim().isEmpty()) {
            return false;
        }
        // Par défaut, si aucune image n'est fournie, on met l'image standard
        if (recipe.getImageUrl() == null || recipe.getImageUrl().isEmpty()) {
            recipe.setImageUrl("default.jpg");
        }
        return recipeRepository.insertRecipe(recipe);
    }
 // ── Met à jour une recette existante ─────────────────────────
    public boolean updateRecipe(Recipe recipe) {
        if (recipe.getTitle() == null || recipe.getTitle().trim().isEmpty()) {
            return false;
        }
        return recipeRepository.updateRecipe(recipe);
    }

    // ── Supprime une recette par son ID ──────────────────────────
    public boolean deleteRecipe(int id) {
        return recipeRepository.deleteRecipe(id);
    }
}