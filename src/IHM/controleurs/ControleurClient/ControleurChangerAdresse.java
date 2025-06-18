package IHM.controleurs.ControleurClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.BD.ActionBD;
import java.sql.SQLException;
import IHM.vues.*;

public class ControleurChangerAdresse implements EventHandler<ActionEvent>{
    
    private ActionBD modele;
    private VueClient vue;

    public ControleurChangerAdresse(ActionBD modele, VueClient vue)
    {
        this.modele = modele;
        this.vue = vue;
    }

    public void handle(ActionEvent event)
    {
        String ville = this.vue.getTfVille().getText();
        String adresse = this.vue.getTfAdresse().getText();
        Integer codePo = null;
        try {
            codePo = Integer.parseInt(this.vue.getTfCodePostal().getText());
        }
        catch (NumberFormatException e)
        {
            this.vue.popUpPasUnNbr().showAndWait();
        }
        if (ville.isEmpty() || adresse.isEmpty() || codePo == null || this.vue.getTfCodePostal().getText().isEmpty())
        {
            this.vue.popUpChampsVides().show();
        }
        else
        {
            try 
            {
                this.modele.changerAdresse(this.vue.getClient(), adresse, codePo, ville);
                this.vue.popUpActionEffectuee().show();
            }
            catch (SQLException e )
            {
                System.out.println("Erreur SQL");
            }
        }
        
    }
}
