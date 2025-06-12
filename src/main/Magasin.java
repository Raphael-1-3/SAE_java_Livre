package main;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Magasin {
    private int idMag;
    private String nomMag;
    private String villeMag;
    private HashMap<Livre, Integer> catalogue;
    

    public Magasin(int id, String nom, String ville, HashMap<Livre, Integer> catalogue)
    {
        this.idMag = id;
        this.nomMag = nom;
        this.villeMag = ville;
        this.catalogue= catalogue;
    }

    public int getIdmag(){return this.idMag;}
    public String getNomMag(){return this.nomMag;}
    public String getVilleMag(){return this.villeMag;}
    

    public void addLivre(Livre l) 
    {
        if (!this.catalogue.containsKey(l))
        {
            this.catalogue.put(l, 1);
        }
    }

    public void addLivre(Livre l, Integer qte)
    {
        if (!this.catalogue.containsKey(l))
        {
            this.catalogue.put(l, qte);
        }
        else
        {
            this.catalogue.put(l, this.catalogue.get(l) + qte);
        }
    }

}


