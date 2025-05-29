<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscription</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/theme2.css" />

</head>
<body>
<div class="wrapper">
<div class="container">
    <div class="login-box">
        <form class="form" method="post" action="Inscription">
            <div class="logo"></div>
            <span class="header">Créer un compte</span>

            <% if (request.getAttribute("errors") != null) { %>
            <fieldset id="error_fieldset" style="border: 2px solid red; padding: 10px;">
                <legend align="left" style="color:red;">Erreurs</legend>
            <ul style="color:red; list-style: disc; margin-left: 20px;">
                <% if (request.getAttribute("nom_error") != null) { %>
                <li class="error">Le nom est requis.</li>
                <% } %>
                <% if (request.getAttribute("email_error") != null) { %>
                <li class="error">L'email est requis.</li>
                <% } %>
                <% if (request.getAttribute("password_error") != null) { %>
                <li class="error"><%= request.getAttribute("password_error") %></li>
                <% } %>
            </ul>
            </fieldset>
            <% } %>


            <input type="text" name="nom" placeholder="Nom" class="input" required />
            <input type="email" name="email" placeholder="Email" class="input" required />
            <input type="password" name="password" placeholder="Mot de passe" class="input" required />

            <button type="submit" class="button sign-in">S'inscrire</button>

            <p class="footer">
                Déjà un compte ?
                <a href="ConnexionServlet" class="link">Se connecter</a>
            </p>
        </form>
    </div>
</div>
</div>
</body>
</html>
