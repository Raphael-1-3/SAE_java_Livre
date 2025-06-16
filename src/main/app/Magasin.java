package main.app;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Magasin {
    private Integer idMag;
    private String nomMag;
    private String villeMag;
    

    public Magasin(Integer id, String nom, String ville)
    {
        this.idMag = id;
        this.nomMag = nom;
        this.villeMag = ville;
    }

    public Integer getIdmag(){return this.idMag;}
    public String getNomMag(){return this.nomMag;}
    public String getVilleMag(){return this.villeMag;}
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Magasin other = (Magasin) obj;
        return idMag == other.idMag &&
               nomMag.equals(other.nomMag) &&
               villeMag.equals(other.villeMag);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(idMag);
        result = 31 * result + nomMag.hashCode();
        result = 31 * result + villeMag.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "Id : " + this.idMag + " | nom : " + this.nomMag + " | Ville : " + this.villeMag;
    }
}


