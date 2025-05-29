package com.gestionstock.controllers.Commande;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Utilisateur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ClientCommandeServlet")
public class ClientCommandeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utilisateur u = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (u == null || !u.getRole().equalsIgnoreCase("client")) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }

        List<Map<String, Object>> commandes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT c.id AS commande_id, c.date_commande, c.statut, p.nom, cp.quantite " +
                             "FROM commande c " +
                             "JOIN commande_produit cp ON c.id = cp.commande_id " +
                             "JOIN produit p ON cp.produit_id = p.id " +
                             "WHERE c.utilisateur_email = ? " +
                             "ORDER BY c.date_commande DESC")) {

            ps.setString(1, u.getEmail());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> commande = new HashMap<>();
                commande.put("id", rs.getInt("commande_id"));
                commande.put("date", rs.getTimestamp("date_commande"));
                commande.put("statut", rs.getString("statut"));
                commande.put("produit", rs.getString("nom"));
                commande.put("quantite", rs.getInt("quantite"));
                commandes.add(commande);
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur chargement commandes client", e);
        }

        request.setAttribute("commandes", commandes);
        request.getRequestDispatcher("WEB-INF/views/Commande/commandesClient.jsp").forward(request, response);
    }
}
