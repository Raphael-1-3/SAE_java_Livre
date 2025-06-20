package IHM.controleurs.ControleurVendeur;

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

public class ControleurModiferStockV implements EventHandler<ActionEvent>{
    private LivreExpress app;
    private ActionBD modele;
    private Livre livre;

    
    public ControleurModiferStockV(LivreExpress app, ActionBD modele, Livre l)
    {
        this.app = app;
        this.modele = modele;
        this.livre = l;
    }

    public void handle (ActionEvent event)
    {
        String rawQte = this.app.getVueVendeur().getTfQte().getText();
        if (rawQte.isEmpty())
        {
            this.app.getVueVendeur().popUpChampsVides().show();
        }
        else
        {
            try 
            {
                Integer qte = Integer.parseInt(rawQte);
                this.modele.UpdateStock(livre, this.app.getVueVendeur().getMagChoisi(), qte);
                this.app.getVueVendeur().popUpActionEffectuee().show();
            }
            catch (NumberFormatException e)
            {
                this.app.getVueVendeur().popUpPasUnNbr().show();
            }
            catch (SQLException e)
            {
                System.out.println("Erreur SQL");
            }
        }
    }
}
