package com.gestionstock.controllers.Connextion;
import com.gestionstock.utils.DBUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ResetPassword")
public class ResetPasswordServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher
                ("WEB-INF/views/Connexion/reset_password.jsp");
        view.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("email_error", true);
            request.setAttribute("errors", true);
            request.getRequestDispatcher("WEB-INF/views/Connexion/reset_password.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM utilisateur WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                request.setAttribute("message", "Aucun compte administrateur ou gestionnaire trouvé avec cet email.");
                request.getRequestDispatcher("WEB-INF/views/Connexion/reset_password.jsp").forward(request, response);
                return;
            }

            // Simuler la réinitialisation (à remplacer par formulaire de nouveau mot de passe)
            PreparedStatement update = conn.prepareStatement("UPDATE utilisateur SET mot_de_passe = ? WHERE email = ?");
            update.setString(1, "NouveauPass123");
            update.setString(2, email);
            update.executeUpdate();

            request.setAttribute("message", "Mot de passe réinitialisé !");
            request.getRequestDispatcher("WEB-INF/views/Connexion/reset_password.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur serveur : " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/views/Connexion/reset_password.jsp").forward(request, response);
        }
    }
}
