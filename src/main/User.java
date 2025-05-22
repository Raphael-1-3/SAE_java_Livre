package main;

public abstract class User {
    protected int id;
    protected String prenom;
    protected String nom;
    protected Magasin magasin;

    public User(int id, String prenom, String nom)
    {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    public String getPrenom(){return this.prenom;}
    public String getNom(){return this.nom;}
    public int getId(){return this.id;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.id == user.getId() &&
            this.prenom.equals(user.getPrenom()) &&
            this.nom.equals(user.getNom());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, prenom, nom);
    }
}
