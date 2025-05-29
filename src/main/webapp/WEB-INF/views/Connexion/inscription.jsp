<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscription Client</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/theme2.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<div class="wrapper">
    <div class="container">
        <div class="login-box">
            <form class="form" method="post" action="${pageContext.request.contextPath}/Inscription">
                <div class="logo"></div>
                <span class="header">Créer un compte client</span>

                <% if (request.getAttribute("message") != null) { %>
                <fieldset id="error_fieldset" style="border: 2px solid red; padding: 10px;">
                    <legend align="left" style="color:red;">Erreur</legend>
                    <p style="color:red;"><%= request.getAttribute("message") %></p>
                </fieldset>
                <% } %>

                <input type="text" name="nom" placeholder="Nom complet" class="input" required />
                <input type="email" name="email" placeholder="Adresse email" class="input" required />
                <input type="password" name="motdepasse" placeholder="Mot de passe" class="input" required />

                <button type="submit" class="button sign-in">S'inscrire</button>

                <p class="footer">
                    Vous avez déjà un compte ?
                    <a href="<%= request.getContextPath() %>/ConnexionServlet" class="link">Se connecter</a>

                </p>
            </form>
        </div>
    </div>
</div>

</body>
</html>
