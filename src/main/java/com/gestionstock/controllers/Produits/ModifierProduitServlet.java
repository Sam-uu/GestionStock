package com.gestionstock.controllers.Produits;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Produit;
import models.Utilisateur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
@WebServlet("/produits/modifier")
public class ModifierProduitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utilisateur u = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (u == null || (!u.getRole().equalsIgnoreCase("admin") && !u.getRole().equalsIgnoreCase("gestionnaire"))) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM produit WHERE id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setNom(rs.getString("nom"));
                produit.setDescription(rs.getString("description"));
                produit.setQuantite(rs.getInt("quantite"));
                produit.setSeuilAlerte(rs.getInt("seuil_alerte"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setImageUrl(rs.getString("image"));
                request.setAttribute("produit", produit);
                request.getRequestDispatcher("/WEB-INF/views/Produits/formProduit.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/produits");
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement du produit", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        int seuil = Integer.parseInt(request.getParameter("seuil"));
        double prix = Double.parseDouble(request.getParameter("prix"));

        String imagePath = null;
        Part imagePart = request.getPart("image");
        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = request.getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            imagePath = "uploads/" + fileName;
            imagePart.write(uploadPath + File.separator + fileName);
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql;
            if (imagePath != null) {
                sql = "UPDATE produit SET nom=?, description=?, quantite=?, seuil_alerte=?, prix=?, image=? WHERE id=?";
            } else {
                sql = "UPDATE produit SET nom=?, description=?, quantite=?, seuil_alerte=?, prix=? WHERE id=?";
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nom);
                ps.setString(2, description);
                ps.setInt(3, quantite);
                ps.setInt(4, seuil);
                ps.setDouble(5, prix);

                if (imagePath != null) {
                    ps.setString(6, imagePath);
                    ps.setInt(7, id);
                } else {
                    ps.setInt(6, id);
                }

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la mise à jour du produit", e);
        }

        response.sendRedirect(request.getContextPath() + "/produits");
    }
}
