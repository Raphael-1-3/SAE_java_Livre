package main.app;


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
    @Override
    public String toString() {
        return "Editeur{id=" + idedit + ", nom='" + nomedit + "'}";
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(idedit);
        result = 31 * result + (nomedit != null ? nomedit.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Editeur other = (Editeur) obj;
        return idedit == other.idedit &&
               (nomedit == null ? other.nomedit == null : nomedit.equals(other.nomedit));
    }
}
