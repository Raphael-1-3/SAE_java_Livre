package IHM.controleurs.ControleurClient;
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
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import IHM.vues.*;
import main.app.*;

public class ControleurChangerMdp implements EventHandler<ActionEvent>{
    
    private ActionBD modele;
    private VueClient vue;

    public ControleurChangerMdp(ActionBD modele, VueClient vue)
    {
        this.modele = modele;
        this.vue = vue;
    }

    public void handle(ActionEvent event)
    {
        String pw = this.vue.getPwField().getText();
        if (pw.isEmpty())
        {
            this.vue.popUpChampsVides().show();
        }
        else
        {
            try 
            {
                this.modele.changerMotDePasse(this.vue.getClient(), pw);
                this.vue.popUpActionEffectuee().show();
            }
            catch (SQLException e )
            {
                System.out.println("Erreur SQL");
            }
        }
        
    }
}
