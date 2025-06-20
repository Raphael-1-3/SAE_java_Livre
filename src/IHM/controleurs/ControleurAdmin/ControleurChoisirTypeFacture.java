package IHM.controleurs.ControleurAdmin;


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

public class ControleurChoisirTypeFacture implements EventHandler<ActionEvent>{
    private LivreExpress app;
    private ActionBD modele;

    public ControleurChoisirTypeFacture(LivreExpress app, ActionBD modele)
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
                this.app.getVueAdmin().pannelChoisirMoisAnnee();
                break;
            case "Client":
                this.app.getVueAdmin().afficherPopUpChosirClient();
                break;
            default:
                break;
        }
    }
}
