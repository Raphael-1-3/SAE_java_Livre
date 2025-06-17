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
import junit.framework.TestFailure;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    public vueClient(LivreExpress LEApp, Client client)
    {
        super();
        this.setPrefSize(1300, 700);
        this.setTop(top(client));
        this.setCenter(centerRecommandation(client));
        //this.setBottom(bottom());
    }

    public Pane top(Client client)
    {
        VBox top = new VBox();
        HBox sstop1 = new HBox();
        HBox sstop2 = new HBox();

        // ssHbox 1 ----
        Label titre = new Label("Livre Expresse - Client");
        titre.setFont(new Font("Times new Roman", 50));
        titre.setPadding(new Insets(15, 15, 0, 0));
        ImageView logo = new ImageView(new Image("file:./img/logo.jpg"));
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        Rectangle bordure = new Rectangle(logo.getFitWidth(), logo.getFitHeight());
        bordure.setArcWidth(30);
        bordure.setArcHeight(30);
        logo.setClip(bordure);
        Label nomUtil = new Label("Connecté en tant que " + client.getNom() + " " + client.getPrenom());
        nomUtil.setFont(new Font("Times new Roman", 25));
        nomUtil.setPadding(new Insets(35, 0, 0, 0));
        nomUtil.setMaxWidth(400);
        nomUtil.setPrefWidth(400);
        HBox boutonsDroite = new HBox(10);
        Button param = new Button("");
        ImageView paramIcon = new ImageView(new Image("file:./img/param.png"));
        paramIcon.setFitHeight(40);
        paramIcon.setFitWidth(40);
        param.setGraphic(paramIcon);
        param.setPadding(new Insets(0, 0, 0, 0));
        param.setPrefSize(50, 50);
        Button deconnexion = new Button();
        ImageView decoIcon = new ImageView(new Image("file:./img/deco.png"));
        decoIcon.setFitWidth(40);
        decoIcon.setFitHeight(40);
        deconnexion.setGraphic(decoIcon);
        deconnexion.setText(""); // Optionally remove text if you want only the image
        deconnexion.setFont(new Font("Times new Roman", 20));
        deconnexion.setPadding(new Insets(0, 0, 0, 0));
        deconnexion.setPrefWidth(50);
        deconnexion.setPrefHeight(50);

        Button panier = new Button();
        ImageView panierIcon = new ImageView(new Image("file:./img/panier.png"));
        panierIcon.setFitHeight(40);
        panierIcon.setFitWidth(40);
        panier.setGraphic(panierIcon);
        panier.setPadding(new Insets(0, 0, 0, 0));
        panier.setPrefWidth(50);
        panier.setPrefHeight(50);
        

        boutonsDroite.setPadding(new Insets(30, 0, 0, 105));
        boutonsDroite.setAlignment(Pos.TOP_RIGHT);
        
        boutonsDroite.getChildren().addAll(deconnexion, param, panier);


        sstop1.setPadding(new Insets(3, 3, 3, 3));
        sstop1.setSpacing(10);
        sstop1.getChildren().addAll(logo, titre, nomUtil, boutonsDroite);



        // ssHBox 2 
        ComboBox<String> actions = new ComboBox<>();
        actions.getItems().addAll(
            "Rechercher par nom de livre",
            "Rechercher par auteur",
            "Rechercher par classification",
            "Rechercher par éditeur"
        );
        actions.setPadding(new Insets(0, 0, 0, 15));

        ComboBox<String> selectionMagasin = new ComboBox<>();
        selectionMagasin.getItems().addAll(
            "La librairie parisienne",
            "Cap au Sud",
            "Ty Li-Breizh-rie",
            "L'européenne",
            "Le Ch'ti livre",
            "Rhône à lire",
            "Loire et livres"
        );
        

        Button executerAction = new Button("Exécuter");
        actions.setPromptText("Choisissez une action"); 
        selectionMagasin.setPromptText("Choissiser un magasin");
        VBox layout = new VBox(10); 
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(actions, selectionMagasin, executerAction);

        TextField schearchbar = new TextField();
        sstop2.setPadding(new Insets(10, 0, 10, 50));
        sstop2.setSpacing(15);
        sstop2.getChildren().addAll(actions, selectionMagasin, executerAction, schearchbar);
        //
        HBox boxLigne = new HBox();
        Line sep = new Line(0, 0, 1100, 0);
        boxLigne.setPadding(new Insets(5, 0, 5, 100));
        boxLigne.getChildren().addAll(sep);

        this.setStyle("-fx-background-color : #d4d5d5;");
        //
        //sstop1.setStyle("-fx-background-color: blue;");
        //sstop2.setStyle("-fx-background-color: lightblue;");
        //boxLigne.setStyle("-fx-background-color: lightgreen;");
        //

        
        
        top.getChildren().addAll(sstop1, boxLigne, sstop2);

        return top;
    }

    public ScrollPane centerRecommandation(Client client)
    {
        VBox recommandations = new VBox(10);
        recommandations.setPadding(new Insets(20));

        // Exemple de recommandations fictives
        for (int i = 1; i <= 10; i++) {
            Label livre = new Label("Livre recommandé n°" + i);
            livre.setFont(new Font("Arial", 18));
            recommandations.getChildren().add(livre);
        }

        ScrollPane scrollPane = new ScrollPane(recommandations);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        setMargin(scrollPane, new Insets(100, 100, 100, 100));

        return scrollPane;
    }

    
}
