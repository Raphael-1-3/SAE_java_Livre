package main;

public abstract class User {
    protected int idu;
    protected String email;
    protected String nom;
    protected String motDePasse;
    protected String role;

    public User(int idu, String email, String nom, String mdp, String role)
    {
        this.idu = idu;
        this.email = email;
        this.nom = nom;
        this.motDePasse = mdp;
        this.role = role;
    }

    public String getEmail(){return this.email;}
    public String getNom(){return this.nom;}
    public int getId(){return this.idu;}
    public String getMdp() {return this.motDePasse;}
    public String getRole() {return this.role;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.idu == user.getId() &&
            this.email.equals(user.getEmail()) &&
            this.nom.equals(user.getNom());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(idu, email, nom);
    }
}
