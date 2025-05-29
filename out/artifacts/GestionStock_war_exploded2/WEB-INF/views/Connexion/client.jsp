<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 06/05/2025
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Utilisateur" %>

<%
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");

    if (utilisateur == null) {
        response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
        return;
    }
%>
<html>
<head>
    <title>Tableau de bord client</title>
    <link rel="stylesheet" href="resources/css/test.css">
</head>
<body>
<div class="wrapper">
    <div class="container login-box">
        <h2>Bienvenue <%= utilisateur.getNom() %></h2>
        <p>Vous êtes connecté en tant que <strong>Client</strong>.</p>

        <a href="<%= request.getContextPath() %>/PasserCommandeServlet" class="button">🛒 Passer une commande</a><br><br>
        <a href="<%= request.getContextPath() %>/ClientCommandeServlet" class="button">📦 Voir mes commandes</a>
    </div>
</div>
</body>
</html>

