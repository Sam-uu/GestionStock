<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Produit" %>
<%
    Produit produit = (Produit) request.getAttribute("produit");
    String fournisseurNom = (String) request.getAttribute("fournisseurNom");
%>
<html>
<head>
    <title>Commander produit</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/test.css" />
</head>
<body>
<div class="wrapper">
    <div class="container">
        <h2>Commander : <%= produit.getNom() %></h2>

        <p><strong>Fournisseur :</strong> <%= fournisseurNom %></p>

        <form method="post" action="<%= request.getContextPath() %>/produits/commander">
            <input type="hidden" name="idProduit" value="<%= produit.getId() %>"/>

            <label for="quantite">Quantité :</label>
            <input type="number" name="quantite" id="quantite" class="input" min="1" required />

            <br/><br/>
            <button type="submit" class="button">📦 Valider la commande</button>
            <a href="<%= request.getContextPath() %>/produits" class="link">Annuler</a>
        </form>
    </div>
</div>
</body>
</html>


</body>
</html>
