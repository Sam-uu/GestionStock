<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Produit" %>
<%
    Produit produit = (Produit) request.getAttribute("produit");
    boolean isEdit = (produit != null);
%>
<html>
<head>
    <title><%= isEdit ? "Modifier" : "Ajouter" %> un produit</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/test.css" />
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const form = document.querySelector("form");
            const erreurMsg = document.getElementById("erreur-message");

            form.addEventListener("submit", function (e) {
                const quantite = parseInt(document.getElementById("quantite").value);
                const seuil = parseInt(document.getElementById("seuil").value);

                if (seuil >= quantite) {
                    e.preventDefault();
                    erreurMsg.style.display = "block";
                } else {
                    erreurMsg.style.display = "none";
                }
            });
        });


    </script>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <h2><%= isEdit ? "Modifier" : "Ajouter" %> un produit</h2>
        <p id="erreur-message" style="color: red; display: none; margin-bottom: 10px;">
            ⚠ Le seuil d'alerte doit être inférieur à la quantité.
        </p>
        <form method="post"
              action="<%= request.getContextPath() + (isEdit ? "/produits/modifier" : "/produits/ajouter") %>"
              enctype="multipart/form-data">

            <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= produit.getId() %>"/>
            <% } %>

            <label for="nom">Nom du produit :</label>
            <input type="text" id="nom" name="nom" class="input" value="<%= isEdit ? produit.getNom() : "" %>" required />

            <label for="description">Description :</label>
            <textarea id="description" name="description" class="input" required><%= isEdit ? produit.getDescription() : "" %></textarea>

            <label for="quantite">Quantité :</label>
            <input type="number" id="quantite" name="quantite" class="input" value="<%= isEdit ? produit.getQuantite() : 0 %>" required />

            <label for="seuil">Seuil d'alerte :</label>
            <input type="number" id="seuil" name="seuil" class="input" value="<%= isEdit ? produit.getSeuilAlerte() : 0 %>" required />


            <label for="prix">Prix :</label>
            <input type="number" step="0.01" id="prix" name="prix" class="input" value="<%= isEdit ? produit.getPrix() : 0 %>" required />

            <%-- Affichage de l'image actuelle --%>
            <% if (isEdit && produit.getImageUrl() != null && !produit.getImageUrl().isEmpty()) { %>
            <label>Image actuelle :</label>
            <img src="<%= request.getContextPath() + "/" + produit.getImageUrl() %>" width="120" style="margin-bottom: 15px;" />
            <% } %>

            <label for="image">Image du produit :</label>
            <input type="file" id="image" name="image" accept="image/*" class="input" />

            <br/>
            <button type="submit" class="button"><%= isEdit ? "Modifier" : "Ajouter" %></button>
            <a href="<%= request.getContextPath() %>/produits" class="link">Annuler</a>
        </form>
    </div>
</div>
</body>
</html>


