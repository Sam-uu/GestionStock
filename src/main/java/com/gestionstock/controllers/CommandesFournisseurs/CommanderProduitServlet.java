package com.gestionstock.controllers.CommandesFournisseurs;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Produit;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet("/produits/commander")
public class CommanderProduitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProduit = Integer.parseInt(request.getParameter("idProduit"));
        Produit produit = new Produit();
        String fournisseurNom = "";

        try (Connection conn = DBUtil.getConnection()) {
            // Charger produit
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM produit WHERE id = ?");
            ps.setInt(1, idProduit);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                produit.setId(rs.getInt("id"));
                produit.setNom(rs.getString("nom"));
            }

            // Charger fournisseur associé
            PreparedStatement psF = conn.prepareStatement(
                    "SELECT f.nom FROM fournisseurs f " +
                            "JOIN produit_fournisseur pf ON f.id = pf.fournisseur_id " +
                            "WHERE pf.produit_id = ? LIMIT 1"
            );
            psF.setInt(1, idProduit);
            ResultSet rsF = psF.executeQuery();
            if (rsF.next()) {
                fournisseurNom = rsF.getString("nom");
            } else {
                fournisseurNom = "Aucun fournisseur associé";
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur chargement produit/fournisseur", e);
        }

        request.setAttribute("produit", produit);
        request.setAttribute("fournisseurNom", fournisseurNom);
        request.getRequestDispatcher("/WEB-INF/views/Produits/commanderProduit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProduit = Integer.parseInt(request.getParameter("idProduit"));
        int quantite = Integer.parseInt(request.getParameter("quantite"));

        try (Connection conn = DBUtil.getConnection()) {
            // Trouver fournisseur associé
            int fournisseurId = -1;
            PreparedStatement psF = conn.prepareStatement(
                    "SELECT fournisseur_id FROM produit_fournisseur WHERE produit_id = ? LIMIT 1"
            );
            psF.setInt(1, idProduit);
            ResultSet rsF = psF.executeQuery();
            if (rsF.next()) {
                fournisseurId = rsF.getInt("fournisseur_id");
            }

            if (fournisseurId != -1) {
                // Créer commande fournisseur
                PreparedStatement psCommande = conn.prepareStatement(
                        "INSERT INTO commande_fournisseur (fournisseur_id, date_commande, statut) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                psCommande.setInt(1, fournisseurId);
                psCommande.setDate(2, Date.valueOf(LocalDate.now()));
                psCommande.setString(3, "en_attente");
                psCommande.executeUpdate();

                ResultSet rsKey = psCommande.getGeneratedKeys();
                int commandeId = -1;
                if (rsKey.next()) {
                    commandeId = rsKey.getInt(1);
                }

                // Ajouter ligne commande
                if (commandeId != -1) {
                    PreparedStatement psLigne = conn.prepareStatement(
                            "INSERT INTO ligne_commande_fournisseur (commande_id, produit_id, quantite) VALUES (?, ?, ?)"
                    );
                    psLigne.setInt(1, commandeId);
                    psLigne.setInt(2, idProduit);
                    psLigne.setInt(3, quantite);
                    psLigne.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur création commande fournisseur", e);
        }

        response.sendRedirect(request.getContextPath() + "/produits");
    }
}

