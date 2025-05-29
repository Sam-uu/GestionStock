package com.gestionstock.controllers.Fournisseurs;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Produit;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/fournisseurs/ajouter")
public class AjouterFournisseurServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ✅ Pas besoin de charger les produits (plus de checkboxes)
        request.getRequestDispatcher("/WEB-INF/views/Fournisseurs/ajouterFournisseur.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");

        try (Connection conn = DBUtil.getConnection()) {
            // ✅ Vérifier si fournisseur existe déjà
            PreparedStatement psCheck = conn.prepareStatement("SELECT id FROM fournisseurs WHERE id = ?");
            psCheck.setInt(1, id);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                request.setAttribute("error", "⚠ Le fournisseur avec l’ID " + id + " existe déjà.");
                request.getRequestDispatcher("/WEB-INF/views/Fournisseurs/ajouterFournisseur.jsp").forward(request, response);
                return;
            }

            // ✅ Insérer fournisseur
            PreparedStatement psInsert = conn.prepareStatement(
                    "INSERT INTO fournisseurs (id, nom, adresse, telephone, email) VALUES (?, ?, ?, ?, ?)"
            );
            psInsert.setInt(1, id);
            psInsert.setString(2, nom);
            psInsert.setString(3, adresse);
            psInsert.setString(4, telephone);
            psInsert.setString(5, email);
            psInsert.executeUpdate();

            // ✅ Récupérer les produits saisis manuellement
            String[] produits = request.getParameterValues("nomProduit");
            if (produits != null) {
                for (String produitNom : produits) {
                    if (produitNom != null && !produitNom.trim().isEmpty()) {
                        // 🔎 Chercher produit par nom
                        PreparedStatement psProduit = conn.prepareStatement(
                                "SELECT id FROM produit WHERE LOWER(nom) = LOWER(?) LIMIT 1"
                        );
                        psProduit.setString(1, produitNom.trim());
                        ResultSet rsProduit = psProduit.executeQuery();

                        if (rsProduit.next()) {
                            int produitId = rsProduit.getInt("id");
                            // ✅ Insérer lien dans produit_fournisseur
                            PreparedStatement psLink = conn.prepareStatement(
                                    "INSERT INTO produit_fournisseur (produit_id, fournisseur_id) VALUES (?, ?)"
                            );
                            psLink.setInt(1, produitId);
                            psLink.setInt(2, id);
                            psLink.executeUpdate();
                        }
                    }
                }
            }

            // ✅ Redirection finale
            response.sendRedirect(request.getContextPath() + "/fournisseurs");

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'ajout du fournisseur", e);
        }
    }
}
