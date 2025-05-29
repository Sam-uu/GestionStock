<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Produit" %>
<%@ page import="java.util.List" %>
<%
  List<Produit> produits = (List<Produit>) request.getAttribute("produits");
%>
<html>
<head>
  <title>Passer une commande</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/test.css">
  <script>
    function toggleForm(id) {
      // Masquer tous les formulaires
      document.querySelectorAll(".quantity-form").forEach(f => f.style.display = "none");

      // Afficher le formulaire sélectionné
      const form = document.getElementById("form-" + id);
      if (form) {
        form.style.display = "block";
      }

      // Faire défiler vers la carte du produit
      const card = document.getElementById("product-" + id);
      if (card) {
        setTimeout(() => {
          card.scrollIntoView({ behavior: "smooth", block: "start" });
        }, 100);
      }
    }
  </script>
</head>
<body>
<div class="wrapper">
  <div class="container login-box">
    <h2>Passer une commande</h2>

    <% if (request.getAttribute("erreur") != null) { %>
    <p style="color: red; font-weight: bold; margin-bottom: 15px;">
      ⚠ <%= request.getAttribute("erreur") %>
    </p>
    <% } %>

    <div class="product-grid">
      <% if (produits != null && !produits.isEmpty()) {
        for (Produit p : produits) { %>
      <div class="product-card" id="product-<%= p.getId() %>">
        <strong><%= p.getNom() %></strong><br/>
        <button onclick="toggleForm(<%= p.getId() %>)" class="button-link">Commander</button><br/><br/>

        <% if (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) { %>
        <img src="<%= request.getContextPath() %>/<%= p.getImageUrl() %>" alt="<%= p.getNom() %>" width="100" height="100" />
        <% } else { %>
        <span style="color:gray;">Pas d'image</span>
        <% } %>

        <form id="form-<%= p.getId() %>" class="quantity-form" method="post" action="ValiderCommandeServlet" style="display:none;">
          <input type="hidden" name="produitId" value="<%= p.getId() %>" />
          <input type="number" name="quantite" min="1" class="input" placeholder="Quantité" required />
          <button type="submit" class="button">Valider</button>
        </form>
      </div>
      <% }
      } else { %>
      <p>Aucun produit disponible.</p>
      <% } %>
    </div>
  </div>
</div>
</body>
</html>





