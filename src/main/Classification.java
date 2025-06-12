package main;

public class Classification {

    private int iddewey;
    private String nomclass;

    public Classification(int iddewey,String nomclass){
        this.iddewey=iddewey;
        this.nomclass=nomclass;
    }

    public int getIddewey(){return this.iddewey;}
    public String getNomClass(){return this.nomclass;}
    
}
