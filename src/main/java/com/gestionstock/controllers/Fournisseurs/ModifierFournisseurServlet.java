package com.gestionstock.controllers.Fournisseurs;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Fournisseur;
import models.Utilisateur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/fournisseurs/modifier")
public class ModifierFournisseurServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 🔐 Vérification session et rôle
        HttpSession session = request.getSession(false);
        Utilisateur u = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (u == null || (!u.getRole().equalsIgnoreCase("admin") && !u.getRole().equalsIgnoreCase("gestionnaire"))) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM fournisseurs WHERE id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Fournisseur f = new Fournisseur();
                f.setId(rs.getInt("id"));
                f.setNom(rs.getString("nom"));
                f.setAdresse(rs.getString("adresse"));
                f.setTelephone(rs.getString("telephone"));
                f.setEmail(rs.getString("email"));
                request.setAttribute("fournisseur", f);
                request.getRequestDispatcher("/WEB-INF/views/Fournisseurs/formFournisseur.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/fournisseurs");
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement du fournisseur", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE fournisseurs SET nom=?, adresse=?, telephone=?, email=? WHERE id=?")) {

            ps.setString(1, nom);
            ps.setString(2, adresse);
            ps.setString(3, telephone);
            ps.setString(4, email);
            ps.setInt(5, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la mise à jour du fournisseur", e);
        }

        response.sendRedirect(request.getContextPath() + "/fournisseurs");
    }
}
