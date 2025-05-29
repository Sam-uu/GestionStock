package com.gestionstock.controllers.Commande;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Produit;
import models.Utilisateur;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ValiderCommandeServlet")
public class ValidationCommandeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utilisateur u = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (u == null || !u.getRole().equalsIgnoreCase("client")) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }

        int produitId = Integer.parseInt(request.getParameter("produitId"));
        int quantite = Integer.parseInt(request.getParameter("quantite"));

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            // Vérifier stock
            int stockDisponible = 0;
            try (PreparedStatement psStock = conn.prepareStatement("SELECT quantite FROM produit WHERE id = ?")) {
                psStock.setInt(1, produitId);
                ResultSet rsStock = psStock.executeQuery();

                if (rsStock.next()) {
                    stockDisponible = rsStock.getInt("quantite");
                } else {
                    conn.rollback();
                    forwardWithErreur(request, response, "Produit introuvable.");
                    return;
                }
            }

            if (stockDisponible < quantite) {
                conn.rollback();
                forwardWithErreur(request, response, "Stock insuffisant pour ce produit !");
                return;
            }

            // Créer commande
            int commandeId;
            try (PreparedStatement psCommande = conn.prepareStatement(
                    "INSERT INTO commande (utilisateur_email) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                psCommande.setString(1, u.getEmail());
                psCommande.executeUpdate();
                ResultSet rsCommande = psCommande.getGeneratedKeys();
                if (!rsCommande.next()) throw new SQLException("Erreur création commande.");
                commandeId = rsCommande.getInt(1);
            }

            // Ajouter produit commandé
            try (PreparedStatement psDetails = conn.prepareStatement(
                    "INSERT INTO commande_produit (commande_id, produit_id, quantite) VALUES (?, ?, ?)")) {
                psDetails.setInt(1, commandeId);
                psDetails.setInt(2, produitId);
                psDetails.setInt(3, quantite);
                psDetails.executeUpdate();
            }

            // Mettre à jour stock
            try (PreparedStatement psUpdate = conn.prepareStatement(
                    "UPDATE produit SET quantite = quantite - ? WHERE id = ?")) {
                psUpdate.setInt(1, quantite);
                psUpdate.setInt(2, produitId);
                psUpdate.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            throw new ServletException("Erreur validation commande", e);
        }

        response.sendRedirect(request.getContextPath() + "/ClientCommandeServlet");
    }

    private void forwardWithErreur(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("erreur", message);

        List<Produit> produits = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM produit");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setNom(rs.getString("nom"));
                produit.setDescription(rs.getString("description"));
                produit.setQuantite(rs.getInt("quantite"));
                produit.setSeuilAlerte(rs.getInt("seuil_alerte"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setImageUrl(rs.getString("image"));
                produit.setAlerte(produit.getQuantite() <= produit.getSeuilAlerte());
                produits.add(produit);
            }
        } catch (SQLException e) {
            request.setAttribute("erreur", "Impossible de charger les produits.");
        }

        request.setAttribute("produits", produits);
        request.getRequestDispatcher("/WEB-INF/views/Commande/passerCommande.jsp").forward(request, response);
    }
}
