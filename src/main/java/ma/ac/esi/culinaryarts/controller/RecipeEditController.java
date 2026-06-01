package ma.ac.esi.culinaryarts.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ma.ac.esi.culinaryarts.model.Recipe;
import ma.ac.esi.culinaryarts.service.RecipeService;
import java.io.IOException;

@WebServlet("/RecipeEditController")
public class RecipeEditController extends HttpServlet {

    private RecipeService recipeService = new RecipeService();

    // GET : affiche le formulaire pré-rempli
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Recipe recipe = recipeService.getRecipeById(id); // à ajouter dans RecipeService
        request.setAttribute("recipe", recipe);
        request.getRequestDispatcher("/editRecipe.jsp").forward(request, response);
    }

    // POST : enregistre les modifications
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String chefName = request.getParameter("chefName");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        Recipe recipe = new Recipe(id, title, category, chefName, description, price, null);
        recipeService.updateRecipe(recipe); // à ajouter dans RecipeService

        response.sendRedirect(request.getContextPath() + "/recipes");
    }
}