package IHM.controleurs.ControleurAdmin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import main.BD.ActionBD;
import IHM.vues.*;
import main.app.*;
import main.app.Magasin;

import java.sql.SQLException;

public class ControleurSelectionMag implements EventHandler<MouseEvent> 
{
    private LivreExpress app;
    private ActionBD modele;
    private Magasin m;

    public ControleurSelectionMag(ActionBD modele, LivreExpress app, Magasin m)
    {
        this.app = app;
        this.modele = modele;
        this.m = m;
    }

    public void handle(MouseEvent event) 
    {
        this.app.getVueAdmin().setMagChoisi(m);
        this.app.getVueAdmin().activer();
        this.app.getVueAdmin().panneauDeBord();
    }
}
