package ma.ac.esi.culinaryarts.repository;
 
import ma.ac.esi.culinaryarts.model.Recipe;
import ma.ac.esi.culinaryarts.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class RecipeRepository {
 
    private static final String SELECT_ALL =
            "SELECT id, title, category, chef_name, description, price, image_url " + 
            "FROM Recipes " +
            "ORDER BY id ASC";
 
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                recipes.add(new Recipe(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("chef_name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("image_url")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }
 
    public boolean insertRecipe(Recipe recipe) {
        String sql = "INSERT INTO Recipes (title, category, chef_name, description, price, image_url) VALUES (?, ?, ?, ?, ?, ?)";
        System.out.println("Tentative d'insertion SQL pour : " + recipe.getTitle());
 
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            stmt.setString(1, recipe.getTitle());
            stmt.setString(2, recipe.getCategory());
            stmt.setString(3, recipe.getChefName());
            stmt.setString(4, recipe.getDescription());
            stmt.setDouble(5, recipe.getPrice());
            stmt.setString(6, recipe.getImageUrl());
 
            int result = stmt.executeUpdate();
            System.out.println("Résultat de l'exécution : " + result);
            return result > 0;
 
        } catch (SQLException e) {
            System.err.println("ÉCHEC SQL : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
 
    public Recipe getRecipeById(int id) {
        String sql = "SELECT * FROM Recipes WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Recipe(
                        rs.getInt("id"), rs.getString("title"), rs.getString("category"),
                        rs.getString("chef_name"), rs.getString("description"),
                        rs.getDouble("price"), rs.getString("image_url")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    // ── Met à jour une recette (sans changer l'image) ────────────
    public boolean updateRecipe(Recipe recipe) {
        String sql = "UPDATE Recipes SET title=?, category=?, chef_name=?, description=?, price=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();  // CORRIGÉ : DBUtil.getConnection()
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getCategory());
            ps.setString(3, recipe.getChefName());
            ps.setString(4, recipe.getDescription());
            ps.setDouble(5, recipe.getPrice());
            ps.setInt(6, recipe.getId());
 
            int result = ps.executeUpdate();
            System.out.println("LOG updateRecipe : " + result + " ligne(s) modifiée(s).");
            return result > 0;
 
        } catch (SQLException e) {
            System.err.println("ÉCHEC updateRecipe : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
 
    // ── Supprime une recette par ID ───────────────────────────────
    public boolean deleteRecipe(int id) {
        String sql = "DELETE FROM Recipes WHERE id=?";
        try (Connection conn = DBUtil.getConnection();  // CORRIGÉ : DBUtil.getConnection()
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            System.out.println("LOG deleteRecipe id=" + id + " : " + result + " ligne(s) supprimée(s).");
            return result > 0;
 
        } catch (SQLException e) {
            System.err.println("ÉCHEC deleteRecipe : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    // Méthode getConnection() privée SUPPRIMÉE — elle retournait null (NullPointerException)
}