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
    private LivreExpress app;
    private Magasin mag;
    private ComboBox<String> criteres;
    private TextField recherche;
    private VBox vbCentre;
    private ScrollPane spCentre;
    private TilePane centre;
    private Label titreCentre;

    private PasswordField pwField;
    private TextField tfAdresse;
    private TextField tfVille;
    private TextField tfCodePostal;

    private ComboBox<String> livraison;
    private String liv;
    /**
     * Instancie la fenetre liee au client
     * @param LEApp
     */
    public VueClient(LivreExpress app, Client client, ActionBD modele) throws SQLException
    {
        super();
        this.modele = modele;
        this.client = client;
        this.vbCentre = new VBox();
        this.app = app;
        this.spCentre = new ScrollPane();
        this.criteres = new ComboBox<>();
        this.criteres.setMaxWidth(200);
        this.criteres.setMinWidth(200);
        this.recherche = new TextField();
        this.recherche.setPromptText("Recherche...");
        this.setStyle("-fx-background-color : #d4d5d5;");
        this.spCentre.setStyle("-fx-background-color : #e7e7e7;" + 
        "-fx-background-radius : 15px;");
        this.spCentre.setMaxSize(1260, 350);
        this.spCentre.setPrefSize(1260, 350);
        this.centre = new TilePane();
        this.centre.setStyle("-fx-background-color : #e7e7e7;" + 
        "-fx-background-radius : 15px;");
        this.centre.setPrefSize(1240, 350);
        this.centre.setMaxSize(1240, 350);
        this.centre.setPadding(new Insets(20));
        this.centre.setHgap(75);
        this.centre.setVgap(30);
        this.vbCentre.setPadding(new Insets(20));
        this.vbCentre.setAlignment(Pos.CENTER);
        this.titreCentre = new Label("TEXTE");
        this.titreCentre.setFont(new Font(30));
        this.titreCentre.setPadding(new Insets(10, 0, 10, 0));
        this.spCentre.setContent(centre);
        this.vbCentre.getChildren().addAll(titreCentre, spCentre);
        
        this.pwField = new PasswordField();
        this.pwField.setMaxWidth(550);
        this.pwField.setMinWidth(550);
        this.tfAdresse = new TextField();
        this.tfAdresse.setMaxWidth(550);
        this.tfAdresse.setMinWidth(550);
        this.tfCodePostal = new TextField();
        this.tfCodePostal.setMaxWidth(550);
        this.tfCodePostal.setMinWidth(550);
        this.tfVille = new TextField();
        this.tfVille.setMaxWidth(550);
        this.tfVille.setMinWidth(550);
        
        this.livraison = new ComboBox<>();
        this.liv = "M";

        this.Top();
        this.setCenter(vbCentre);
        this.panelChoixMag();
        this.criteres.setDisable(true);
        this.recherche.setDisable(true);
        
        //System.out.println(this.app.getVueClient());
        ControleurCritere controleurC = new ControleurCritere(this, this.modele);
        
    }

    public TextField getRecherche()
    {
        return this.recherche;
    }

    public Client getClient()
    {
        return this.client;
    }

    public Magasin getMag()
    {
        return this.mag;
    }

    public void setMag(Magasin m)
    {
        this.mag = m;
    }

    public ComboBox<String> getCriteres()
    {
        return this.criteres;
    }

    public PasswordField getPwField() {
        return this.pwField;
    }

    public TextField getTfAdresse() {
        return this.tfAdresse;
    }

    public TextField getTfVille() {
        return this.tfVille;
    }

    public TextField getTfCodePostal() {
        return this.tfCodePostal;
    }
    
    public void setLiv(String s)
    {
        this.liv = s;
    }

    public void activer()
    {
        this.criteres.setDisable(false);
        this.recherche.setDisable(false);
    }

    public void Top()
    {
        VBox pannelTop = new VBox(10);
        HBox boxTop = new HBox();
        
        ImageView logo = new ImageView(new Image("file:./img/logo.jpg"));
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        Label titre = new Label("Client");
        titre.setFont(new Font("Times new Roman", 50));
        titre.setPadding(new Insets(15, 15, 0, 0));
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

        Button store = new Button("");
        ImageView storeIcon = new ImageView(new Image("file:./img/store.png"));
        storeIcon.setFitHeight(40);
        storeIcon.setFitHeight(40);
        store.setGraphic(storeIcon);
        store.setPadding(new Insets(0));
        store.setPrefSize(50, 50);
        store.setOnAction(new ControleurChangerStore(this.app, this.modele));

        Button param = new Button("");
        ImageView paramIcon = new ImageView(new Image("file:./img/param.png"));
        paramIcon.setFitHeight(40);
        paramIcon.setFitWidth(40);
        param.setGraphic(paramIcon);
        param.setPadding(new Insets(0, 0, 0, 0));
        param.setPrefSize(50, 50);
        param.setOnAction(new ControleurAllerParametres(this.modele, this.app));
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
        deconnexion.setOnAction(new ControleurRetourAccueil(this.modele, this.app));

        Button panier = new Button();
        ImageView panierIcon = new ImageView(new Image("file:./img/panier.png"));
        panierIcon.setFitHeight(40);
        panierIcon.setFitWidth(40);
        panier.setGraphic(panierIcon);
        panier.setPadding(new Insets(0, 0, 0, 0));
        panier.setPrefWidth(50);
        panier.setPrefHeight(50);
        panier.setOnAction(new ControleurConsulterPanier(this.modele, this.app));
        

        boutonsDroite.setPadding(new Insets(30, 0, 0, 355));
        boutonsDroite.setAlignment(Pos.TOP_RIGHT);
        
        boutonsDroite.getChildren().addAll(store, deconnexion, param, panier);

        boxTop.setPadding(new Insets(3, 3, 3, 3));
        boxTop.setSpacing(10);
        boxTop.getChildren().addAll(logo, titre, nomUtil, boutonsDroite);

        HBox boxLigne = new HBox();
        Line sep = new Line(0, 0, 1100, 0);
        boxLigne.setPadding(new Insets(5, 0, 5, 100));
        boxLigne.getChildren().addAll(sep);

        HBox boxBot = new HBox(225);
        boxBot.setPadding(new Insets(0, 0, 0, 100));

        this.criteres.getItems().addAll(
            "Rechercher par nom de livre",
            "Rechercher par auteur",
            "Rechercher par classification",
            "Rechercher par éditeur", 
            "Rechecher par magasin",
            "Recommandations"
        );
        this.criteres.setOnAction(new ControleurChoixCriteres(this.app, this.modele, this.criteres));
        this.criteres.setPadding(new Insets(0, 0, 0, 15));
        this.criteres.setPromptText("Choisissez une action"); 

        this.recherche.setPadding(new Insets(5, 10, 5, 10));
        this.recherche.setMinWidth(300);
        this.recherche.setMaxWidth(300);
        boxBot.getChildren().addAll(this.criteres, this.recherche);

        pannelTop.getChildren().addAll(boxTop, boxLigne, boxBot);

        this.setTop(pannelTop);
    }

    public Alert popUpActionEffectuee()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Action effectuee !");
        return alert;
    }

    public Alert popUpChampsVides()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs");
        return alert;
    }

    public Alert popUpPasUnNbr()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer un nombre dans le code postal");
        return alert;
    }

    public void afficherPopUpLivre(Livre l)
    {
        Stage popup = new Stage();
        popup.setTitle(l.getTitre());
        
        BorderPane root = new BorderPane();

        Image imgBrut = null;
        try {
            imgBrut = new Image("https://covers.openlibrary.org/b/isbn/" + l.getISBN() + "-M.jpg", 200, 300, true, true, true);
            
            if (imgBrut.isError())
            {
                imgBrut = new Image("file:./img/coverBase.png", 200, 300, true, true, true);
            }
        }
        catch (Exception e)
        {
            imgBrut = new Image("file:./img/coverBase.png", 200, 300, true, true, true);   
        }
        ImageView img = new ImageView(imgBrut);
        img.setFitWidth(200);
        img.setFitHeight(300);
        
        img.setPreserveRatio(true);

        root.setLeft(img);

        VBox right = new VBox(10);
        right.setPadding(new Insets(20));
        Label titre = new Label(l.getTitre());
        titre.setStyle("-fx-font-weight : bold;" + 
        "-fx-font-size : 30px;");
    
        Label isbn = new Label("" + l.getISBN());
        titre.setStyle("-fx-font-size : 15px;");

        Label nbPages = new Label("Nombre de pages : " + l.getNbpages());
        nbPages.setStyle("-fx-font-size : 15px;");

        Label publi = new Label("Date de publication : " + l.getDatepubli());
        publi.setStyle("-fx-font-size : 15px;");

        Label prix = new Label("Prix : " + l.getPrix() + "€");
        prix.setStyle("-fx-font-size : 15px;");
        prix.setPadding(new Insets(0, 0, 100, 0));
        
        Button boutonAjouter = new Button("Ajouter au panier");
        boutonAjouter.setOnAction(new ControleurAjouterPanier(this.modele, this.app, l));
        right.getChildren().addAll(titre, isbn, nbPages, publi, prix, boutonAjouter);

        
        

        root.setRight(right);

        Scene sc = new Scene(root);
        sc.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");
        Image logo = new Image("file:./img/logo.jpg");
        popup.getIcons().add(logo);
        popup.setScene(sc);
        popup.show();

    }

    public void afficherPopUpLivrePanier(Livre l)
    {
        Stage popup = new Stage();
        popup.setTitle(l.getTitre());
        
        BorderPane root = new BorderPane();

        Image imgBrut = null;
        try {
            imgBrut = new Image("https://covers.openlibrary.org/b/isbn/" + l.getISBN() + "-M.jpg", 200, 300, true, true, true);
            
            if (imgBrut.isError())
            {
                imgBrut = new Image("file:./img/coverBase.png", 200, 300, true, true, true);
            }
        }
        catch (Exception e)
        {
            imgBrut = new Image("file:./img/coverBase.png", 200, 300, true, true, true);   
        }
        ImageView img = new ImageView(imgBrut);
        img.setFitWidth(200);
        img.setFitHeight(300);
        
        img.setPreserveRatio(true);

        root.setLeft(img);

        VBox right = new VBox(10);
        right.setPadding(new Insets(20));
        Label titre = new Label(l.getTitre());
        titre.setStyle("-fx-font-weight : bold;" + 
        "-fx-font-size : 30px;");
    
        Label isbn = new Label("" + l.getISBN());
        titre.setStyle("-fx-font-size : 15px;");

        Label nbPages = new Label("Nombre de pages : " + l.getNbpages());
        nbPages.setStyle("-fx-font-size : 15px;");

        Label publi = new Label("Date de publication : " + l.getDatepubli());
        publi.setStyle("-fx-font-size : 15px;");

        Label prix = new Label("Prix : " + l.getPrix() + "€");
        prix.setStyle("-fx-font-size : 15px;");
        prix.setPadding(new Insets(0, 0, 100, 0));
        
        Button boutonSuppr = new Button("Supprimer du Panier");
        boutonSuppr.setOnAction(new ControleurSupprimerPanier(this.modele, this.app, l));
        right.getChildren().addAll(titre, isbn, nbPages, publi, prix, boutonSuppr);

        
        

        root.setRight(right);

        Scene sc = new Scene(root);
        sc.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");
        Image logo = new Image("file:./img/logo.jpg");
        popup.getIcons().add(logo);
        popup.setScene(sc);
        popup.show();

    }

    public void panelChoixMag()
    {
        try
        {   
            this.titreCentre.setText("Choix Magasin");
            this.centre.getChildren().clear();
            for (Magasin m : this.modele.getAllMagasins())
            {
                VBox vb = new VBox();
                vb.setStyle("-fx-border-color : black;" + 
                "-fx-border-width : 2px;" + 
                "-fx-border-radius : 10px;" + 
                "-fx-background-color : #fcfcfc;" + 
                "-fx-background-radius : 10px;");
                HBox.setMargin(vb, new Insets(10));
                vb.setPrefSize(350, 100);
                vb.setMaxSize(350, 100);
                vb.setMinSize(350, 100);
                Label nom = new Label(m.getNomMag());
                nom.setFont(new Font(20));
                nom.setPadding(new Insets(20, 0, 0, 75));
                nom.setStyle("-fx-font-weight : bold;");
                Label ville = new Label(m.getVilleMag());
                ville.setFont(new Font(20));
                ville.setPadding(new Insets(10, 0, 20, 75));
                vb.getChildren().addAll(nom, ville);
                vb.setOnMouseClicked(new ControleurChosirMagasin(this.modele, this.app));
                this.centre.getChildren().add(vb);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

    public void setupRecommandations()
    {
        Label titre = new Label("Catalogue");
        titre.setFont(new Font("Times new Roman", 30));
        titre.setPadding(new Insets(0, 0, 0, 500));
        ProgressIndicator loading = new ProgressIndicator();
        VBox vb = new VBox();
        ScrollPane sp = new ScrollPane();
        sp.setPrefHeight(400);
        sp.setFocusTraversable(false);

        Task<List<Livre>> task = new Task<>() {
            @Override
            protected List<Livre> call() throws Exception
            {
                return getRecommandations();
            }
        };
        this.titreCentre.setText("Recommandations");
        this.centre.getChildren().clear();
        this.centre.getChildren().addAll(loading);
        loading.visibleProperty().bind(task.runningProperty());
        task.setOnSucceeded(e ->{
            panelAfficherLivres(task.getValue(), "Recommandations");
        });
        new Thread(task).start();
    }

    public List<Livre> getRecommandations()
    {
        try
        {
            return this.modele.onVousRecommande(this.client);
        }
        catch (Exception e)
        {
            return null;
        }
        
    }

    public void panelAfficherLivres(List<Livre> liste, String titre)
    {
        try
        {
            this.titreCentre.setText(titre);
            this.centre.getChildren().clear();
            for (Livre l : liste)
            {
                HBox hb = new HBox(20);
                hb.setStyle("-fx-border-color : black;" + 
                "-fx-border-width : 2px;" + 
                "-fx-border-radius : 10px;" + 
                "-fx-background-color : #fcfcfc;" + 
                "-fx-background-radius : 10px;");
                hb.setPrefSize(350, 100);
                hb.setMaxSize(350, 100);
                hb.setMinSize(350, 100);
                Image imgBrut = null;
                try {
                    imgBrut = new Image("https://covers.openlibrary.org/b/isbn/" + l.getISBN() + "-M.jpg", 60, 80, true, true, true);
                    
                    if (imgBrut.isError())
                    {
                        imgBrut = new Image("file:./img/coverBase.png", 60, 80, true, true, true);
                    }
                }
                catch (Exception e)
                {
                    imgBrut = new Image("file:./img/coverBase.png", 60, 80, true, true, true);   
                }
                ImageView img = new ImageView(imgBrut);
                img.setFitWidth(60);
                img.setFitHeight(80);
                
                img.setPreserveRatio(true);

                Label titreLivre = new Label(l.getTitre());
                titreLivre.setFont(new Font(20));

                hb.getChildren().addAll(img, titreLivre);
                hb.setPadding(new Insets(10));
                hb.setOnMouseClicked(new ControleurLookLivre(this.app, this.modele, l));
                this.centre.getChildren().add(hb);
            }
        }
        catch (Exception e)
        {
            System.out.println("Erreur");
        }
    }

    public ScrollPane LivresAuPanier()
    {
        ScrollPane sp = new ScrollPane();
        VBox vb = new VBox();
        for (Livre l : this.client.getPanier().keySet())
        {
            HBox hb = new HBox(20);
                hb.setStyle("-fx-border-color : black;" + 
                "-fx-border-width : 2px;" + 
                "-fx-border-radius : 10px;" + 
                "-fx-background-color : #fcfcfc;" + 
                "-fx-background-radius : 10px;");
                hb.setPrefSize(350, 100);
                hb.setMaxSize(350, 100);
                hb.setMinSize(350, 100);
                Image imgBrut = null;
                try {
                    imgBrut = new Image("https://covers.openlibrary.org/b/isbn/" + l.getISBN() + "-M.jpg", 60, 80, true, true, true);
                    
                    if (imgBrut.isError())
                    {
                        imgBrut = new Image("file:./img/coverBase.png", 60, 80, true, true, true);
                    }
                }
                catch (Exception e)
                {
                    imgBrut = new Image("file:./img/coverBase.png", 60, 80, true, true, true);   
                }
                ImageView img = new ImageView(imgBrut);
                img.setFitWidth(60);
                img.setFitHeight(80);
                
                img.setPreserveRatio(true);
                VBox droite = new VBox();
                Label titreLivre = new Label(l.getTitre());
                titreLivre.setFont(new Font(20));

                Label qteLivre = new Label("Qte : " + this.client.getPanier().get(l));
                qteLivre.setFont(new Font(20));

                droite.getChildren().addAll(titreLivre, qteLivre);

                hb.getChildren().addAll(img, droite);
                hb.setPadding(new Insets(10));
                hb.setOnMouseClicked(new ControleurLookLivrePanier(this.app, this.modele, l));
                vb.getChildren().add(hb);
            }
        sp.setContent(vb);
        return sp;
        }

    public void afficherPopUpPanier()
    {
        Stage popup = new Stage();
        popup.setTitle("Panier");
        BorderPane root = new BorderPane();
        
        VBox droite = new VBox(10);

        Integer nbArticle = this.client.getNbArticles();
        Double prix = this.client.getPrixTotal();

        Label nombreArticles = new Label("Nombre total d'articles : " + nbArticle);
        nombreArticles.setFont(new Font(20));

        Label prixTotal = new Label("Prix total du panier : " + prix + "€");
        prixTotal.setFont(new Font(20));

        if (prix > 0.0)
        {
            Button commanderPanier = new Button("Commander");
            commanderPanier.setOnAction(new ControleurAllerCommander(this.app, this.modele));

            droite.getChildren().addAll(nombreArticles, prixTotal, commanderPanier);
        }
        else
        {
            droite.getChildren().addAll(nombreArticles, prixTotal);
        }
        

        root.setLeft(this.LivresAuPanier());
        root.setRight(droite);
        root.setPrefSize(1300, 800);
        Scene sc = new Scene(root);
        sc.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");
        Image logo = new Image("file:./img/logo.jpg");
        popup.getIcons().add(logo);
        popup.setScene(sc);
        popup.show();

    }

    public void afficherPopUpParametres()
    {
        Stage popup = new Stage();
        popup.setTitle("Parametres");
        BorderPane root = new BorderPane();
        
        //mdp
        VBox gauche = new VBox(10);
        gauche.setPadding(new Insets(10));
        Label titreGauche = new Label("Changement mot de passe");
        titreGauche.setFont(new Font(20));
        titreGauche.setPadding(new Insets(0, 0, 0, 0));
        titreGauche.setStyle("-fx-font-weight : bold;");

        Label nvMdp = new Label("Nouveau mot de passe");
        nvMdp.setFont(new Font(15));
        nvMdp.setPadding(new Insets(20, 0, 0, 10));

        VBox.setMargin(this.pwField, new Insets(10, 0, 200, 0));
        Button bMdp = new Button("Changer Mot de Passe");
        bMdp.setOnAction(new ControleurChangerMdp(this.modele, this));
        VBox.setMargin(bMdp, new Insets(0, 100, 0, 100));
        gauche.setAlignment(Pos.TOP_CENTER);
        gauche.getChildren().addAll(titreGauche, nvMdp, this.pwField, bMdp);
        
        VBox droite = new VBox(10);
        droite.setPadding(new Insets(10));
        Label titreDroite = new Label("Changement d'adresse");
        titreDroite.setFont(new Font(20));
        titreDroite.setPadding(new Insets(0, 0, 0, 0));
        titreDroite.setStyle("-fx-font-weight : bold;");

        Label lbVille = new Label("Ville");
        lbVille.setFont(new Font(15));
        lbVille.setPadding(new Insets(20, 0, 0, 10));
        
        Label lbAdresse = new Label("Adresse");
        lbAdresse.setFont(new Font(15));
        lbAdresse.setPadding(new Insets(20, 0, 0, 10));

        Label lbCodePostal = new Label("Code Postal");
        lbCodePostal.setFont(new Font(15));
        lbCodePostal.setPadding(new Insets(20, 0, 0, 10));

        Button bAdresse = new Button("Changer adresse");
        bAdresse.setOnAction(new ControleurChangerAdresse(this.modele, this.app));
        VBox.setMargin(bAdresse, new Insets(35, 100, 0, 120));
        droite.setAlignment(Pos.TOP_CENTER);
        droite.getChildren().addAll(titreDroite, lbVille, tfVille, lbAdresse, tfAdresse, lbCodePostal, tfCodePostal, bAdresse);

        root.setLeft(gauche);
        root.setRight(droite);

        root.setPrefSize(1300, 400);
        Scene sc = new Scene(root);
        sc.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");
        Image logo = new Image("file:./img/logo.jpg");
        popup.getIcons().add(logo);
        popup.setScene(sc);
        popup.show();
    }

    public void afficherPopUpCommander()
    {
        Stage popup = new Stage();
        popup.setTitle("Parametres");
        BorderPane root = new BorderPane();
        
        VBox gauche = new VBox(10);

        Integer nbArticle = this.client.getNbArticles();
        Double prix = this.client.getPrixTotal();

        Label nombreArticles = new Label("Nombre total d'articles : " + nbArticle);
        nombreArticles.setFont(new Font(20));

        Label prixTotal = new Label("Prix total du panier : " + prix + "€");
        prixTotal.setFont(new Font(20));



        this.livraison.getItems().addAll("A Domicile",
        "En magasin");
        
        this.livraison.setPromptText("Choisissez une action"); 
        Commande c = null;
        try 
        {
            c = new Commande(0, this.modele);
            c.setEnLigne("O");
            c.setLivraison(liv);
            c.setPanier(this.client.getPanier());
            c.setDate(this.modele.getCurrentDate());
        }
        
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
        
        Button commander = new Button("Commander");
        commander.setOnAction(new ControleurPasserCommandeCli(this.app, this.modele, c));
    
        gauche.getChildren().addAll(nombreArticles, prixTotal, this.livraison);

        root.setLeft(gauche);
        root.setRight(commander);

        root.setPrefSize(600, 400);
        Scene sc = new Scene(root);
        sc.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");
        Image logo = new Image("file:./img/logo.jpg");
        popup.getIcons().add(logo);
        popup.setScene(sc);
        popup.show();
    }

}
