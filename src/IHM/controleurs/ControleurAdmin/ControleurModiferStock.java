package IHM.controleurs.ControleurAdmin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import IHM.vues.LivreExpress;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.BD.ActionBD;
import main.BD.ConnexionMySQL;
import main.app.Livre;

public class ControleurModiferStock implements EventHandler<ActionEvent>{
    private LivreExpress app;
    private ActionBD modele;
    private Livre livre;

    
    public ControleurModiferStock(LivreExpress app, ActionBD modele, Livre l)
    {
        this.app = app;
        this.modele = modele;
        this.livre = l;
    }

    public void handle (ActionEvent event)
    {
        String rawQte = this.app.getVueAdmin().getTfQte().getText();
        if (rawQte.isEmpty())
        {
            this.app.getVueAdmin().popUpChampsVides().show();
        }
        else
        {
            try 
            {
                Integer qte = Integer.parseInt(rawQte);
                this.modele.UpdateStock(livre, this.app.getVueAdmin().getMagChoisi(), qte);
                this.app.getVueAdmin().popUpActionEffectuee().show();
            }
            catch (NumberFormatException e)
            {
                this.app.getVueAdmin().popUpPasUnNbr().show();
            }
            catch (SQLException e)
            {
                System.out.println("Erreur SQL");
            }
        }
    }
}
