package main;

public class Livre {

    private long isbn;
    private String titre;
    private int nbpages;
    private String datepubli;
    private double prix;

    public Livre(long isbn,String titre, int nbpages, String datepubli, double prix){
        this.isbn=isbn;
        this.titre=titre;
        this.nbpages=nbpages;
        this.datepubli=datepubli;
        this.prix=prix;
    }
    
    public double getPrix(){return this.prix;}
    public long getISBN(){return this.isbn;}
    public int getNbpages(){return this.nbpages;}
    public String getTitre(){return this.titre;}
    public String getDatepubli(){return this.datepubli;}

    @Override
    public int hashCode()
    {
        return this.isbn * 7919 + titre.hashCode() ;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) {return true;}
        if (o == null || !(o instanceof Livre)) {return false;}
        Livre l = (Livre) o;
        return l.isbn == this.isbn;
    }
}
