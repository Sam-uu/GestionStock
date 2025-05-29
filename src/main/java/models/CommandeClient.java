package models;
import java.sql.Timestamp;
import java.util.Date;

public class CommandeClient {
    private int id;
    private String clientEmail;
    private int produitId;
    private int quantite;
    private String statut;
    private Timestamp dateCommande;

    public CommandeClient() {}

    public CommandeClient(int id, String clientEmail, int produitId, int quantite, String statut, Timestamp dateCommande) {
        this.id = id;
        this.clientEmail = clientEmail;
        this.produitId = produitId;
        this.quantite = quantite;
        this.statut = statut;
        this.dateCommande = dateCommande;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getClientEmail() {return clientEmail;}

    public void setClientEmail(String clientEmail) {this.clientEmail = clientEmail;}

    public int getProduitId() {return produitId;}

    public void setProduitId(int produitId) {this.produitId = produitId;}

    public int getQuantite() {return quantite;}

    public void setQuantite(int quantite) {this.quantite = quantite;}

    public String getStatut() {return statut;}

    public void setStatut(String statut) {this.statut = statut;}

    public Timestamp getDateCommande() {return dateCommande;}

    public void setDateCommande(Timestamp dateCommande) {this.dateCommande = dateCommande;}

    @Override
    public String toString() {
        return "CommandeClient{" +
                "id=" + id +
                ", clientEmail='" + clientEmail + '\'' +
                ", produitId=" + produitId +
                ", quantite=" + quantite +
                ", statut='" + statut + '\'' +
                ", dateCommande=" + dateCommande +
                '}';
    }
}
