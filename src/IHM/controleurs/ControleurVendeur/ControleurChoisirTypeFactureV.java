package IHM.controleurs.ControleurVendeur;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;

import java.sql.SQLException;

import IHM.vues.LivreExpress;
import IHM.vues.VueAdmin;
import main.BD.ActionBD;
import main.app.Livre;

public class ControleurChoisirTypeFactureV implements EventHandler<ActionEvent>{
    private LivreExpress app;
    private ActionBD modele;

    public ControleurChoisirTypeFactureV(LivreExpress app, ActionBD modele)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle (ActionEvent event)
    {
        ToggleButton b = (ToggleButton) event.getSource();
        String s = b.getText();
        switch (s) {
            case "Magasin":
                this.app.getVueVendeur().pannelChoisirMoisAnnee();
                break;
            case "Client":
                this.app.getVueVendeur().afficherPopUpChosirClient();
                break;
            default:
                break;
        }
    }
}
