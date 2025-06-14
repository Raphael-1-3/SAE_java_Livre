package app;


public class Classification {

    private int iddewey;
    private String nomclass;

    public Classification(int iddewey,String nomclass){
        this.iddewey=iddewey;
        this.nomclass=nomclass;
    }

    public int getIddewey(){return this.iddewey;}
    public String getNomClass(){return this.nomclass;}
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Classification that = (Classification) obj;
        return iddewey == that.iddewey &&
               (nomclass != null ? nomclass.equals(that.nomclass) : that.nomclass == null);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(iddewey);
        result = 31 * result + (nomclass != null ? nomclass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Classification{" +
                "iddewey=" + iddewey +
                ", nomclass='" + nomclass + '\'' +
                '}';
    }
}
