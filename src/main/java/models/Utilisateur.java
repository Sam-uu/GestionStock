package models;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private String role; // "admin", "gestionnaire", "client"
    // Constructeur vide
    public Utilisateur() {}

    // Constructeur avec paramètres
    public Utilisateur(int id, String nom, String email, String motDePasse, String role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public String getNom() {return nom;}

    public void setNom(String nom) {this.nom = nom;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getMotDePasse() {return motDePasse;}

    public void setMotDePasse(String motDePasse) {this.motDePasse = motDePasse;}

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
