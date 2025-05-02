package main;

public class Client extends User {
    private char modeReception;
    private int codePostal;
    private String villeCli;
    private String adresseCli;

    public Client(int id, String prenom, String nom, Magasin magasin, int codePostal, String villeCli, String adresseCli)
    {
        super(id, prenom, nom, magasin);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
    }

    public void faireCommande(){}

    public void changerModeReception() {}

    public void choisirMagasin() {}

    public void consulterCatalogue() {}

    public void avoirRecommandations() {}
}
