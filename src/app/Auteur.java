package app;
public class Auteur{

    private int idAut;
    private String nomAuteur;
    private String anneeNaissance;
    private String anneeMort;
    private boolean estmort;


    public Auteur(int idAut,String nom,String anneeN,String anneeM){
        this.idAut=idAut;
        this.nomAuteur=nom;
        this.anneeMort=anneeM;
        this.anneeNaissance=anneeN;
    }

    public int getIdAuteur(){return this.idAut;}
    public String getNomAuteur(){return this.nomAuteur;}
    public String getAnneeNaissance(){return this.anneeNaissance;}
    public String getAnneeMort(){return this.anneeMort;}
    public boolean getEstMort(){return this.estmort;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Auteur auteur = (Auteur) obj;
        return idAut == auteur.idAut &&
               estmort == auteur.estmort &&
               nomAuteur.equals(auteur.nomAuteur) &&
               anneeNaissance.equals(auteur.anneeNaissance) &&
               anneeMort.equals(auteur.anneeMort);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(idAut, nomAuteur, anneeNaissance, anneeMort, estmort);
    }

    @Override
    public String toString() {
        return "Auteur{" +
                "idAut=" + idAut +
                ", nomAuteur='" + nomAuteur + '\'' +
                ", anneeNaissance='" + anneeNaissance + '\'' +
                ", anneeMort='" + anneeMort + '\'' +
                ", estmort=" + estmort +
                '}';
    }

}