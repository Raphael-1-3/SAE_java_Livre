package app;
public class Administrateur extends Vendeur {
    public Administrateur(int id, String email, String nom, String motDePasse, String role, String prenom, Magasin mag) {
        super(id, email, nom, motDePasse, role, prenom, mag);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public void creerVendeur() {}

    public void ajouterLibrairie() {}

    public void gererStock() {}

    public void panneauBord() {}
    
    public void faireFacture() {}

    public void choisirMagasin() {}

    public void StatsVente() {}
}
