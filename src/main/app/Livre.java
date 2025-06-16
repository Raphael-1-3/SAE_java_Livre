package main.app;

import java.sql.*;

public class Livre {

    private long isbn;
    private String titre;
    private int nbpages;
    private int datepubli;
    private double prix;

    public Livre(long isbn,String titre, int nbpages, int datepubli, double prix){
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
    public int getDatepubli(){return this.datepubli;}

    @Override
    public int hashCode()
    {
        return (int) this.isbn * 7919 + titre.hashCode() ;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) {return true;}
        if (o == null || !(o instanceof Livre)) {return false;}
        Livre l = (Livre) o;
        return l.isbn == this.isbn;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "isbn=" + this.isbn +
                ", titre='" + this.titre + '\'' +
                ", nbpages=" + this.nbpages +
                ", datepubli=" + this.datepubli +
                ", prix=" + this.prix +
                '}';
    }
}
