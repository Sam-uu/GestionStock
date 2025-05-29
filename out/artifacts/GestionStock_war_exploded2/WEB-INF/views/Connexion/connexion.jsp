<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/theme2.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<div class="wrapper">
<div class="container">
    <div class="login-box">
        <form class="form" method="post" action="ConnexionServlet">
            <div class="logo"></div>
            <span class="header">Bienvenue !</span>

            <% if (request.getAttribute("errors") != null) { %>
            <fieldset id="error_fieldset" style="border: 2px solid red; padding: 10px;">
                <legend align="left" style="color:red;">Erreurs</legend>
            <ul style="color:red; list-style: disc; margin-left: 20px;">
                <% if (request.getAttribute("email_error") != null) { %>
                <li class="error">L'email est obligatoire.</li>
                <% } %>
                <% if (request.getAttribute("password_error") != null) { %>
                <li class="error">Le mot de passe est obligatoire.</li>
                <% } %>
            </ul>
            </fieldset>
            <% } %>

            <input type="email" name="email" placeholder="Email" class="input" required />
            <input type="password" name="password" placeholder="Mot de passe" class="input" required />
            <button type="submit" class="button sign-in">Se connecter</button>

            <p class="footer">
                Vous n'avez pas de compte ?
                <a href="Inscription" class="link">Créer un compte</a>
                <br />
                <a href="ResetPassword" class="link">Mot de passe oublié ?</a>
            </p>
        </form>
    </div>
</div>
</div>
</body>
</html>

