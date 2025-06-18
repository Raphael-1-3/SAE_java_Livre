/* package IHM.controleurs.ControleurAdmin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.BD.ActionBD;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;
import javafx.fxml.FXML;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import IHM.vues.*;
import main.app.*;
public class ControleurSelectionGraphique implements EventHandler<ActionEvent> 
{
    private LivreExpress app;
    private ActionBD modele;

    public ControleurSelectionGraphique(ActionBD modele, LivreExpress app)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle(ActionEvent event) 
    {
        
        try 
        {
            VueAdmin va = this.app.getVueAdmin();
            String choixGra = va.getSelectionGra().getValue();
            String recherche = va.getParametreGraphique().getText();

            if (choixGra.equals(null) || recherche.equals(null) || choixGra.equals("") || recherche.equals("")) this.app.getVueConnexion().popUpChampsVides().showAndWait();
            else 
            {
            
                switch (choixGra) 
                {
                    case "NombreDeLivreVendueParMagasinParAns":
                        va.afficheGraphiqueNombreDeLivreVendueParMagasinParAns(this.modele.NombreDeLivreVendueParMagasinParAns());
                }
            }
        } catch(SQLException sql) {System.out.println("probleme modele");}
    }
}
    */