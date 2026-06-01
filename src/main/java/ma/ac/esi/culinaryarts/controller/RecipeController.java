package ma.ac.esi.culinaryarts.controller;

import ma.ac.esi.culinaryarts.model.Recipe;
import ma.ac.esi.culinaryarts.service.RecipeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/recipes") // C'est l'URL que vous appellerez (ex: localhost:8080/CulinaryArts/recipes)
public class RecipeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RecipeService recipeService = new RecipeService();

        // Lire le paramètre optionnel ?category=... (ex: ?category=Dessert)
        String category = request.getParameter("category");

        List<Recipe> recipes;
        
        if (category != null && !category.trim().isEmpty()) {
            // Filtrer par catégorie culinaire si le paramètre est présent
            recipes = recipeService.getRecipesByCategory(category);
        } else {
            // Sinon retourner toutes les recettes du catalogue
            recipes = recipeService.getAllRecipes();
        }

        // Transmettre la liste à la JSP via les attributs de requête
        // On utilise "recipesAttr" pour être bien clair dans la JSP
        request.setAttribute("recipesAttr", recipes);
        request.setAttribute("currentCategory", category);

        // Forward vers la vue JSP (le fichier recipes.jsp doit être à la racine de webapp)
        request.getRequestDispatcher("/recipes.jsp").forward(request, response);
    }
}