package main;

public class Vendeur extends User{
    private String prenom;
    private Magasin mag;
    public Vendeur(int idve, String email, String nom, String mdp, String role, String prenom, Magasin mag)
    {
        super(idve, email,nom, mdp, role);
        this.prenom = prenom;
        this.mag = mag;
    }

    public String getPrenom() {return this.prenom;}
    public Magasin getMagasin() {return this.mag;}
    public int getIdMag() {return this.mag.getIdmag();}

    public void ajouterLivre(){
        
    }

    public void updateStock() {}

    public void disponibilites() {}

    public void passerCommande() {}

    public void Transfer() {}

}
