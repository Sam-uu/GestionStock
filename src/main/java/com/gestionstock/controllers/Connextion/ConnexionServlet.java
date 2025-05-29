package com.gestionstock.controllers.Connextion;
import com.gestionstock.utils.DBUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Utilisateur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ConnexionServlet")
public class ConnexionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher
                ("WEB-INF/views/Connexion/connexion.jsp");
        view.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ConnexionServlet appelé");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("email_error", true);
            request.setAttribute("errors", true);
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("password_error", true);
            request.setAttribute("errors", true);
        }
        if (request.getAttribute("errors") != null && (Boolean) request.getAttribute("errors")) {
            request.getRequestDispatcher("WEB-INF/views/Connexion/connexion.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            Utilisateur u = new Utilisateur();
            u.setEmail(email);
            u.setMotDePasse(password);

            HttpSession session = request.getSession();

            if (rs.next()) {
                u.setNom(rs.getString("nom"));
                u.setRole(rs.getString("role")); // admin ou gestionnaire
            } else {
                u.setNom("Client");
                u.setRole("client");
            }

            // 🔍 Ajout du log ici
            System.out.println("Connexion OK : " + email + " (" + u.getRole() + ")");

            session.setAttribute("utilisateurConnecte", u);

            switch (u.getRole()) {
                case "admin":
                    response.sendRedirect(request.getContextPath() + "/AdminPage");
                    break;
                case "gestionnaire":
                    response.sendRedirect(request.getContextPath() + "/GestionnairePage");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/ClientPage");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur serveur : " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/views/Connexion/connexion.jsp").forward(request, response);
        }
    }
}