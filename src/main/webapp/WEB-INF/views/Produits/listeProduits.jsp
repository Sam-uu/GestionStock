<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, models.Produit" %>
<html>
<head>
    <title>Liste des Produits</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/test.css" />
</head>
<body>
<div class="wrapper">
    <div class="container">
        <div class="login-box">
            <h2>Produits en stock</h2>

            <!-- ✅ Boutons -->
            <a href="<%= request.getContextPath() %>/produits/ajouter" class="button">➕ Ajouter un produit</a>
            <a href="<%= request.getContextPath() %>/commandes-fournisseurs/historique" class="button">📋 Historique commandes fournisseurs</a>
            <br><br>

            <table>
                <thead>
                <tr>
                    <th>ID Fournisseur</th>
                    <th>Nom Fournisseur</th>
                    <th>Nom Produit</th>
                    <th>Description</th>
                    <th>Quantité</th>
                    <th>Seuil</th>
                    <th>Prix (DH)</th>
                    <th>Actions</th>
                    <th>Image</th>
                </tr>
                </thead>

                <tbody>
                <%
                    List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                    if (produits != null && !produits.isEmpty()) {
                        for (Produit p : produits) {
                            boolean alerte = p.isAlerte();
                %>
                <tr class="<%= alerte ? "low-stock" : "" %>">
                    <td><%= p.getFournisseurId() > 0 ? p.getFournisseurId() : "-" %></td>
                    <td><%= p.getFournisseurNom() != null ? p.getFournisseurNom() : "Non lié" %></td>
                    <td><%= p.getNom() %></td>
                    <td><%= p.getDescription() %></td>
                    <td>
                        <%= p.getQuantite() %>
                        <% if (alerte) { %> ⚠<br><span style="color: yellow;">Stock faible</span><% } %>
                    </td>
                    <td><%= p.getSeuilAlerte() %></td>
                    <td><%= p.getPrix() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/produits/modifier?id=<%= p.getId() %>" class="action-link">Modifier</a>
                        <form action="<%= request.getContextPath() %>/produits/supprimer" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= p.getId() %>"/>
                            <button type="submit" class="action-link" onclick="return confirm('Confirmer la suppression ?')">Supprimer</button>
                        </form>
                        <a href="<%= request.getContextPath() %>/produits/commander?idProduit=<%= p.getId() %>" class="action-link">📦 Commander</a>
                    </td>
                    <td>
                        <% if (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) { %>
                        <img src="<%= request.getContextPath() + "/" + p.getImageUrl().replace("\\", "/") %>" width="60" height="60"/>
                        <% } else { %>
                        <span style="color:gray;">Pas d'image</span>
                        <% } %>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr><td colspan="9">Aucun produit disponible.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
