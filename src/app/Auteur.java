package app;
public class Auteur{

    private String idAut;
    private String nomAuteur;
    private int anneeNaissance;
    private int anneeMort;
    private boolean estmort;


    public Auteur(String idAut,String nom,int anneeN,int anneeM){
        this.idAut=idAut;
        this.nomAuteur=nom;
        this.anneeMort=anneeM;
        this.anneeNaissance=anneeN;
    }

    public String getIdAuteur(){return this.idAut;}
    public String getNomAuteur(){return this.nomAuteur;}
    public int getAnneeNaissance(){return this.anneeNaissance;}
    public int getAnneeMort(){return this.anneeMort;}
    public boolean getEstMort(){return this.estmort;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Auteur auteur = (Auteur) obj;
        return idAut.equals(auteur.idAut) &&
               estmort == auteur.estmort &&
               nomAuteur.equals(auteur.nomAuteur) &&
               anneeNaissance == auteur.anneeNaissance &&
               anneeMort == (auteur.anneeMort);
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