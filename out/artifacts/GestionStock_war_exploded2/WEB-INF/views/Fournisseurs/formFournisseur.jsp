<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Fournisseur" %>
<%
    Fournisseur fournisseur = (Fournisseur) request.getAttribute("fournisseur");
    boolean isEdit = (fournisseur != null);
%>
<html>
<head>
    <title><%= isEdit ? "Modifier" : "Ajouter" %> un fournisseur</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/test.css" />
</head>
<body>
<div class="wrapper">
    <div class="container">
        <h2><%= isEdit ? "Modifier" : "Ajouter" %> un fournisseur</h2>

        <form method="post"
              action="<%= request.getContextPath() + (isEdit ? "/fournisseurs/modifier" : "/fournisseurs/ajouter") %>">

            <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= fournisseur.getId() %>"/>
            <% } %>

            <label for="nom">Nom du fournisseur :</label>
            <input type="text" id="nom" name="nom" class="input"
                   value="<%= isEdit ? fournisseur.getNom() : "" %>" required />

            <label for="adresse">Adresse :</label>
            <input type="text" id="adresse" name="adresse" class="input"
                   value="<%= isEdit ? fournisseur.getAdresse() : "" %>" required />

            <label for="telephone">Téléphone :</label>
            <input type="text" id="telephone" name="telephone" class="input"
                   value="<%= isEdit ? fournisseur.getTelephone() : "" %>" required />

            <label for="email">Email :</label>
            <input type="email" id="email" name="email" class="input"
                   value="<%= isEdit ? fournisseur.getEmail() : "" %>" required />

            <br/>
            <button type="submit" class="button"><%= isEdit ? "Modifier" : "Ajouter" %></button>
            <a href="<%= request.getContextPath() %>/fournisseurs" class="link">Annuler</a>
        </form>
    </div>
</div>
</body>
</html>
