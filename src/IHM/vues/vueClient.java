package IHM.vues;

import main.*;
import main.app.*;
import main.app.Client;
import main.BD.*;
import main.Exceptions.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
import java.util.List;



public class vueClient extends BorderPane
{

    /**
     * Instancie la fenetre liee au client
     * @param LEApp
     */
    public vueClient(String LEApp, Client client)
    {
        super();
        //this.setTop(top());
        //this.setCenter(center());
        //this.setBottom(bottom());
    }

    public void top(Client client)
    {
        VBox top = new VBox();
        HBox sstop1 = new HBox();
        HBox sstop2 = new HBox();

        // ssHbox 1 ----
        Text titre = new Text("Livre Expresse - Client");
        titre.setFont(new Font("Times new Roman", 60));
        ImageView logo = new ImageView(new Image("file:./img/logo.jpg"));
        Text nomUtil = new Text("Vous êtes connecté en tant que " + " nom  " + " " + " prenom");
        Button param = new Button();
        Button deconnexion = new Button();

        sstop1.getChildren().addAll(logo, titre, nomUtil, deconnexion, param);

        // ssHBox 2 ----
        TitledPane menuAction = new TitledPane();
        ComboBox<Button> actions = new ComboBox<>();
        Button RechercherNomLivre = new Button();
        Button RechercheAuteur = new Button();
        Button RechercheClassification = new Button();
        Button RechercheEditeur = new Button();
        actions.getItems().addAll(RechercherNomLivre, RechercheAuteur, RechercheClassification, RechercheEditeur);
        
        ComboBox<Text> selectionMagasin = new ComboBox<>();
        Text lp   = new Text("La librairie parisienne");
        Text cs   = new Text("Cap au Sud");
        Text tlbr = new Text("Ty Li-Breizh-rie");
        Text euro = new Text("L'européenne");
        Text ctl  = new Text("Le Ch'ti livre");
        Text rl   = new Text("Rhône à lire");
        Text ll   = new Text("Loire et livres");
        selectionMagasin.getItems().addAll(lp, cs, tlbr, euro, ctl, rl, ll);

        Text OU = new Text("OU");
        OU.setFont(new Font("Times new Roman", 60));

        sstop2.getChildren().addAll(menuAction, OU ,selectionMagasin);
        top.getChildren().addAll(sstop1, sstop2);
    }
}
