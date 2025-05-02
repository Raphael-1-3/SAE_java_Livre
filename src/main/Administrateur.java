package main;

public class Administrateur extends Vendeur {
    public Administrateur (int id, String prenom, String nom, Magasin mag)
    {
        super(id, prenom, nom, mag);
    }

    public void creerVendeur() {}

    public void ajouterLibrairie() {}

    public void gererStock() {}

    public void panneauBord() {}
    
    public void faireFacture() {}

    public void choisirMagasin() {}

    public void StatsVente() {}
}
