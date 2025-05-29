<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.gestionstock.doa.ProduitDAO" %>
<%@ page import="com.gestionstock.doa.FournisseurDAO" %>
<%@ page import="com.gestionstock.doa.CommandeClientDAO" %>
<%
    int totalProduits = ProduitDAO.getTotalProduits();
    int stockFaible = ProduitDAO.getProduitsStockFaible();
    int totalFournisseurs = FournisseurDAO.getTotalFournisseurs();
    int totalCommandes = CommandeClientDAO.getTotalCommandes();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Tableau de Bord</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/theme2.css" />
    <style>
        body {
            background-color: #121212;
            color: #fff;
            font-family: Arial, sans-serif;
        }
        .dashboard {
            max-width: 800px;
            margin: 50px auto;
            padding: 30px;
            background-color: #1e1e1e;
            border-radius: 20px;
            box-shadow: 0 0 15px rgba(255, 0, 0, 0.2);
            text-align: center;
        }
        .card {
            background-color: #272727;
            padding: 20px;
            margin: 15px;
            border-radius: 15px;
            font-size: 20px;
        }
        .card.alert {
            background-color: #8B0000;
            color: #fff;
            font-weight: bold;
        }
        h1 {
            color: #FF4444;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<div class="dashboard">
    <h1>📊 Tableau de Bord</h1>
    <div class="card">🛒 Produits en stock : <%= totalProduits %></div>
    <div class="card alert">⚠️ Stock faible : <%= stockFaible %></div>
    <div class="card">🚚 Fournisseurs enregistrés : <%= totalFournisseurs %></div>
    <div class="card">📦 Commandes clients : <%= totalCommandes %></div>
</div>
</body>
</html>
