package main;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Magasin {
    private int idMag;
    private String nomMag;
    private String villeMag;
    

    public Magasin(int id, String nom, String ville)
    {
        this.idMag = id;
        this.nomMag = nom;
        this.villeMag = ville;
    }

    public int getIdmag(){return this.idMag;}
    public String getNomMag(){return this.nomMag;}
    public String getVilleMag(){return this.villeMag;}
    

}


