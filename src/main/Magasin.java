package main;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Magasin {
    private int idMag;
    private String nomMag;
    private String villeMag;
    private List<Livre> listeLivre;
    

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
    

    public static void rechercheLivre(Magasin mag){
        List<String> maListe = new ArrayList<>();
        maListe.add("Magasin1");
        maListe.add("Magasin2");
        maListe.add("Retour");
        boolean commande_faite = false;
        while(!commande_faite){
            System.out.println(AfficherMenu.Menu("Recherche", maListe));
            Scanner scanner = new Scanner(System.in);
            System.out.println("Quel est le titre du livre que tu recherche ? :" );
            String nomLivre = scanner.nextLine();
            estPresent(mag,nomLivre);

        }
    }

    public static void estPresent(Magasin mag,String l){
        for(Livre livre:mag.listeLivre){
            if(livre.getTitre().equals(l)){
            System.out.println("Le livre "+livre.getTitre()+" est présent dans le magasin "+mag.getNomMag());
        }
        
        else{
            System.out.println("Le livre "+livre.getTitre()+" n'est pas présent dans le magasin "+mag.getNomMag());
        }

        }
    }

    public static void ajouterLivre(Magasin mag)
    {
        
    }

}


