package com.gestionstock.controllers.Connextion;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Produit;
import models.Utilisateur;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ClientPage")
public class ClientPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utilisateur u = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (u == null || !u.getRole().equalsIgnoreCase("client")) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }
        List<Produit> produits = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM produit")) {

            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setQuantite(rs.getInt("quantite"));
                p.setSeuilAlerte(rs.getInt("seuil_alerte"));
                p.setPrix(rs.getDouble("prix"));
                p.setImageUrl(rs.getString("image"));
                p.setAlerte(p.getQuantite() <= p.getSeuilAlerte());  // ✅ calcul alerte
                produits.add(p);
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des produits", e);
        }

        request.setAttribute("produits", produits);
        request.getRequestDispatcher("/WEB-INF/views/Commande/passerCommande.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utilisateur u = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (u == null || !u.getRole().equalsIgnoreCase("client")) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }

        int produitId = Integer.parseInt(request.getParameter("produit_id"));
        int quantite = Integer.parseInt(request.getParameter("quantite"));

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            // 1. Créer la commande
            PreparedStatement psCommande = conn.prepareStatement(
                    "INSERT INTO commande (utilisateur_email) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            psCommande.setString(1, u.getEmail());
            psCommande.executeUpdate();

            ResultSet rs = psCommande.getGeneratedKeys();
            rs.next();
            int commandeId = rs.getInt(1);

            // 2. Ajouter le produit commandé
            PreparedStatement psDetails = conn.prepareStatement(
                    "INSERT INTO commande_produit (commande_id, produit_id, quantite) VALUES (?, ?, ?)");
            psDetails.setInt(1, commandeId);
            psDetails.setInt(2, produitId);
            psDetails.setInt(3, quantite);
            psDetails.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l’enregistrement de la commande", e);
        }

        response.sendRedirect(request.getContextPath() + "/ClientCommandeServlet");
    }
}
