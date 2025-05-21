package main;
import java.sql.*;

public class Livre {

    private int isbn;
    private String titre;
    private int nbpages;
    private Date datepubli;
    private double prix;

    public Livre(int isbn,String titre, int nbpages, Date datepubli, double prix){
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
    public Date getDatepubli(){return this.datepubli;}

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
