<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, models.CommandeFournisseur" %>
<html>
<head>
    <title>Historique des commandes fournisseurs</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/test.css" />
</head>
<body>
<div class="wrapper">
    <div class="container">
        <h2>📋 Historique des commandes fournisseurs</h2>

        <a href="<%= request.getContextPath() %>/produits" class="button-link">⬅ Retour aux Produits</a><br><br>

        <table border="1" cellpadding="8" cellspacing="0" style="width: 100%; color: white;">
            <thead>
            <tr>
                <th>ID Commande</th>
                <th>ID Fournisseur</th>
                <th>Date Commande</th>
                <th>Statut</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<CommandeFournisseur> commandes = (List<CommandeFournisseur>) request.getAttribute("commandes");
                if (commandes != null && !commandes.isEmpty()) {
                    for (CommandeFournisseur c : commandes) {
            %>
            <tr>
                <td><%= c.getId() %></td>
                <td><%= c.getFournisseurId() %></td>
                <td><%= c.getDateCommande() %></td>
                <td><%= c.getStatut() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="4">Aucune commande fournisseur enregistrée.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
