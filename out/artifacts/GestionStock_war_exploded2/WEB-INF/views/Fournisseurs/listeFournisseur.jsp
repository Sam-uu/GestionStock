<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, models.Fournisseur" %>
<html>
<head>
    <title>Liste des Fournisseurs</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/test.css" />
</head>
<body>
<div class="wrapper">
    <div class="container">
        <div class="login-box">
            <h2>Fournisseurs enregistrés</h2>

            <!-- ✅ Formulaire de recherche -->
            <form action="<%= request.getContextPath() %>/fournisseurs" method="get" style="margin-bottom: 15px;">
                <label for="idRecherche">🔎 Rechercher Fournisseur par ID :</label>
                <input type="number" name="id" id="idRecherche" class="input" placeholder="Entrer ID..." required />
                <button type="submit" class="button">OK</button>
            </form>

            <!-- ✅ Affichage du fournisseur recherché -->
            <%
                Fournisseur fournisseurRecherche = (Fournisseur) request.getAttribute("fournisseurRecherche");
                if (fournisseurRecherche != null) {
            %>
            <h3>Résultat de la recherche :</h3>
            <table border="1" cellpadding="8" cellspacing="0" style="width: 100%; color: white;">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Adresse</th>
                    <th>Téléphone</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                <tr style="background-color: #444;">
                    <td><%= fournisseurRecherche.getId() %></td>
                    <td><%= fournisseurRecherche.getNom() %></td>
                    <td><%= fournisseurRecherche.getAdresse() %></td>
                    <td><%= fournisseurRecherche.getTelephone() %></td>
                    <td><%= fournisseurRecherche.getEmail() %></td>
                </tr>
                </tbody>
            </table>
            <br>
            <% } %>

            <!-- ✅ Bouton ajouter -->
            <a href="<%= request.getContextPath() %>/fournisseurs/ajouter" class="button">➕ Ajouter un fournisseur</a><br><br>

            <!-- ✅ Tableau général -->
            <table border="1" cellpadding="8" cellspacing="0" style="width: 100%; color: white;">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Adresse</th>
                    <th>Téléphone</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Fournisseur> fournisseurs = (List<Fournisseur>) request.getAttribute("fournisseurs");
                    if (fournisseurs != null && !fournisseurs.isEmpty()) {
                        for (Fournisseur f : fournisseurs) {
                %>
                <tr>
                    <td><%= f.getId() %></td>
                    <td><%= f.getNom() %></td>
                    <td><%= f.getAdresse() %></td>
                    <td><%= f.getTelephone() %></td>
                    <td><%= f.getEmail() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/fournisseurs/modifier?id=<%= f.getId() %>" class="action-link">Modifier</a>
                        <form action="<%= request.getContextPath() %>/fournisseurs/supprimer" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= f.getId() %>"/>
                            <button type="submit" class="action-link" onclick="return confirm('Confirmer la suppression ?')">Supprimer</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="6">Aucun fournisseur enregistré.</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
