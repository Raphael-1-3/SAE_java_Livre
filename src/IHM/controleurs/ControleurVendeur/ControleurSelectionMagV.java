package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import main.BD.ActionBD;
import IHM.vues.*;
import main.app.*;
import main.app.Magasin;

import java.sql.SQLException;

public class ControleurSelectionMagV implements EventHandler<MouseEvent> 
{
    private LivreExpress app;
    private ActionBD modele;
    private Magasin m;

    public ControleurSelectionMagV(ActionBD modele, LivreExpress app, Magasin m)
    {
        this.app = app;
        this.modele = modele;
        this.m = m;
    }

    public void handle(MouseEvent event) 
    {
        this.app.getVueVendeur().setMagChoisi(m);
        this.app.getVueVendeur().activer();
        try
        {
        this.app.getVueVendeur().panneauDeBord();
        }catch (SQLException e) {System.out.println("Probleme appli");}
    }
}
