package models;

import java.util.Date;

public class CommandeFournisseur {
    private int id;
    private int fournisseurId;
    private Date dateCommande;
    private String statut;

    public CommandeFournisseur() {}

    public CommandeFournisseur(int id, int fournisseurId, Date dateCommande, String statut) {
        this.id = id;
        this.fournisseurId = fournisseurId;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(int fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "CommandeFournisseur{" +
                "id=" + id +
                ", fournisseurId=" + fournisseurId +
                ", dateCommande=" + dateCommande +
                ", statut='" + statut + '\'' +
                '}';
    }
}
