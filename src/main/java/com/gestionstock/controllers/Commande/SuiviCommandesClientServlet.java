package com.gestionstock.controllers.Commande;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.CommandeClient;
import models.Utilisateur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SuiviCommandesClientServlet")
public class SuiviCommandesClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utilisateur client = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (client == null || !client.getRole().equalsIgnoreCase("client")) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }

        List<CommandeClient> commandes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM commande_client WHERE client_email = ? ORDER BY date_commande DESC")) {

            ps.setString(1, client.getEmail());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CommandeClient c = new CommandeClient();
                c.setId(rs.getInt("id"));
                c.setClientEmail(rs.getString("client_email"));
                c.setProduitId(rs.getInt("produit_id"));
                c.setQuantite(rs.getInt("quantite"));
                c.setStatut(rs.getString("statut"));
                c.setDateCommande(rs.getTimestamp("date_commande"));
                commandes.add(c);
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des commandes", e);
        }

        request.setAttribute("commandes", commandes);
        request.getRequestDispatcher("/WEB-INF/views/Commande/suiviCommandes.jsp").forward(request, response);
    }
}
