package main;

public class Editeur {

    private int idedit;
    private String nomedit;

    public Editeur(int idedit, String nomedit){
        this.idedit=idedit;
        this.nomedit=nomedit;
    }

    public int getIdEditeur()
    {
        return this.idedit;
    }

    public String getNomEdit()
    {
        return this.nomedit;
    }
}
