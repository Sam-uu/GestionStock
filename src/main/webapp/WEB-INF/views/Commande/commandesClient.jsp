<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 06/05/2025
  Time: 23:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Mes commandes</title>
    <link rel="stylesheet" href="resources/css/test.css">
</head>
<body>
<div class="wrapper" style="overflow-x: auto";>
    <div class="container login-box">
        <h2>📦 Mes commandes</h2>
        <table border="1" cellpadding="10" style="width: 100%; color: white;">
            <tr>
                <th>ID</th>
                <th>Date</th>
                <th>Produit</th>
                <th>Quantité</th>
                <th>Statut</th>
            </tr>
            <c:forEach var="cmd" items="${commandes}">
                <tr>
                    <td>${cmd["id"]}</td>
                    <td>${cmd["date"]}</td>
                    <td>${cmd["produit"]}</td>
                    <td>${cmd["quantite"]}</td>
                    <td>${cmd["statut"]}</td>

                </tr>
            </c:forEach>
        </table>
        <br />
        <a href="<%= request.getContextPath() %>/ClientPage" class="link">⬅ Retour à l’accueil</a>
    </div>
</div>
</body>
</html>
