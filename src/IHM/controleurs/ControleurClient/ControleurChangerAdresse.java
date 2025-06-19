package IHM.controleurs.ControleurClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.BD.ActionBD;
import java.sql.SQLException;
import IHM.vues.*;

public class ControleurChangerAdresse implements EventHandler<ActionEvent>{
    
    private ActionBD modele;
    private LivreExpress app;

    public ControleurChangerAdresse(ActionBD modele, LivreExpress app)
    {
        this.modele = modele;
        this.app = app;
    }

    public void handle(ActionEvent event)
    {
        String ville = this.app.getVueClient().getTfVille().getText();
        String adresse = this.app.getVueClient().getTfAdresse().getText();
        Integer codePo = null;
        try {
            codePo = Integer.parseInt(this.app.getVueClient().getTfCodePostal().getText());
        }
        catch (NumberFormatException e)
        {
            this.app.getVueClient().popUpPasUnNbr().showAndWait();
        }
        if (ville.isEmpty() || adresse.isEmpty() || codePo == null || this.app.getVueClient().getTfCodePostal().getText().isEmpty())
        {
            this.app.getVueClient().popUpChampsVides().show();
        }
        else
        {
            try 
            {
                this.modele.changerAdresse(this.app.getVueClient().getClient(), adresse, codePo, ville);
                this.app.getVueClient().popUpActionEffectuee().show();
            }
            catch (SQLException e )
            {
                System.out.println("Erreur SQL");
            }
        }
        
    }
}
