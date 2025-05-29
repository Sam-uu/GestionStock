<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Tableau de Bord</title>
    <link rel="stylesheet" href="resources/css/test.css">
</head>
<body>
<div class="wrapper">
    <div class="container login-box">
        <h2>Bienvenue Admin</h2>
        <p>Vous avez un accès complet à la gestion du système.</p>

        <a href="<%= request.getContextPath() %>/produits" class="button">🛒 Gérer les produits</a>
        <br><br>
        <a href="<%= request.getContextPath() %>/fournisseurs" class="button">📦 Gérer les fournisseurs</a>
        <a href="<%= request.getContextPath() %>/dashboard" class="button">📊 Tableau de bord</a>

    </div>
</div>
</body>
</html>
