package com.gestionstock.controllers.CommandesFournisseurs;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.CommandeFournisseur;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/commandes-fournisseurs/historique")
public class HistoriqueCommandesFournisseursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CommandeFournisseur> commandes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT c.id, c.fournisseur_id, c.date_commande, c.statut, f.nom AS fournisseurNom " +
                    "FROM commande_fournisseur c " +
                    "JOIN fournisseurs f ON c.fournisseur_id = f.id " +
                    "ORDER BY c.date_commande DESC";

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    CommandeFournisseur commande = new CommandeFournisseur();
                    commande.setId(rs.getInt("id"));
                    commande.setFournisseurId(rs.getInt("fournisseur_id"));
                    commande.setDateCommande(rs.getTimestamp("date_commande"));
                    commande.setStatut(rs.getString("statut"));

                    commandes.add(commande);
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur chargement historique commandes fournisseurs", e);
        }

        request.setAttribute("commandes", commandes);
        request.getRequestDispatcher("/WEB-INF/views/CommandesFournisseurs/historiqueCommandes.jsp")
                .forward(request, response);
    }
}
