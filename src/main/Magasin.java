package main;
import java.util.List;
import java.util.ArrayList;

public class Magasin {
    private int idMag;
    private String nomMag;
    private String villeMag;
    private List listeLivre;

    public Magasin(int id, String nom, String ville)
    {
        this.idMag = id;
        this.nomMag = nom;
        this.villeMag = ville;
        this.listeLivre= new ArrayList<>();
    }

    public int getIdmag(){return this.idMag;}
    public String getNomMag(){return this.nomMag;}
    public String getVilleMag(){return this.villeMag;}
}
