package ma.ac.esi.culinaryarts.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import ma.ac.esi.culinaryarts.model.Recipe;
import ma.ac.esi.culinaryarts.service.RecipeService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/RecipeSubmitController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class RecipeSubmitController extends HttpServlet {

    private RecipeService recipeService = new RecipeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/submitRecipe.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("LOG : Entrée dans le doPost de RecipeSubmitController"); // POINT 1

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("LOG : Utilisateur non connecté, redirection.");
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        try {
            // 1. Récupération des paramètres
            String title = request.getParameter("title");
            String category = request.getParameter("category");
            String chefName = request.getParameter("chefName");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            
            System.out.println("LOG : Données reçues -> Titre: " + title + ", Prix: " + priceStr); // POINT 2

            double price = 0.0;
            if (priceStr != null && !priceStr.isEmpty()) {
                price = Double.parseDouble(priceStr);
            }

            // 2. Gestion de l'image
            Part filePart = request.getPart("imageFile");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            // Chemin de destination
            String uploadPath = getServletContext().getRealPath("/") + "assets" + File.separator + "images";
            System.out.println("LOG : Tentative d'écriture dans : " + uploadPath); // POINT 3
            
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                System.out.println("LOG : Création du dossier assets/images : " + created);
            }

            filePart.write(uploadPath + File.separator + uniqueFileName);
            System.out.println("LOG : Image écrite avec succès."); // POINT 4

            // 3. Insertion via le Service
            Recipe recipe = new Recipe(0, title, category, chefName, description, price, uniqueFileName);
            boolean success = recipeService.submitRecipe(recipe);
            
            System.out.println("LOG : Résultat insertion Service : " + success); // POINT 5

            if (success) {
                request.setAttribute("message", "Recette ajoutée avec succès !");
            } else {
                request.setAttribute("error", "L'insertion en base de données a échoué.");
            }

        } catch (Exception e) {
            System.out.println("LOG ERROR : Une exception est survenue !");
            e.printStackTrace(); // CECI affichera le rouge dans la console
            request.setAttribute("error", "Erreur système : " + e.getMessage());
        }

        request.getRequestDispatcher("/submitRecipe.jsp").forward(request, response);
    }
}