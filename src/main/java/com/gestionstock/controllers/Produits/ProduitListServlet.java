package com.gestionstock.controllers.Produits;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Produit;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/produits")
public class ProduitListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Produit> produits = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT p.*, pf.fournisseur_id AS pf_fournisseur_id, f.nom AS fournisseur_nom " +
                             "FROM produit p " +
                             "LEFT JOIN produit_fournisseur pf ON p.id = pf.produit_id " +
                             "LEFT JOIN fournisseurs f ON pf.fournisseur_id = f.id"
             );
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

                // Fournisseur
                produit.setFournisseurId(rs.getInt("pf_fournisseur_id"));  // ✅ correction
                produit.setFournisseurNom(rs.getString("fournisseur_nom"));

                produits.add(produit);
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des produits", e);
        }

        request.setAttribute("produits", produits);
        request.getRequestDispatcher("/WEB-INF/views/Produits/listeProduits.jsp").forward(request, response);
    }
}
