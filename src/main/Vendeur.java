package main;

public class Vendeur extends User{
    private Magasin magasin;
    public Vendeur(int id , String prenom, String nom, Magasin mag)
    {
        super(id, prenom, nom);
        this.magasin = mag;
    }

    public void ajouterLivre(){
        
    }

    public void updateStock() {}

    public void disponibilites() {}

    public void passerCommande() {}

    public void Transfer() {}

}
