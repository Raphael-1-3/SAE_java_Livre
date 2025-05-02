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

}