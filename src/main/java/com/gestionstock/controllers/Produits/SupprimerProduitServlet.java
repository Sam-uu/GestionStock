package com.gestionstock.controllers.Produits;

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
import java.sql.SQLException;

@WebServlet("/produits/supprimer")
public class SupprimerProduitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 🔐 Vérification de la session et du rôle
        HttpSession session = request.getSession(false);
        Utilisateur u = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (u == null || (!u.getRole().equalsIgnoreCase("admin") && !u.getRole().equalsIgnoreCase("gestionnaire"))) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM produit WHERE id = ?")) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la suppression du produit", e);
        }

        response.sendRedirect(request.getContextPath() + "/produits");
    }
}