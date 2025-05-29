package com.gestionstock.controllers.Fournisseurs;

import com.gestionstock.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Fournisseur;
import models.Utilisateur;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/fournisseurs")
public class ListerFournisseursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utilisateur u = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

        if (u == null || (!u.getRole().equalsIgnoreCase("admin") && !u.getRole().equalsIgnoreCase("gestionnaire"))) {
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
            return;
        }

        int idRecherche = -1;
        if (request.getParameter("id") != null) {
            try {
                idRecherche = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException ignored) {}
        }

        List<Fournisseur> fournisseurs = new ArrayList<>();
        Fournisseur fournisseurRecherche = null;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM fournisseurs")) {

            while (rs.next()) {
                Fournisseur f = new Fournisseur();
                f.setId(rs.getInt("id"));
                f.setNom(rs.getString("nom"));
                f.setAdresse(rs.getString("adresse"));
                f.setTelephone(rs.getString("telephone"));
                f.setEmail(rs.getString("email"));

                if (f.getId() == idRecherche) {
                    fournisseurRecherche = f;
                } else {
                    fournisseurs.add(f);
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des fournisseurs", e);
        }

        request.setAttribute("fournisseurRecherche", fournisseurRecherche);
        request.setAttribute("fournisseurs", fournisseurs);
        request.getRequestDispatcher("/WEB-INF/views/Fournisseurs/listeFournisseur.jsp")
                .forward(request, response);
    }
}
