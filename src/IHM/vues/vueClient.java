package IHM.vues;

import main.*;
import main.BD.ActionBD;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import IHM.controleurs.controleurSelectionClient;



public class vueClient extends BorderPane
{
    private ActionBD modele;
    private Client client;
    private LivreExpress LEApp;
    private TextField scheachbar;
    private ComboBox<String> selectionRecherche;
    private ComboBox<String> selectionMagasin;

    /**
     * Instancie la fenetre liee au client
     * @param LEApp
     */
    public vueClient(LivreExpress LEApp, Client client, ActionBD modele) throws SQLException
    {
        super();
        this.LEApp = LEApp;
        this.client = client;
        this.modele = modele;
        this.selectionRecherche = new ComboBox<>();
        this.selectionMagasin = new ComboBox<>();
        this.scheachbar = new TextField();
        this.setPrefSize(1300, 700);
        this.setTop(top(client));
        this.setCenter(centerRecommandation(client));
        //this.setBottom(bottom());
    }

    public ActionBD getModele() {
        return this.modele;
    }

    public Client getClient() {
        return this.client;
    }

    public LivreExpress getLEApp() {
        return this.LEApp;
    }

    public TextField getScheachbar() {
        return this.scheachbar;
    }

    public ComboBox<String> getSelectionRecherche() {
        return this.selectionRecherche;
    }

    public ComboBox<String> getSelectionMagasin() {
        return this.selectionMagasin;
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
        this.selectionRecherche.getItems().addAll(
            "Rechercher par nom de livre",
            "Rechercher par auteur",
            "Rechercher par classification",
            "Rechercher par éditeur", 
            "Rechecher par magasin"
        );
        this.selectionRecherche.setPadding(new Insets(0, 0, 0, 15));

        this.selectionMagasin.getItems().addAll(
            "La librairie parisienne",
            "Cap au Sud",
            "Ty Li-Breizh-rie",
            "L'européenne",
            "Le Ch'ti livre",
            "Rhône à lire",
            "Loire et livres"
        );
        
        Button executerAction = new Button("Exécuter");
        executerAction.setPrefWidth(100);
        executerAction.setOnAction(new controleurSelectionClient(this.modele, this.LEApp, this.selectionRecherche, this.selectionMagasin));
        this.selectionRecherche.setPromptText("Choisissez une action"); 
        this.selectionMagasin.setPromptText("Choissiser un magasin");
        VBox layout = new VBox(10); 
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(this.selectionRecherche, this.selectionMagasin, executerAction);

        this.scheachbar = new TextField();
        this.scheachbar.setPrefWidth(600); // Largeur préférée
        sstop2.setPadding(new Insets(10, 0, 10, 50));
        sstop2.setSpacing(15);
        sstop2.getChildren().addAll(this.selectionRecherche, this.selectionMagasin, this.scheachbar, executerAction);
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

    public Pane centerRecommandation(Client client) throws SQLException
    {
        HBox center = new HBox();
        VBox recommandations = new VBox(10);
        recommandations.setPadding(new Insets(20));
        VBox ajouterPanier = new VBox(1);

        // Exemple de recommandations fictives
        List<Livre> tabrecoC = null;
        try
        {
            tabrecoC = modele.onVousRecommande(client);
            for (Livre bouquin : tabrecoC)
            {
                Text affichage = new Text(bouquin.getTitre() + "   " + bouquin.getPrix());
                recommandations.getChildren().addAll(affichage);
            }
        } catch (PasDHistoriqueException pdh) 
        { recommandations.getChildren().setAll(new Text("Vous n'avez jamais rien commandé ou nous n'avons aucune recommendation a vous presenter"));}
        
        ScrollPane scrollPane = new ScrollPane(recommandations);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(1000);
        setMargin(center, new Insets(70, 70, 70, 70));
        center.getChildren().addAll(scrollPane, ajouterPanier);

        return center;
    }

    
}
