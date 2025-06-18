package IHM.controleurs.ControleurAdmin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import main.BD.ActionBD;
import IHM.vues.*;
import main.app.*;
import main.app.Magasin;

import java.sql.SQLException;

public class ControleurSelectionMag implements EventHandler<ActionEvent> 
{
    private LivreExpress app;
    private ActionBD modele;
    private ComboBox<String> comboBox;

    public ControleurSelectionMag(ActionBD modele, LivreExpress app, ComboBox<String> comboBox)
    {
        this.app = app;
        this.modele = modele;
        this.comboBox = comboBox;
    }

    public void handle(ActionEvent event) 
    {
        String nomMag = comboBox.getValue();

        if (nomMag != null && !nomMag.equals("Aucun magasin à afficher.")) 
        {
            try
            {
                Magasin mag = this.modele.magAPartirNom(nomMag);
                this.app.getVueAdmin().setMagChoisi(mag);
                this.app.getVueAdmin().afficheInfoMag();
            } 
            catch(SQLException sql) 
            {
                System.out.println("Erreur SQL : " + sql.getMessage());
            }
        }
    }
}
