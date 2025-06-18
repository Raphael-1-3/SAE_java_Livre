package IHM.vues;

import main.*;
import IHM.controleurs.ControleurClient.*;
import main.BD.ActionBD;
import main.app.*;
import main.BD.*;
import main.Exceptions.*;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
import java.util.HashMap;
import java.util.List;





public class VueClient extends BorderPane
{
    private ActionBD modele;
    private Client client;
    private LivreExpress LEApp;
    private TextField barRecherche;
    private ComboBox<String> selectionRecherche;
    private ComboBox<String> selectionMagasin;
    private Livre livreChoisi;

    private VBox centre;
    private HBox TitrePage;
    private HBox contenantRLIL;
    private VBox box1;
    private VBox box2;
    private VBox box3;

    /**
     * Instancie la fenetre liee au client
     * @param LEApp
     */
    public VueClient(LivreExpress LEApp, Client client, ActionBD modele) throws SQLException
    {
        super();
        Label titre = new Label("Catalogue");
        titre.setFont(new Font("Times new Roman", 30));
        titre.setPadding(new Insets(0, 0, 0, 500));
        this.LEApp = LEApp;
        this.client = client;
        this.modele = modele;
        this.selectionRecherche = new ComboBox<>();
        this.selectionMagasin = new ComboBox<>();
        this.barRecherche = new TextField();
        this.contenantRLIL = new HBox();
        this.box1 = new VBox();
        this.box1.setPrefSize(400, 400);
        this.box2 = new VBox();
        this.box2.setPrefSize(400, 400);
        this.box3 = new VBox();
        this.box3.setPrefSize(400, 400);
        this.centre = new VBox();
        this.TitrePage = new HBox();
        this.TitrePage.getChildren().add(titre);
        this.TitrePage.setPrefHeight(25);
        this.box1.setStyle("-fx-background-radius : 15px;" + 
        "-fx-background-color : #f9f9f9;" + 
        "-fx-background : #f9f9f9;");
        this.contenantRLIL.setStyle("-fx-background-radius : 15px;" + 
        "-fx-background-color : #f9f9f9;" + 
        "-fx-background : #f9f9f9;" + 
        "-fx-border-width : 2px;" + 
        "-fx-border-color : #df9f53;" + 
        "-fx-border-radius : 15px;");
        this.box2.setStyle("-fx-background-radius : 15px;" + 
        "-fx-background-color : #f9f9f9;" + 
        "-fx-background : #f9f9f9;" + 
        "-fx-border-width : 0 2px 0 2px;" + 
        "-fx-border-color : #df9f53");
        this.box3.setStyle("-fx-background-color: #f9f9f9;" + 
        "-fx-background-radius : 15px;" + 
        "-fx-background : #f9f9f9;");
        this.contenantRLIL.setPrefSize(1000, 1000);
        this.contenantRLIL.getChildren().addAll(this.box1, this.box2, this.box3);
        this.centre.getChildren().addAll(this.TitrePage, this.contenantRLIL);
        this.setCenter(this.centre);
        this.setCenterRecommandation(this.client);
        this.setMargin(this.centre, new Insets(60, 120, 60, 60));
        this.setPrefSize(1300, 700);
        this.setTop(this.top(this.client));
        //this.setBottom(this.bottom());
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

    public TextField getbarRecherche() {
        return this.barRecherche;
    }

    public ComboBox<String> getSelectionRecherche() {
        return this.selectionRecherche;
    }

    public ComboBox<String> getSelectionMagasin() {
        return this.selectionMagasin;
    }
    public Livre getLivreChoisi() {
        return this.livreChoisi;
    }

    public void setLivreChoisi(Livre livreChoisi) {
        this.livreChoisi = livreChoisi;
    }

    public Client getclient() {
        return this.client;
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
        panier.setOnAction(new ControleurConsulterPanier(this.modele, this.LEApp));
        

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
        executerAction.setOnAction(new controleurSelectionClient(this.modele, this.LEApp));
        this.selectionRecherche.setPromptText("Choisissez une action"); 
        this.selectionMagasin.setPromptText("Choissiser un magasin");
        VBox layout = new VBox(10); 
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(this.selectionRecherche, this.selectionMagasin, executerAction);

        this.barRecherche = new TextField();
        this.barRecherche.setPrefWidth(600); // Largeur préférée
        sstop2.setPadding(new Insets(10, 0, 10, 50));
        sstop2.setSpacing(15);
        sstop2.getChildren().addAll(this.selectionRecherche, this.selectionMagasin, this.barRecherche, executerAction);
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

    public void setCenterRecommandation(Client client) throws SQLException
    {
        ProgressIndicator loading = new ProgressIndicator();
        VBox vb = new VBox();
        ScrollPane sp = new ScrollPane();
        sp.setPrefHeight(400);
        sp.setFocusTraversable(false);

        Task<VBox> task = new Task<>() {
            @Override
            protected VBox call() throws Exception
            {
                return centerRecommandation(client);
            }
        };
        loading.visibleProperty().bind(task.runningProperty());
        task.setOnSucceeded(e -> {
            vb.getChildren().clear();
            vb.getChildren().add(task.getValue());
        });
        
        vb.getChildren().addAll(loading);
        sp.setContent(vb);
        this.box2.getChildren().clear();
        this.box2.getChildren().add(sp);
        
        new Thread(task).start();
    }

    public VBox centerRecommandation(Client client) throws SQLException
    {
        VBox livresBox = new VBox();
        livresBox.setPadding(new Insets(15, 15, 15, 15));
        try
        {
            List<Livre> tabrecoC = this.modele.onVousRecommande(client);
            if (tabrecoC == null || tabrecoC.isEmpty()) {
                livresBox.getChildren().add(new Text("Aucune recommandation à afficher."));
            } else {
                for (Livre bouquin : tabrecoC)
                {
                    Label affichageT = new Label(bouquin.getTitre());
                    affichageT.setPadding(new Insets(5));
                    affichageT.setFont(new Font(15));
                    affichageT.setStyle("-fx-underline : true;");
                    affichageT.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                    affichageT.setOnMouseClicked(new controleurSelectionLivre(this.modele, this.LEApp));
                    livresBox.getChildren().add(affichageT);
                }
            }
        } catch (PasDHistoriqueException pdh) {
            livresBox.getChildren().add(new Text("Vous n'avez jamais rien commandé ou nous n'avons aucune recommandation à vous présenter"));
        }
        return livresBox;
    }

    /**
     * Permet d'afficher dans le center de la vue la liste de livres en fonction de la recherche
     * @param livres
     */
    public void centerAfficherLivres(List<Livre> livres) 
    {
        this.box2.getChildren().clear();
        VBox libresbox = new VBox();
        if (livres == null || livres.isEmpty()) {
            libresbox.getChildren().add(new Text("Aucun livre à afficher."));
        } else {
            for (Livre livre : livres) {
                Label affichageT = new Label(livre.getTitre());
                affichageT.setPadding(new Insets(0, 0, 0, 10));
                affichageT.setFont(new Font(15));
                affichageT.setStyle("-fx-underline : true;");
                affichageT.setOnMouseClicked(new controleurSelectionLivre(this.modele, this.LEApp));
                libresbox.getChildren().add(affichageT);
            }
        }
        ScrollPane sp = new ScrollPane(libresbox);
        this.box2.getChildren().add(sp);
    }

    public void afficheInfoLivre(Livre livre)
    {
        this.box3.getChildren().clear();
        VBox infoBox = new VBox(10);
        if (livre == null) {
            infoBox.getChildren().add(new Text("Aucune information à afficher."));
        } else {
            try 
            {
                Label titre = new Label("Titre : " + livre.getTitre());
                Label prix = new Label("Prix : " + livre.getPrix() + " €");

                List<Auteur> auteurs = this.modele.getAuteurParIdLivre(livre.getISBN());
                StringBuilder auteursStr = new StringBuilder("Auteurs : ");
                if (auteurs.isEmpty()) auteursStr.append("Aucun");
                else for (Auteur a : auteurs) auteursStr.append(a.getNomAuteur());

            Label auteursLabel = new Label(auteursStr.toString());
            auteursLabel.setPadding(new Insets(10, 0, 0, 0));
            auteursLabel.setFont(new Font(15));

            List<Classification> classes = this.modele.getClassificationParIdLivre(livre.getISBN());
            StringBuilder classStr = new StringBuilder("Classifications : ");
            if (classes.isEmpty()) classStr.append("Aucune");
            else for (Classification c : classes) classStr.append(c.getNomClass());
            Label classLabel = new Label(classStr.toString());
            classLabel.setPadding(new Insets(10, 0, 0, 0));
            classLabel.setFont(new Font(15));

            List<Editeur> editeurs = this.modele.getEditeurParIdLivre(livre.getISBN());
            StringBuilder editStr = new StringBuilder("Editeurs : ");
            if (editeurs.isEmpty()) editStr.append("Aucun");
            else for (Editeur e : editeurs) editStr.append(e.getNomEdit());
            Label editLabel = new Label(editStr.toString());
            editLabel.setPadding(new Insets(10, 0, 0, 0));
            editLabel.setFont(new Font(15));

                infoBox.getChildren().addAll(titre, prix, auteursLabel, classLabel, editLabel);

            } catch (Exception e) {
                infoBox.getChildren().clear();
                infoBox.getChildren().add(new Text("Erreur lors de l'affichage des informations du livre."));
            }
        }
        this.box3.getChildren().add(infoBox);
        this.boutouAjouterPanier();
    }

    public void boutouAjouterPanier()
    {
        Button ajtpanier =new Button("ajouter ce livre au panier");
        ajtpanier.setOnAction(new ControleurAjouterPanier(this.modele, this.LEApp));
        ajtpanier.setPadding(new Insets(10, 10, 10, 10));
        this.box3.getChildren().add(ajtpanier);
    } 

    public void centerAfficheEditeur(List<Editeur> editeurs) {
        VBox editeursBox = new VBox();
        if (editeurs == null || editeurs.isEmpty()) {
            editeursBox.getChildren().add(new Text("Aucun éditeur à afficher."));
        } else {
            for (Editeur editeur : editeurs) {
                Label affichageE = new Label(editeur.getNomEdit());
                affichageE.setOnMouseClicked(new controleurSelectionEditeur(this.modele, this.LEApp));
                affichageE.setStyle("-fx-underline = true;");
                affichageE.setFont(new Font(15));
                affichageE.setPadding(new Insets(0, 0, 0, 10));
                editeursBox.getChildren().add(affichageE);
            }
        }
        ScrollPane sp = new ScrollPane(editeursBox);
        this.box1.getChildren().add(sp);
    }

    public void centerAfficheClassification(List<Classification> classifications) {
        VBox classificationsBox = new VBox();
        if (classifications == null || classifications.isEmpty()) {
            classificationsBox.getChildren().add(new Text("Aucune classification à afficher."));
        } else {
            for (Classification classification : classifications) {
                Label affichageC = new Label(classification.getNomClass());
                affichageC.setOnMouseClicked(new controleurSelectionClassification(this.modele, this.LEApp));
                affichageC.setStyle("-fx-underline = true;");
                affichageC.setFont(new Font(15));
                affichageC.setPadding(new Insets(0, 0, 0, 10));
                classificationsBox.getChildren().add(affichageC);
            }
        }
        ScrollPane sp = new ScrollPane(classificationsBox);
        this.box1.getChildren().add(sp);
    }
    

    public void centerAfficheAuteur(List<Auteur> auteurs) {
        VBox auteursBox = new VBox();
        if (auteurs == null || auteurs.isEmpty()) {
            auteursBox.getChildren().add(new Text("Aucun auteur à afficher."));
        } else {
            for (Auteur auteur : auteurs) {
                Label affichageA = new Label(auteur.getNomAuteur());
                affichageA.setOnMouseClicked(new controleurSelectionAuteur(this.modele, this.LEApp));
                affichageA.setStyle("-fx-underline = true;");
                affichageA.setFont(new Font(15));
                affichageA.setPadding(new Insets(0, 0, 0, 10));
                auteursBox.getChildren().add(affichageA);
            }
        }
        ScrollPane sp = new ScrollPane(auteursBox);
        this.box1.getChildren().add(sp);
    }

    

    public void consulterPanier()
    {
        this.TitrePage.getChildren().clear();
        Text titre = new Text("Votre Panier");
        this.TitrePage.getChildren().addAll(titre);
        VBox panierBox = new VBox();
        HashMap<Livre, Integer> panier = this.getclient().getPanier();
        if (panier == null || panier.isEmpty()) {
            Label pVide = new Label("Votre panier est vide.");
            panierBox.getChildren().add(pVide);
        } else {
            for (Livre livre : panier.keySet()) {
                HBox ligne = new HBox();
                Label quantiteLabel = new Label(String.valueOf(panier.get(livre)));
                Label titreLabel = new Label(livre.getTitre());
                titreLabel.setOnMouseClicked(new controleurSelectionLivre(modele, LEApp));

                quantiteLabel.setPrefWidth(60);
                titreLabel.setPrefWidth(300);

                quantiteLabel.setAlignment(Pos.CENTER_LEFT);
                titreLabel.setAlignment(Pos.CENTER_LEFT);

                ligne.getChildren().addAll(quantiteLabel, titreLabel);
                ligne.setSpacing(10);
                panierBox.getChildren().add(ligne);
            }
        }
        ScrollPane sp = new ScrollPane(panierBox);
        VBox droite = new VBox();

        Integer nbArticle = this.client.getNbArticles();
        Double prix = this.client.getPrixTotal();


        Button suppr = new Button("supprimer livre");
        Button commander = new Button("Commander le panier");

        droite.getChildren().addAll(new Button("supprimer livre"),new Button("Commader le panier"));
        this.box3.getChildren().clear();
        this.box2.getChildren().add(droite);
        this.box1.getChildren().add(sp);
        this.contenantRLIL.getChildren().clear();
        this.contenantRLIL.getChildren().addAll(this.box1, this.box3, this.box2);
    }

    public void reset()
    {
        this.box3.getChildren().clear();
        this.box1.getChildren().clear();
        this.box2.getChildren().clear();
        this.barRecherche.clear();
        this.TitrePage.getChildren().clear();
    }

    public void majCatalogue()
    {
        this.contenantRLIL.getChildren().clear();
        this.contenantRLIL.getChildren().addAll(this.box1, this.box2, this.box3);
    }

    public void resetBox2()
    {
        this.box2.getChildren().clear();
    }

    public void majPanier()
    {
        this.consulterPanier();
    }
}
