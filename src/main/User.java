package main;

public abstract class User {
    protected int id;
    protected String prenom;
    protected String nom;
    protected Magasin magasin;

    public User(int id, String prenom, String nom, Magasin mag)
    {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.magasin = mag;
    }

}
