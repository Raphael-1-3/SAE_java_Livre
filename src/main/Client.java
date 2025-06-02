package main;

import java.util.ArrayList;
import java.util.List;

public class Client extends User {
    private char modeReception;
    private int codePostal;
    private String villeCli;
    private String adresseCli;
    private List<Commande> commandes;
    private ActionBD bd;

    public Client(int id, String nom, String prenom, int codePostal, String villeCli, String adresseCli)
    {
        super(id, prenom, nom);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = new ArrayList<>();

    }

    public Client(int id, String nom, String prenom, int codePostal, String villeCli, String adresseCli, List<Commande> commandes)
    {
        super(id, prenom, nom);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = commandes;
    }

    public void choisirMagasin() {}

    public void consulterCatalogue() {}

    public void avoirRecommandations() {}
    
    public int getCodePostal() {
        return codePostal;
    }

    public String getVilleCli() {
        return villeCli;
    }

    public String getAdresseCli() {
        return adresseCli;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return this.modeReception == client.modeReception &&
                this.codePostal == client.getCodePostal() &&
                this.villeCli.equals(client.getVilleCli()) &&
                this.adresseCli.equals(client.getAdresseCli());
    }

    

    @Override
    public int hashCode() {
        return this.id * 3131  + this.nom.hashCode() + this.prenom.hashCode();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + this.id +
                ", nom='" + this.nom + '\'' +
                ", prenom='" + this.prenom + '\'' +
                ", codePostal=" + this.codePostal +
                ", villeCli='" + this.villeCli + '\'' +
                ", adresseCli='" + this.adresseCli + '\'' +
                '}';
    }
}
