package com.gestionstock.controllers.Produits;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Utilisateur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5,             // 5MB
        maxRequestSize = 1024 * 1024 * 10)         // 10MB
@WebServlet("/produits/ajouter")

public class AjouterProduitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 🔐 Vérification de la session et du rôle
        HttpSession session = request.getSession(false);
        Utilisateur u = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (u == null || (!u.getRole().equalsIgnoreCase("admin") && !u.getRole().equalsIgnoreCase("gestionnaire"))) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/Produits/formProduit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        int seuil = Integer.parseInt(request.getParameter("seuil"));
        double prix = Double.parseDouble(request.getParameter("prix"));
        Part imagePart = request.getPart("image");
        // ✅ Vérification que le seuil est inférieur à la quantité
        if (seuil >= quantite) {
            request.setAttribute("erreur", "Le seuil d'alerte doit être inférieur à la quantité.");
            request.setAttribute("nom", nom);
            request.setAttribute("description", description);
            request.setAttribute("quantite", quantite);
            request.setAttribute("seuil", seuil);
            request.setAttribute("prix", prix);
            request.getRequestDispatcher("/WEB-INF/views/Produits/formProduit.jsp").forward(request, response);
            return;
        }
        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString(); // nom du fichier
        String uploadPath = request.getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        String imagePath = uploadPath + File.separator + fileName;

        imagePart.write(imagePath);

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO produit (nom, description, quantite, seuil_alerte, prix,image) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, nom);
            ps.setString(2, description);
            ps.setInt(3, quantite);
            ps.setInt(4, seuil);
            ps.setDouble(5, prix);
            ps.setString(6, "uploads/" + fileName);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'ajout du produit", e);
        }

        response.sendRedirect(request.getContextPath() + "/produits");
    }
}

