package main;

public class Administrateur extends Vendeur {
    public Administrateur(int id, String email, String nom, String motDePasse, String role, String prenom, Magasin mag) {
        super(id, email, nom, motDePasse, role, prenom, mag);
    }

    public void creerVendeur() {}

    public void ajouterLibrairie() {}

    public void gererStock() {}

    public void panneauBord() {}
    
    public void faireFacture() {}

    public void choisirMagasin() {}

    public void StatsVente() {}
}
