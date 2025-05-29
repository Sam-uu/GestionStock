package models;

public class LigneCommandeClient {
    private int id;
    private int commandeId;
    private int produitId;
    private int quantite;

    public LigneCommandeClient() {}

    public LigneCommandeClient(int id, int commandeId, int produitId, int quantite) {
        this.id = id;
        this.commandeId = commandeId;
        this.produitId = produitId;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "LigneCommandeClient{" +
                "id=" + id +
                ", commandeId=" + commandeId +
                ", produitId=" + produitId +
                ", quantite=" + quantite +
                '}';
    }
}
