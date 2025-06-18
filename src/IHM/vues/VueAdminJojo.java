/* package IHM.vues;

import java.util.HashMap;
import java.util.Map;

import IHM.controleurs.ControleurAcceuil.controleurAccueil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.BD.ActionBD;
import main.app.Administrateur;
import main.app.Magasin;
import IHM.controleurs.ControleurAdmin.*;

public class VueAdminJojo extends StackPane
{
    private Administrateur admin;
    private ActionBD modele;
    private LivreExpress app;

    // Attributs pour tous les TextField et PasswordField
    private TextField tfEmail = new TextField();
    private PasswordField pfMDP = new PasswordField();
    private TextField tfNom = new TextField();
    private TextField tfPrenom = new TextField();
    private TextField tfIdMag = new TextField();
    private TextField tfNomLib = new TextField();
    private TextField tfVilleLib = new TextField();
    private TextField tfISBN = new TextField();
    private TextField tfTitre = new TextField();
    private TextField tfAuteur = new TextField();
    private TextField tfEditeur = new TextField();
    private TextField tfPrix = new TextField();
    private TextField tfNbPages = new TextField();
    private TextField tfLivre = new TextField();
    private TextField tfMagasin = new TextField();
    private TextField tfClient = new TextField();
    private TextField tfMagDep = new TextField();
    private TextField tfMagArr = new TextField();
    private TextField tfQte = new TextField();
    private TextField tfMois = new TextField();
    private TextField tfAnnee = new TextField();

    // Autres attributs utiles
    private  Button boutonParametres = new Button();
    private  Button boutonMaison = new Button();
    private  Button boutonDeco = new Button();

    private BorderPane carre;
    private VBox top;

    private ComboBox<String> selectionGra;
    private TextField parametreGraphique;
    
    public ComboBox<String> getSelectionGra() {
        return this.selectionGra;
    }

    public TextField getParametreGraphique() {
        return this.parametreGraphique;
    }
    public VueAdminJojo(LivreExpress app, ActionBD modele, Administrateur admin) {
        this.admin = admin;
        this.app = app;

        this.modele=modele;
        this.carre = createCadre();
        this.top = createTop();

        // Initialisation des TextField et PasswordField
        tfEmail.setPromptText("Email");
        pfMDP.setPromptText("Mot de passe");
        tfNom.setPromptText("Nom");
        tfPrenom.setPromptText("Prénom");
        tfIdMag.setPromptText("ID Magasin");
        tfNomLib.setPromptText("Nom Librairie");
        tfVilleLib.setPromptText("Ville Librairie");
        tfISBN.setPromptText("ISBN");
        tfTitre.setPromptText("Titre");
        tfAuteur.setPromptText("Auteur");
        tfEditeur.setPromptText("Éditeur");
        tfPrix.setPromptText("Prix");
        tfNbPages.setPromptText("Nombre de pages");
        tfLivre.setPromptText("Titre du livre");
        tfMagasin.setPromptText("Magasin");
        tfClient.setPromptText("Client");
        tfMagDep.setPromptText("Magasin de départ");
        tfMagArr.setPromptText("Magasin d'arrivée");
        tfQte.setPromptText("Quantité");
        tfMois.setPromptText("Mois");
        tfAnnee.setPromptText("Année");

        boutonMaison.setGraphic(new ImageView("file:./img/deco.png"));
        boutonMaison.setPrefSize(50, 20);
        boutonParametres.setGraphic(new ImageView("file:./img/param.png"));
        boutonParametres.setPrefSize(50, 20);

        super.getChildren().addAll(carre, top);
        StackPane.setAlignment(this.top, Pos.TOP_LEFT);
    }

    // Getters pour tous les champs
    public TextField getTfEmail() { return tfEmail; }
    public PasswordField getPfMDP() { return pfMDP; }
    public TextField getTfNom() { return tfNom; }
    public TextField getTfPrenom() { return tfPrenom; }
    public TextField getTfIdMag() { return tfIdMag; }
    public TextField getTfNomLib() { return tfNomLib; }
    public TextField getTfVilleLib() { return tfVilleLib; }
    public TextField getTfISBN() { return tfISBN; }
    public TextField getTfTitre() { return tfTitre; }
    public TextField getTfAuteur() { return tfAuteur; }
    public TextField getTfEditeur() { return tfEditeur; }
    public TextField getTfPrix() { return tfPrix; }
    public TextField getTfNbPages() { return tfNbPages; }
    public TextField getTfLivre() { return tfLivre; }
    public TextField getTfMagasin() { return tfMagasin; }
    public TextField getTfClient() { return tfClient; }
    public TextField getTfMagDep() { return tfMagDep; }
    public TextField getTfMagArr() { return tfMagArr; }
    public TextField getTfQte() { return tfQte; }
    public TextField getTfMois() { return tfMois; }
    public TextField getTfAnnee() { return tfAnnee; }

    // Méthodes de création de vues
    public void creerVendeur() {
        this.carre = createCadre();
        GridPane grid = new GridPane();
        grid.setMaxSize(500, 520);
        grid.setHgap(20);
        grid.setVgap(15);

        String labelStyle = "-fx-text-fill: white; -fx-font-size: 16px;";

        Label titreForm = new Label("Creer Vendeur");
        titreForm.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        titreForm.setPadding(new Insets(0, 0, 30, 0));
        grid.add(titreForm, 0, 0, 2, 1);

        Label lblEmail = new Label("Email:");
        lblEmail.setStyle(labelStyle);
        grid.add(lblEmail, 0, 1);
        tfEmail.setPrefWidth(300); tfEmail.setPrefHeight(40);
        grid.add(tfEmail, 1, 1);

        Label lblNom = new Label("Nom:");
        lblNom.setStyle(labelStyle);
        grid.add(lblNom, 0, 2);
        tfNom.setPrefWidth(300); tfNom.setPrefHeight(40);
        grid.add(tfNom, 1, 2);

        Label lblPrenom = new Label("Prénom:");
        lblPrenom.setStyle(labelStyle);
        grid.add(lblPrenom, 0, 3);
        tfPrenom.setPrefWidth(300); tfPrenom.setPrefHeight(40);
        grid.add(tfPrenom, 1, 3);

        Label lblIdMag = new Label("ID Magasin:");
        lblIdMag.setStyle(labelStyle);
        grid.add(lblIdMag, 0, 4);
        tfIdMag.setPrefWidth(300); tfIdMag.setPrefHeight(40);
        grid.add(tfIdMag, 1, 4);

        Label lblMDP = new Label("Mot de passe:");
        lblMDP.setStyle(labelStyle);
        grid.add(lblMDP, 0, 5);
        pfMDP.setPrefWidth(300); pfMDP.setPrefHeight(40);
        grid.add(pfMDP, 1, 5);

        Button btnCreer = new Button("Creer");
        btnCreer.setPrefWidth(120); btnCreer.setPrefHeight(40);
        HBox boutonBox = new HBox(btnCreer);
        boutonBox.setAlignment(Pos.BOTTOM_RIGHT);
        boutonBox.setPadding(new Insets(30, 0, 0, 0));
        grid.add(boutonBox, 1, 6);

        this.carre.setCenter(grid);
        this.getChildren().setAll(carre, top);
    }

    public void ajouterLibrairie() {
        this.carre = createCadre();
        GridPane grid = new GridPane();
        grid.setMaxSize(500, 400);
        grid.setHgap(20);
        grid.setVgap(15);

        String labelStyle = "-fx-text-fill: white; -fx-font-size: 16px;";

        Label titreForm = new Label("Ajouter une librairie");
        titreForm.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        titreForm.setPadding(new Insets(0, 0, 30, 0));
        grid.add(titreForm, 0, 0, 2, 1);

        Label lblNom = new Label("Nom :");
        lblNom.setStyle(labelStyle);
        grid.add(lblNom, 0, 1);
        tfNomLib.setPrefWidth(300); tfNomLib.setPrefHeight(40);
        grid.add(tfNomLib, 1, 1);

        Label lblVille = new Label("Ville :");
        lblVille.setStyle(labelStyle);
        grid.add(lblVille, 0, 2);
        tfVilleLib.setPrefWidth(300); tfVilleLib.setPrefHeight(40);
        grid.add(tfVilleLib, 1, 2);

        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setPrefWidth(120); btnAjouter.setPrefHeight(40);
        HBox boutonBox = new HBox(btnAjouter);
        boutonBox.setAlignment(Pos.BOTTOM_RIGHT);
        boutonBox.setPadding(new Insets(30, 0, 0, 0));
        grid.add(boutonBox, 1, 3);

        this.carre.setCenter(grid);
        this.getChildren().setAll(carre, top);
    }

    public void panneauDeBord() {
        this.carre = createCadre();
        VBox vbox = new VBox(20);
        this.selectionGra = new ComboBox<>();
        selectionGra.getItems().addAll(
            "NombreDeLivreVendueParMagasinParAns",
            "chiffreAffaireParClassificationParAns",
            "CAMagasinParMoisParAnnee",
            "CAVenteEnLigneEnMagasinParAnnee",
            "nombreAuteurParEditeur",
            "nombreClientParVilleQuiOntAcheterAuteur",
            "valeurStockMagasin",
            "statsCAParClientParAnnee",
            "auteurLePlusVenduParAnnee"
        );
        selectionGra.setPromptText("Choisir une statistique");
        vbox.getChildren().add(selectionGra);
        vbox.setAlignment(Pos.CENTER);
        Label titre = new Label("Panneau de Bord");
        titre.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        vbox.getChildren().add(titre);
        this.carre.setCenter(vbox);
        this.getChildren().setAll(carre, top);
    }

    public void ajouterLivre() {
        this.carre = createCadre();
        GridPane grid = new GridPane();
        grid.setMaxSize(600, 500);
        grid.setHgap(20);
        grid.setVgap(15);

        String labelStyle = "-fx-text-fill: white; -fx-font-size: 16px;";

        Label titreForm = new Label("Ajouter un livre");
        titreForm.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        titreForm.setPadding(new Insets(0, 0, 30, 0));
        grid.add(titreForm, 0, 0, 2, 1);

        Label lblISBN = new Label("ISBN :");
        lblISBN.setStyle(labelStyle);
        grid.add(lblISBN, 0, 1);
        tfISBN.setPrefWidth(300); tfISBN.setPrefHeight(40);
        grid.add(tfISBN, 1, 1);

        Label lblTitre = new Label("Titre :");
        lblTitre.setStyle(labelStyle);
        grid.add(lblTitre, 0, 2);
        tfTitre.setPrefWidth(300); tfTitre.setPrefHeight(40);
        grid.add(tfTitre, 1, 2);

        Label lblAuteur = new Label("Auteur :");
        lblAuteur.setStyle(labelStyle);
        grid.add(lblAuteur, 0, 3);
        tfAuteur.setPrefWidth(300); tfAuteur.setPrefHeight(40);
        grid.add(tfAuteur, 1, 3);

        Label lblEditeur = new Label("Éditeur :");
        lblEditeur.setStyle(labelStyle);
        grid.add(lblEditeur, 0, 4);
        tfEditeur.setPrefWidth(300); tfEditeur.setPrefHeight(40);
        grid.add(tfEditeur, 1, 4);

        Label lblPrix = new Label("Prix :");
        lblPrix.setStyle(labelStyle);
        grid.add(lblPrix, 0, 5);
        tfPrix.setPrefWidth(300); tfPrix.setPrefHeight(40);
        grid.add(tfPrix, 1, 5);

        Label lblNbPages = new Label("Nombre de pages :");
        lblNbPages.setStyle(labelStyle);
        grid.add(lblNbPages, 0, 6);
        tfNbPages.setPrefWidth(300); tfNbPages.setPrefHeight(40);
        grid.add(tfNbPages, 1, 6);

        Label lblDate = new Label("Date de publication :");
        lblDate.setStyle(labelStyle);
        DatePicker dpDate = new DatePicker();
        grid.add(lblDate, 0, 7);
        grid.add(dpDate, 1, 7);

        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setPrefWidth(120); btnAjouter.setPrefHeight(40);
        HBox boutonBox = new HBox(btnAjouter);
        boutonBox.setAlignment(Pos.BOTTOM_RIGHT);
        boutonBox.setPadding(new Insets(30, 0, 0, 0));
        grid.add(boutonBox, 1, 8);

        this.carre.setCenter(grid);
        this.getChildren().setAll(carre, top);
    }

    public void regarderDisponibilites() {
        this.carre = createCadre();
        GridPane grid = new GridPane();
        grid.setMaxSize(500, 300);
        grid.setHgap(20);
        grid.setVgap(15);

        String labelStyle = "-fx-text-fill: white; -fx-font-size: 16px;";

        Label titreForm = new Label("Regarder les disponibilités");
        titreForm.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        titreForm.setPadding(new Insets(0, 0, 30, 0));
        grid.add(titreForm, 0, 0, 2, 1);

        Label lblLivre = new Label("Titre du livre :");
        lblLivre.setStyle(labelStyle);
        grid.add(lblLivre, 0, 1);
        tfLivre.setPrefWidth(300); tfLivre.setPrefHeight(40);
        grid.add(tfLivre, 1, 1);

        Label lblMagasin = new Label("Magasin :");
        lblMagasin.setStyle(labelStyle);
        grid.add(lblMagasin, 0, 2);
        tfMagasin.setPrefWidth(300); tfMagasin.setPrefHeight(40);
        grid.add(tfMagasin, 1, 2);

        Button btnRechercher = new Button("Rechercher");
        btnRechercher.setPrefWidth(120); btnRechercher.setPrefHeight(40);
        HBox boutonBox = new HBox(btnRechercher);
        boutonBox.setAlignment(Pos.BOTTOM_RIGHT);
        boutonBox.setPadding(new Insets(30, 0, 0, 0));
        grid.add(boutonBox, 1, 3);

        this.carre.setCenter(grid);
        this.getChildren().setAll(carre, top);
    }

    public void passerCommandeClient() {
        this.carre = createCadre();
        GridPane grid = new GridPane();
        grid.setMaxSize(600, 400);
        grid.setHgap(20);
        grid.setVgap(15);

        String labelStyle = "-fx-text-fill: white; -fx-font-size: 16px;";

        Label titreForm = new Label("Passer une commande pour un Client");
        titreForm.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        titreForm.setPadding(new Insets(0, 0, 30, 0));
        grid.add(titreForm, 0, 0, 2, 1);

        Label lblClient = new Label("Client :");
        lblClient.setStyle(labelStyle);
        grid.add(lblClient, 0, 1);
        tfClient.setPrefWidth(300); tfClient.setPrefHeight(40);
        grid.add(tfClient, 1, 1);

        Label lblLivre = new Label("Livre :");
        lblLivre.setStyle(labelStyle);
        grid.add(lblLivre, 0, 2);
        tfLivre.setPrefWidth(300); tfLivre.setPrefHeight(40);
        grid.add(tfLivre, 1, 2);

        Label lblMagasin = new Label("Magasin :");
        lblMagasin.setStyle(labelStyle);
        grid.add(lblMagasin, 0, 3);
        tfMagasin.setPrefWidth(300); tfMagasin.setPrefHeight(40);
        grid.add(tfMagasin, 1, 3);

        Button btnCommander = new Button("Commander");
        btnCommander.setPrefWidth(120); btnCommander.setPrefHeight(40);
        HBox boutonBox = new HBox(btnCommander);
        boutonBox.setAlignment(Pos.BOTTOM_RIGHT);
        boutonBox.setPadding(new Insets(30, 0, 0, 0));
        grid.add(boutonBox, 1, 4);

        this.carre.setCenter(grid);
        this.getChildren().setAll(carre, top);
    }

    public void transfererLivre() {
        this.carre = createCadre();
        GridPane grid = new GridPane();
        grid.setMaxSize(600, 400);
        grid.setHgap(20);
        grid.setVgap(15);

        String labelStyle = "-fx-text-fill: white; -fx-font-size: 16px;";

        Label titreForm = new Label("Transférer un livre");
        titreForm.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        titreForm.setPadding(new Insets(0, 0, 30, 0));
        grid.add(titreForm, 0, 0, 2, 1);

        Label lblISBN = new Label("ISBN :");
        lblISBN.setStyle(labelStyle);
        grid.add(lblISBN, 0, 1);
        tfISBN.setPrefWidth(300); tfISBN.setPrefHeight(40);
        grid.add(tfISBN, 1, 1);

        Label lblMagasinDep = new Label("Magasin de départ :");
        lblMagasinDep.setStyle(labelStyle);
        grid.add(lblMagasinDep, 0, 2);
        tfMagDep.setPrefWidth(300); tfMagDep.setPrefHeight(40);
        grid.add(tfMagDep, 1, 2);

        Label lblMagasinArr = new Label("Magasin d'arrivée :");
        lblMagasinArr.setStyle(labelStyle);
        grid.add(lblMagasinArr, 0, 3);
        tfMagArr.setPrefWidth(300); tfMagArr.setPrefHeight(40);
        grid.add(tfMagArr, 1, 3);

        Label lblQte = new Label("Quantité :");
        lblQte.setStyle(labelStyle);
        grid.add(lblQte, 0, 4);
        tfQte.setPrefWidth(300); tfQte.setPrefHeight(40);
        grid.add(tfQte, 1, 4);

        Button btnTransferer = new Button("Transférer");
        btnTransferer.setPrefWidth(120); btnTransferer.setPrefHeight(40);
        HBox boutonBox = new HBox(btnTransferer);
        boutonBox.setAlignment(Pos.BOTTOM_RIGHT);
        boutonBox.setPadding(new Insets(30, 0, 0, 0));
        grid.add(boutonBox, 1, 5);

        this.carre.setCenter(grid);
        this.getChildren().setAll(carre, top);
    }

    public void obtenirFactures() {
        this.carre = createCadre();
        GridPane grid = new GridPane();
        grid.setMaxSize(500, 300);
        grid.setHgap(20);
        grid.setVgap(15);

        String labelStyle = "-fx-text-fill: white; -fx-font-size: 16px;";

        Label titreForm = new Label("Obtenir les factures");
        titreForm.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        titreForm.setPadding(new Insets(0, 0, 30, 0));
        grid.add(titreForm, 0, 0, 2, 1);

        Label lblClient = new Label("Client :");
        lblClient.setStyle(labelStyle);
        grid.add(lblClient, 0, 1);
        tfClient.setPrefWidth(300); tfClient.setPrefHeight(40);
        grid.add(tfClient, 1, 1);

        Label lblMagasin = new Label("Magasin :");
        lblMagasin.setStyle(labelStyle);
        grid.add(lblMagasin, 0, 2);
        tfMagasin.setPrefWidth(300); tfMagasin.setPrefHeight(40);
        grid.add(tfMagasin, 1, 2);

        Label lblMois = new Label("Mois :");
        lblMois.setStyle(labelStyle);
        grid.add(lblMois, 0, 3);
        tfMois.setPrefWidth(300); tfMois.setPrefHeight(40);
        grid.add(tfMois, 1, 3);

        Label lblAnnee = new Label("Année :");
        lblAnnee.setStyle(labelStyle);
        grid.add(lblAnnee, 0, 4);
        tfAnnee.setPrefWidth(300); tfAnnee.setPrefHeight(40);
        grid.add(tfAnnee, 1, 4);

        Button btnObtenir = new Button("Obtenir");
        btnObtenir.setPrefWidth(120); btnObtenir.setPrefHeight(40);
        HBox boutonBox = new HBox(btnObtenir);
        boutonBox.setAlignment(Pos.BOTTOM_RIGHT);
        boutonBox.setPadding(new Insets(30, 0, 0, 0));
        grid.add(boutonBox, 1, 5);

        this.carre.setCenter(grid);
        this.getChildren().setAll(carre, top);
    }

    public void choisirMagasin() {
        this.carre = createCadre();
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Label titre = new Label("Choisir un magasin");
        titre.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");

        ComboBox<String> cbMagasins = new ComboBox<>();
        cbMagasins.setPromptText("Sélectionner un magasin");

        Button btnChoisir = new Button("Choisir");
        btnChoisir.setPrefWidth(120); btnChoisir.setPrefHeight(40);

        vbox.getChildren().addAll(titre, cbMagasins, btnChoisir);

        this.carre.setCenter(vbox);
        this.getChildren().setAll(carre, top);
    }

    private VBox createTop() {
        VBox banniere = new VBox();
        HBox ligne = new HBox();
        HBox boutonsFixes = new HBox(25);
        boutonsFixes.setPadding(new Insets(25, 0, 0, 0));

        VBox contenuMenu = new VBox(15);

        // Création des boutons dans une liste
        Button btnCreerVendeur = new Button("Creer un vendeur");
        Button btnAjouterLibrairie = new Button("Ajouter une librairie");
        Button btnPanneauDeBord = new Button("Panneau de Bord");
        Button btnAjouterLivre = new Button("Ajouter un livre");
        Button btnRegarderDispo = new Button("Regarder les disponibilités");
        Button btnPasserCommande = new Button("Passer une commande pour un Client");
        Button btnTransfererLivre = new Button("Tranferer un livre");
        Button btnObtenirFactures = new Button("Obtenir les factures");
        Button btnChoisirMagasin = new Button("Choisir Un magasin");

        Button[] boutons = {
            btnCreerVendeur,
            btnAjouterLibrairie,
            btnPanneauDeBord,
            btnAjouterLivre,
            btnRegarderDispo,
            btnPasserCommande,
            btnTransfererLivre,
            btnObtenirFactures,
            btnChoisirMagasin
        };

        for (Button btn : boutons) {
            btn.setOnAction(new ControleurChangerPage(this.modele, this.app));
            contenuMenu.getChildren().add(btn);
        }

        TitledPane menuDeroulant = new TitledPane("Liste Des Commandes", contenuMenu);
        menuDeroulant.setExpanded(false);
        menuDeroulant.setMaxWidth(450);
        contenuMenu.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        ImageView logo = new ImageView("file:./img/logo.jpg");
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        Rectangle bordure = new Rectangle(logo.getFitWidth(), logo.getFitHeight());
        bordure.setArcWidth(30);
        bordure.setArcHeight(30);
        logo.setClip(bordure);

        Label titre = new Label("Livre Express - Admin");
        titre.setFont(new Font("Times New Roman", 60));
        titre.setStyle("-fx-text-fill: #3f4353");
        titre.setPadding(new Insets(0, 500, 0, 15));
        titre.setTextFill(Color.BLACK);
        titre.setAlignment(Pos.TOP_LEFT);

        boutonsFixes.getChildren().addAll(boutonMaison, boutonParametres);

        ligne.getChildren().addAll(logo, titre, boutonsFixes);
        boutonsFixes.setPadding(new Insets(0,0,0,450));
        ligne.setAlignment(Pos.CENTER_LEFT);

        Line sep = new Line(0, 0, 1700, 0);
        sep.setStrokeWidth(2);
        sep.setStroke(Color.BLACK);
        HBox boxLigne = new HBox(sep);
        boxLigne.setPadding(new Insets(10, 0, 10, 100));

        banniere.getChildren().addAll(ligne, boxLigne, menuDeroulant);
        banniere.setPadding(new Insets(25, 0, 0, 0));
        VBox.setMargin(menuDeroulant, new Insets(0, 0, 0, 40));

        return banniere;
    }

    private BorderPane createCadre() {
        BorderPane mid = new BorderPane();
        mid.setMaxSize(1300, 650);
        mid.setStyle("-fx-background-color: grey;");
        return mid;
    }



    
    public void afficheGraphiqueNombreDeLivreVendueParMagasinParAns(HashMap<Integer, HashMap<Magasin, Integer>> donnees)
    {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Magasin");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de livres vendus");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Nombre de livres vendus par magasin");


        for (Map.Entry<Integer, HashMap<Magasin, Integer>> entreeAnnee : donnees.entrySet()) 
        {
            Integer annee = entreeAnnee.getKey();
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(annee.toString());

            for (Map.Entry<Magasin, Integer> entreeMagasin : entreeAnnee.getValue().entrySet()) 
            {
                Magasin magasin = entreeMagasin.getKey();
                Integer nbLivres = entreeMagasin.getValue();
                serie.getData().add(new XYChart.Data<>(magasin.getNomMag(), nbLivres));
            }

            barChart.getData().add(serie);
        }
    }
}*/
