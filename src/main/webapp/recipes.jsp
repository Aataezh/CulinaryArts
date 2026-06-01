<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, ma.ac.esi.culinaryarts.model.Recipe" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Catalogue Gastronomique - Culinary Arts</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: auto;
        }
        h1 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
            text-transform: uppercase;
            letter-spacing: 2px;
        }
        
        /* Grille de cartes */
        .recipes-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 25px;
            justify-content: center;
        }

        /* Style de la carte */
        .recipe-card {
            background: white;
            border-radius: 15px;
            width: 300px;
            box-shadow: 0 10px 20px rgba(0,0,0,0.05);
            overflow: hidden;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            display: flex;
            flex-direction: column;
        }

        .recipe-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.1);
        }

        /* Zone Image */
        .recipe-image {
            width: 100%;
            height: 200px;
            background-color: #ddd;
            overflow: hidden;
        }

        .recipe-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        /* Contenu de la carte */
        .recipe-content {
            padding: 20px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }

        .recipe-title {
            font-size: 1.25rem;
            color: #e91e63;
            margin: 0 0 10px 0;
            font-weight: bold;
        }

        .recipe-category {
            font-size: 0.85rem;
            color: #7f8c8d;
            text-transform: uppercase;
            margin-bottom: 10px;
            display: block;
        }

        .recipe-description {
            font-size: 0.95rem;
            color: #34495e;
            line-height: 1.5;
            margin-bottom: 15px;
            flex-grow: 1;
        }

        .recipe-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-top: 1px solid #eee;
            padding-top: 15px;
        }

        .chef-name {
            font-size: 0.9rem;
            color: #2c3e50;
            font-weight: 500;
        }

        .price {
            font-size: 1.2rem;
            font-weight: bold;
            color: #27ae60;
        }

        .no-data {
            text-align: center;
            width: 100%;
            color: #95a5a6;
            margin-top: 50px;
        }
        .btn-add {
    background-color: #e91e63; /* Rose signature */
    color: white;
    padding: 12px 25px;
    text-decoration: none;
    border-radius: 30px;
    font-weight: bold;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(233, 30, 99, 0.3);
}

.btn-add:hover {
    background-color: #c2185b;
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(233, 30, 99, 0.4);
}
.recipe-actions {
    display: flex;
    gap: 10px;
    padding: 10px 20px 15px;
    border-top: 1px solid #eee;
    justify-content: flex-end;
}

.btn-edit {
    padding: 7px 14px;
    background-color: #3498db;
    color: white;
    border-radius: 20px;
    text-decoration: none;
    font-size: 0.85rem;
    font-weight: bold;
    transition: background 0.3s;
}
.btn-edit:hover { background-color: #2980b9; }

.btn-delete {
    padding: 7px 14px;
    background-color: #e74c3c;
    color: white;
    border: none;
    border-radius: 20px;
    font-size: 0.85rem;
    font-weight: bold;
    cursor: pointer;
    transition: background 0.3s;
}
.btn-delete:hover { background-color: #c0392b; }
    </style>
</head>
<body>

<div class="container">
    <h1>Marketplace Culinary Arts</h1>
    <div class="header-actions" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px;">
    <h1>Catalogue Gastronomique</h1>
    
    <a href="<%= request.getContextPath() %>/RecipeSubmitController" class="btn-add">
        + Ajouter une Recette
    </a>
</div>

    <% 
        String category = (String) request.getAttribute("currentCategory");
        if (category != null && !category.isEmpty()) {
    %>
        <p style="text-align:center; color: #666; margin-bottom: 30px;">
            Filtre actif : <strong><%= category %></strong>
        </p>
    <% } %>
    <div class="user-info">
    <span>
        <%-- Affiche le nom de l'utilisateur ou "Visiteur" --%>
        👤 <%= session.getAttribute("user") != null 
              ? session.getAttribute("user") : "Visiteur" %>
    </span>
</div>

<% if (session.getAttribute("user") != null) { %>
    <%-- Bouton de déconnexion via un formulaire POST pour la sécurité --%>
    <form action="<%= request.getContextPath() %>/LogoutController" method="post"> 
        <button type="submit" class="logout-btn">
            Déconnexion
        </button>
    </form>
<% } %>

    <div class="recipes-grid">
        <% 
            List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipesAttr");
            
            if (recipes != null && !recipes.isEmpty()) {
                for (Recipe r : recipes) {
        %>
            <div class="recipe-card">
                <div class="recipe-image">
                    <img src="assets/images/<%= r.getImageUrl() %>" alt="<%= r.getTitle() %>">
                </div>
                
                <div class="recipe-content">
                    <span class="recipe-category"><%= r.getCategory() %></span>
                    <h3 class="recipe-title"><%= r.getTitle() %></h3>
                    <p class="recipe-description"><%= r.getDescription() %></p>
                    
                    <div class="recipe-footer">
    <span class="chef-name">👨‍🍳 <%= r.getChefName() %></span>
    <span class="price"><%= r.getPrice() %> €</span>
</div>

<%-- AJOUT : Boutons Modifier / Supprimer --%>
<div class="recipe-actions">
    <a href="<%= request.getContextPath() %>/RecipeEditController?id=<%= r.getId() %>" 
       class="btn-edit">✏️ Modifier</a>

    <form action="<%= request.getContextPath() %>/RecipeDeleteController" method="post"
          onsubmit="return confirm('Supprimer cette recette ?');">
        <input type="hidden" name="id" value="<%= r.getId() %>">
        <button type="submit" class="btn-delete">🗑️ Supprimer</button>
    </form>
</div>
                </div>
            </div>
        <% 
                }
            } else {
        %>
            <div class="no-data">
                <h3>Aucune recette disponible.</h3>
                <p>Revenez plus tard pour découvrir les nouvelles créations de nos chefs.</p>
            </div>
        <% } %>
    </div>
</div>

</body>
</html>