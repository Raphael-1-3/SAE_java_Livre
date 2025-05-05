package main;

public class Livre {

    private int isbn;
    private String titre;
    private int nbpages;
    private String datepubli;
    private double prix;

    public Livre(int isbn,String titre, int nbpages, String datepubli, double prix){
        this.isbn=isbn;
        this.titre=titre;
        this.nbpages=nbpages;
        this.datepubli=datepubli;
        this.prix=prix;
    }
    
    public double getPrix(){return this.prix;}
    public int getISBN(){return this.isbn;}
    public int getNbpages(){return this.nbpages;}
    public String getTitre(){return this.titre;}
    public String getDatepubli(){return this.datepubli;}
    
}
