<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionnaire - Tableau de Bord</title>
    <link rel="stylesheet" href="resources/css/test.css">
</head>
<body>
<div class="wrapper">
    <div class="container login-box">
        <h2>Bienvenue Gestionnaire</h2>
        <p>Vous pouvez gérer les produits et les stocks.</p>

        <a href="<%= request.getContextPath() %>/produits" class="button">🛒 Voir les produits</a>
        <br><br>
        <a href="<%= request.getContextPath() %>/fournisseurs" class="button">📦 Gérer les fournisseurs</a>

    </div>
</div>
</body>
</html>
