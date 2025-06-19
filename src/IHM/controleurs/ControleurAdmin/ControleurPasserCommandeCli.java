package IHM.controleurs.ControleurAdmin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.BD.ActionBD;
import main.Exceptions.PasDeTelUtilisateurException;
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

import IHM.controleurs.ControleurVendeur.ControleurPasserCommande;
import IHM.vues.*;
import main.app.*;
public class ControleurPasserCommandeCli implements EventHandler<ActionEvent> 
{
    private LivreExpress app;
    private ActionBD modele;
    private Client client;

    public ControleurPasserCommandeCli(LivreExpress app,ActionBD modele)
    {
        this.app = app;
        this.modele = modele;
        
    }

    public Client getClient() {
        return this.client;
    }

    public void handle(ActionEvent event)
    {
        String nomCli = this.app.getVueAdmin().getTfNom().getText();
        String prenomCli = this.app.getVueAdmin().getTfPrenom().getText();
        Integer codePostalCli = null;
        try{
            codePostalCli = Integer.parseInt(this.app.getVueAdmin().getTfCodePostal().getText());
        }
        catch (NumberFormatException e)
        {
            this.app.getVueAdmin().popUpPasUnNbr().show();
        }
        if (nomCli.isEmpty() || prenomCli.isEmpty() || codePostalCli == null) {
            this.app.getVueAdmin().popUpChampsVides().show();;
        }
        else{
            try {
                this.client = this.modele.getClientAPartirNomPrenomCodePostal(nomCli, prenomCli, codePostalCli);
                System.out.println(client);
                this.app.getVueAdmin().vueCommandeClient(client);
            } catch (SQLException | PasDeTelUtilisateurException e) {
                this.app.getVueAdmin().popUpClientInexistant().show();
            }
        }
        
    }
}