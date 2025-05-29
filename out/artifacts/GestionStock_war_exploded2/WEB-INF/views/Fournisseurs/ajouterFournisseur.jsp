<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (request.getAttribute("error") != null) ? (String) request.getAttribute("error") : "";
%>
<html>
<head>
    <title>Ajouter un Fournisseur</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/test.css" />
    <script>
        // ✅ Script pour générer dynamiquement les champs de produits
        function ajouterChampsProduits() {
            const nombre = document.getElementById("nbProduits").value;
            const container = document.getElementById("produitsContainer");
            container.innerHTML = ""; // Clear previous inputs

            for (let i = 1; i <= nombre; i++) {
                const input = document.createElement("input");
                input.type = "text";
                input.name = "nomProduit";
                input.className = "input";
                input.placeholder = "Nom du produit " + i;
                input.required = false;

                container.appendChild(input);
            }
        }
    </script>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <h2>Ajouter un Fournisseur</h2>

        <% if (!error.isEmpty()) { %>
        <p style="color: red; font-weight: bold;"><%= error %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/fournisseurs/ajouter" method="post">
            <label for="id">ID Fournisseur :</label>
            <input type="number" name="id" id="id" class="input" required />

            <label for="nom">Nom :</label>
            <input type="text" name="nom" id="nom" class="input" required />

            <label for="adresse">Adresse :</label>
            <input type="text" name="adresse" id="adresse" class="input" required />

            <label for="telephone">Téléphone :</label>
            <input type="text" name="telephone" id="telephone" class="input" required />

            <label for="email">Email :</label>
            <input type="email" name="email" id="email" class="input" required />

            <!-- ✅ Nouvelle section pour saisir les produits -->
            <h3>Produits proposés :</h3>
            <label for="nbProduits">Nombre de produits proposés :</label>
            <input type="number" id="nbProduits" min="0" value="0" class="input" onchange="ajouterChampsProduits()" />

            <div id="produitsContainer" style="margin-top: 10px;"></div>

            <br>
            <button type="submit" class="button">➕ Ajouter le Fournisseur</button>
        </form>
    </div>
</div>
</body>
</html>
