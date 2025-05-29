package com.gestionstock.controllers.Connextion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import com.gestionstock.doa.UtilisateurDAO;

@WebServlet("/Inscription")
public class InscriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affiche la page inscription.jsp
        request.getRequestDispatcher("/WEB-INF/views/Connexion/inscription.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération des champs du formulaire
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motdepasse");

        // Rôle fixé automatiquement
        String role = "client";

        // Création du DAO
        UtilisateurDAO dao = new UtilisateurDAO();

        if (dao.utilisateurExiste(email)) {
            // L'email existe déjà : afficher un message d’erreur sur la même page
            request.setAttribute("message", "Un utilisateur avec cet email existe déjà.");
            request.getRequestDispatcher("/WEB-INF/views/Connexion/inscription.jsp")
                    .forward(request, response);
        } else {

            // 🔽 🔽 🔽 AJOUTE CETTE LIGNE DE LOG ICI 🔽 🔽 🔽
            System.out.println(">>> INSCRIPTION OK : " + nom + " - " + email);

            // Inscription du nouvel utilisateur
            dao.ajouterUtilisateur(nom, email, motDePasse, role);

            // Redirection vers la page de connexion
            response.sendRedirect(request.getContextPath() + "/ConnexionServlet");
        }
    }
}
