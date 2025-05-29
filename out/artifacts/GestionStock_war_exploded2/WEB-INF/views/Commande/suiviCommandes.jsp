<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 06/05/2025
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.CommandeClient, java.util.*" %>
<html>
<head>
    <title>Suivi de mes commandes</title>
  <link rel="stylesheet" href="resources/css/test.css">
</head>
<body>
<div class="wrapper">
  <div class="container login-box">
    <h2>Mes commandes</h2>
    <table border="1" cellpadding="8" cellspacing="0" style="width: 100%; color: white;">
      <thead>
      <tr>
        <th>ID</th>
        <th>Produit ID</th>
        <th>Quantité</th>
        <th>Statut</th>
        <th>Date</th>
      </tr>
      </thead>
      <tbody>
      <%
        List<CommandeClient> commandes = (List<CommandeClient>) request.getAttribute("commandes");
        if (commandes != null && !commandes.isEmpty()) {
          for (CommandeClient c : commandes) {
      %>
      <tr>
        <td><%= c.getId() %></td>
        <td><%= c.getProduitId() %></td>
        <td><%= c.getQuantite() %></td>
        <td><%= c.getStatut() %></td>
        <td><%= c.getDateCommande() %></td>
      </tr>
      <%
        }
      } else {
      %>
      <tr>
        <td colspan="5">Aucune commande enregistrée.</td>
      </tr>
      <%
        }
      %>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>
