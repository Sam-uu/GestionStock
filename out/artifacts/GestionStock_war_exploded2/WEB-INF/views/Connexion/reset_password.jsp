<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Réinitialiser le mot de passe</title>
    <link rel="stylesheet" href="resources/css/theme2.css" />
</head>
<body>
<div class="wrapper">
<div class="container">
    <div class="login-box">
        <form class="form" method="post" action="ResetPassword">
            <div class="logo"></div>
            <span class="header">Réinitialiser le mot de passe</span>

            <% if (request.getAttribute("errors") != null) { %>
            <fieldset id="error_fieldset" style="border: 2px solid red; padding: 10px;">
                <legend align="left" style="color:red;">Erreurs</legend>
            <ul style="color:red; list-style: disc; margin-left: 20px;">
                <% if (request.getAttribute("email_error") != null) { %>
                <li class="error">L'adresse email est obligatoire.</li>
                <% } %>
            </ul>
            <% } %>


            <input type="email" name="email" placeholder="Email" class="input" required />
            <button type="submit" class="button sign-in">Envoyer un lien</button>

            <p class="footer">
                Retour à la <a href="ConnexionServlet" class="link">connexion</a>
            </p>
        </form>
    </div>
</div>
</div>
</body>
</html>

