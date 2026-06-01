<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ma.ac.esi.culinaryarts.model.Recipe" %>
<%
    Recipe recipe = (Recipe) request.getAttribute("recipe");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier la Recette</title>
    <style>
        /* Même style que submitRecipe.jsp */
        body { font-family: 'Segoe UI', sans-serif; background: #f4f7f6; margin: 0; padding: 20px; }
        .form-container { max-width: 600px; margin: auto; background: white;
                          padding: 40px; border-radius: 15px;
                          box-shadow: 0 10px 20px rgba(0,0,0,0.08); }
        h2 { color: #e91e63; text-align: center; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 6px; font-weight: bold; color: #2c3e50; }
        input, select, textarea {
            width: 100%; padding: 10px 14px; border: 1px solid #ddd;
            border-radius: 8px; box-sizing: border-box; font-size: 14px;
        }
        textarea { resize: vertical; min-height: 100px; }
        .btn-save {
            width: 100%; padding: 12px; background-color: #3498db;
            color: white; border: none; border-radius: 25px;
            font-size: 16px; font-weight: bold; cursor: pointer;
            transition: background 0.3s;
        }
        .btn-save:hover { background-color: #2980b9; }
        .btn-cancel {
            display: block; text-align: center; margin-top: 15px;
            color: #7f8c8d; text-decoration: none; font-size: 14px;
        }
        .message { padding: 10px; border-radius: 8px; margin-bottom: 20px; text-align: center; }
        .success { background: #d5f5e3; color: #1e8449; }
        .error   { background: #fadbd8; color: #922b21; }
    </style>
</head>
<body>
<div class="form-container">
    <h2>✏️ Modifier la Recette</h2>

    <% if (request.getAttribute("message") != null) { %>
        <div class="message success"><%= request.getAttribute("message") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="message error"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/RecipeEditController" method="post">
        <%-- ID caché indispensable pour savoir quelle recette modifier --%>
        <input type="hidden" name="id" value="<%= recipe.getId() %>">

        <div class="form-group">
            <label>Titre</label>
            <input type="text" name="title" value="<%= recipe.getTitle() %>" required>
        </div>

        <div class="form-group">
            <label>Catégorie</label>
            <select name="category">
                <option <%= "Entrée".equals(recipe.getCategory())  ? "selected" : "" %>>Entrée</option>
                <option <%= "Plat".equals(recipe.getCategory())    ? "selected" : "" %>>Plat</option>
                <option <%= "Dessert".equals(recipe.getCategory()) ? "selected" : "" %>>Dessert</option>
                <option <%= "Boisson".equals(recipe.getCategory()) ? "selected" : "" %>>Boisson</option>
            </select>
        </div>

        <div class="form-group">
            <label>Nom du Chef</label>
            <input type="text" name="chefName" value="<%= recipe.getChefName() %>" required>
        </div>

        <div class="form-group">
            <label>Description</label>
            <textarea name="description"><%= recipe.getDescription() %></textarea>
        </div>

        <div class="form-group">
            <label>Prix (€)</label>
            <input type="number" name="price" step="0.01" 
                   value="<%= recipe.getPrice() %>" required>
        </div>

        <button type="submit" class="btn-save">💾 Enregistrer les modifications</button>
        <a href="<%= request.getContextPath() %>/recipes" class="btn-cancel">← Annuler</a>
    </form>
</div>
</body>
</html>