<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter une Recette — Culinary Arts</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f7f6; color: #2c3e50; padding: 40px 20px; }
        .container { max-width: 700px; margin: auto; background: white; padding: 40px; border-radius: 20px; box-shadow: 0 15px 35px rgba(0,0,0,0.05); }
        h1 { color: #e91e63; text-align: center; margin-bottom: 30px; }
        .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: 600; }
        input, select, textarea { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 10px; box-sizing: border-box; }
        button { width: 100%; padding: 15px; background: #27ae60; color: white; border: none; border-radius: 12px; cursor: pointer; font-size: 1.1rem; font-weight: bold; transition: 0.3s; }
        button:hover { background: #219150; }
        .success { background: #d4edda; color: #155724; padding: 15px; border-radius: 10px; margin-bottom: 20px; }
        .error { background: #f8d7da; color: #721c24; padding: 15px; border-radius: 10px; margin-bottom: 20px; }
        .file-input { background: #fafafa; border: 2px dashed #ddd; padding: 20px; text-align: center; }
    </style>
</head>
<body>

<div class="container">
    <h1>Partagez votre Création</h1>
 
    <% String message = (String) request.getAttribute("message"); %>
    <% String error = (String) request.getAttribute("error"); %>
    <% if (message != null) { %><div class="success">✅ <%= message %></div><% } %>
    <% if (error != null) { %><div class="error">❌ <%= error %></div><% } %>
 
    <form action="<%= request.getContextPath() %>/RecipeSubmitController" method="post" enctype="multipart/form-data">
        
        <div class="form-group file-input">
            <label for="imageFile">Photo du plat *</label>
            <input type="file" id="imageFile" name="imageFile" accept="image/*" required>
        </div>

        <div class="form-group">
            <label for="title">Nom de la recette *</label>
            <input type="text" id="title" name="title" required>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="category">Catégorie</label>
                <select id="category" name="category">
                    <option value="Entrée">Entrée</option>
                    <option value="Plat">Plat Principal</option>
                    <option value="Dessert">Dessert</option>
                    <option value="Boisson">Boisson</option>
                </select>
            </div>
            <div class="form-group">
                <label for="price">Prix (€)</label>
                <input type="number" id="price" name="price" step="0.01" required>
            </div>
        </div>

        <div class="form-group">
            <label for="chefName">Nom du Chef</label>
            <input type="text" id="chefName" name="chefName">
        </div>
 
        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description"></textarea>
        </div>
 
        <button type="submit">Publier la recette</button>
    </form>
</div>

</body>
</html>
