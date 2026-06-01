package ma.ac.esi.culinaryarts.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ma.ac.esi.culinaryarts.service.RecipeService;
import java.io.IOException;

@WebServlet("/RecipeDeleteController")
public class RecipeDeleteController extends HttpServlet {

    private RecipeService recipeService = new RecipeService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Vérification de session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        recipeService.deleteRecipe(id);

        response.sendRedirect(request.getContextPath() + "/recipes");
    }
}