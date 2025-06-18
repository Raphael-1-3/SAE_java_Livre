package IHM.vues;

import javax.swing.Action;

import IHM.controleurs.ControleurAdmin.*;

import IHM.controleurs.ControleurAdmin.ControleurChangerPage;

import javafx.geometry.Insets;

import javafx.geometry.Pos;

import javafx.scene.Node;

import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.*;

import javafx.scene.paint.Color;

import javafx.scene.shape.Line;

import javafx.scene.shape.Rectangle;

import javafx.scene.text.Font;

import main.BD.ActionBD;
import main.app.Administrateur;

public class VueAdmin extends StackPane 
{
    private Administrateur admin;
     private ActionBD modele;

    private final Button boutonParametres = new Button();

    private final Button boutonMaison = new Button();

    private BorderPane carre;

    private VBox top;
    private TextField tfEmail;
    private PasswordField pfMDP;
    private TextField tfNom;
    private TextField tfPrenom;
    private TextField tfIdMag;


    public VueAdmin(LivreExpress app, ActionBD modele, Administrateur admin) {
        this.admin = admin;

        this.modele=modele;

        this.carre = createCadre();

        this.top = createTop();
        boutonMaison.setGraphic(new ImageView("file:./img/deco.png"));


        boutonMaison.setPrefSize(50, 20);
        // Initialisation des TextField et PasswordField
        tfEmail = new TextField();
        tfEmail.setPromptText("Email");
        pfMDP = new PasswordField();
        pfMDP.setPromptText("Mot de passe");
        tfNom = new TextField();
        tfNom.setPromptText("Nom");
        tfPrenom = new TextField();
        tfPrenom.setPromptText("Prénom");
        tfIdMag = new TextField();
        tfIdMag.setPromptText("ID Magasin");

        boutonParametres.setGraphic(new ImageView("file:./img/param.png"));

        boutonParametres.setPrefSize(50, 20);

        top.setMaxHeight(2);

        super.getChildren().addAll(carre,top);

        StackPane.setAlignment(this.top, Pos.TOP_LEFT);

    }

    public TextField getTfEmail() {
        return tfEmail;}

    public PasswordField getPfMDP() {
        return pfMDP;}

    public TextField getTfNom() {
        return tfNom;}

    public TextField getTfPrenom() {
        return tfPrenom;}

    public TextField getTfIdMag() {
        return tfIdMag;}

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

        tfEmail.setPrefWidth(300);
        tfEmail.setPrefHeight(40);
        grid.add(this.tfEmail, 1, 1);

        Label lblNom = new Label("Nom:");
        lblNom.setStyle(labelStyle);
        grid.add(lblNom, 0, 2);        tfNom.setPrefWidth(300);

        tfNom.setPrefHeight(40);
        grid.add(this.tfNom, 1, 2);
        Label lblPrenom = new Label("Prénom:");

        lblPrenom.setStyle(labelStyle);

        grid.add(lblPrenom, 0, 3);

        tfPrenom.setPrefWidth(300);


        tfPrenom.setPrefHeight(40);


        grid.add(this.tfPrenom, 1, 3);



        Label lblIdMag = new Label("ID Magasin:");

        lblIdMag.setStyle(labelStyle);

        grid.add(lblIdMag, 0, 4);





        tfIdMag.setPrefWidth(300);


        tfIdMag.setPrefHeight(40);


        grid.add(this.tfIdMag, 1, 4);



        Label lblMDP = new Label("Mot de passe:");

        lblMDP.setStyle(labelStyle);

        grid.add(lblMDP, 0, 5);






        pfMDP.setPrefWidth(300);


        pfMDP.setPrefHeight(40);


        grid.add(this.pfMDP, 1, 5);





        // Bouton "Creer" en bas à droite

        Button btnCreer = new Button("Creer");


        btnCreer.setPrefWidth(120);


        btnCreer.setPrefHeight(40);

        HBox boutonBox = new HBox(btnCreer);

        boutonBox.setAlignment(Pos.BOTTOM_RIGHT);

        boutonBox.setPadding(new Insets(30, 0, 0, 0));

        grid.add(boutonBox, 1, 6);



        this.carre.setCenter(grid);

        this.getChildren().setAll(carre, top);

    }

private VBox createTop() {


    VBox banniere = new VBox();


    HBox ligne = new HBox();


    HBox boutonsFixes = new HBox(25);


    boutonsFixes.setPadding(new Insets(25, 0, 0, 0));





    // Menu déroulant avec toutes les actions demandées


    VBox contenuMenu = new VBox(15);


    contenuMenu.getChildren().addAll(


        new Button("Creer un vendeur"),


        new Button("Ajouter une librairie"),


        new Button("Panneau de Bord"),


        new Button("Ajouter un livre"),


        new Button("Regarder les disponibilités"),


        new Button("Passer une commande pour un Client"),


        new Button("Tranferer un livre"),


        new Button("Obtenir les factures"),


        new Button("Choisir Un magasin")


    );





    TitledPane menuDeroulant = new TitledPane("Liste Des Commadnes", contenuMenu);


    menuDeroulant.setExpanded(false);


    menuDeroulant.setMaxWidth(450);


    contenuMenu.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));





    // Logo et titre


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


    ControleurChangerPage controleur = new ControleurChangerPage(modele, this);


        for (Node node : contenuMenu.getChildren()) {


            if (node instanceof Button) {


                ((Button) node).addEventHandler(MouseEvent.MOUSE_CLICKED, controleur);


    }}

    return banniere;


}

    private BorderPane createCadre() {

        BorderPane mid = new BorderPane();

        mid.setMaxSize(1300, 650);

        mid.setStyle("-fx-background-color: grey;");

        return mid;

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


    TextField tfNomLib = new TextField();


    grid.add(lblNom, 0, 1);


    grid.add(tfNomLib, 1, 1);





    Label lblVille = new Label("Ville :");


    lblVille.setStyle(labelStyle);


    TextField tfVilleLib = new TextField();


    grid.add(lblVille, 0, 2);


    grid.add(tfVilleLib, 1, 2);





    Button btnAjouter = new Button("Ajouter");


    btnAjouter.setPrefWidth(120);


    btnAjouter.setPrefHeight(40);


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


    vbox.setAlignment(Pos.CENTER);


    Label titre = new Label("Panneau de Bord");


    titre.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");


    vbox.getChildren().add(titre);


    // Ajoute ici les éléments de dashboard/statistiques (tableaux, graphiques, etc.)


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


    TextField tfISBN = new TextField();


    grid.add(lblISBN, 0, 1);


    grid.add(tfISBN, 1, 1);





    Label lblTitre = new Label("Titre :");


    lblTitre.setStyle(labelStyle);


    TextField tfTitre = new TextField();


    grid.add(lblTitre, 0, 2);


    grid.add(tfTitre, 1, 2);





    Label lblAuteur = new Label("Auteur :");


    lblAuteur.setStyle(labelStyle);


    TextField tfAuteur = new TextField();


    grid.add(lblAuteur, 0, 3);


    grid.add(tfAuteur, 1, 3);





    Label lblEditeur = new Label("Éditeur :");


    lblEditeur.setStyle(labelStyle);


    TextField tfEditeur = new TextField();


    grid.add(lblEditeur, 0, 4);


    grid.add(tfEditeur, 1, 4);





    Label lblPrix = new Label("Prix :");


    lblPrix.setStyle(labelStyle);


    TextField tfPrix = new TextField();


    grid.add(lblPrix, 0, 5);


    grid.add(tfPrix, 1, 5);





    Label lblNbPages = new Label("Nombre de pages :");


    lblNbPages.setStyle(labelStyle);


    TextField tfNbPages = new TextField();


    grid.add(lblNbPages, 0, 6);


    grid.add(tfNbPages, 1, 6);





    Label lblDate = new Label("Date de publication :");


    lblDate.setStyle(labelStyle);


    DatePicker dpDate = new DatePicker();


    grid.add(lblDate, 0, 7);


    grid.add(dpDate, 1, 7);





    Button btnAjouter = new Button("Ajouter");


    btnAjouter.setPrefWidth(120);


    btnAjouter.setPrefHeight(40);


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


    TextField tfLivre = new TextField();


    grid.add(lblLivre, 0, 1);


    grid.add(tfLivre, 1, 1);





    Label lblMagasin = new Label("Magasin :");


    lblMagasin.setStyle(labelStyle);


    TextField tfMagasin = new TextField();


    grid.add(lblMagasin, 0, 2);


    grid.add(tfMagasin, 1, 2);





    Button btnRechercher = new Button("Rechercher");


    btnRechercher.setPrefWidth(120);


    btnRechercher.setPrefHeight(40);


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


    TextField tfClient = new TextField();


    grid.add(lblClient, 0, 1);


    grid.add(tfClient, 1, 1);





    Label lblLivre = new Label("Livre :");


    lblLivre.setStyle(labelStyle);


    TextField tfLivre = new TextField();


    grid.add(lblLivre, 0, 2);


    grid.add(tfLivre, 1, 2);





    Label lblMagasin = new Label("Magasin :");


    lblMagasin.setStyle(labelStyle);


    TextField tfMagasin = new TextField();


    grid.add(lblMagasin, 0, 3);


    grid.add(tfMagasin, 1, 3);





    Button btnCommander = new Button("Commander");


    btnCommander.setPrefWidth(120);


    btnCommander.setPrefHeight(40);


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


    TextField tfISBN = new TextField();


    grid.add(lblISBN, 0, 1);


    grid.add(tfISBN, 1, 1);





    Label lblMagasinDep = new Label("Magasin de départ :");


    lblMagasinDep.setStyle(labelStyle);


    TextField tfMagDep = new TextField();


    grid.add(lblMagasinDep, 0, 2);


    grid.add(tfMagDep, 1, 2);





    Label lblMagasinArr = new Label("Magasin d'arrivée :");


    lblMagasinArr.setStyle(labelStyle);


    TextField tfMagArr = new TextField();


    grid.add(lblMagasinArr, 0, 3);


    grid.add(tfMagArr, 1, 3);





    Label lblQte = new Label("Quantité :");


    lblQte.setStyle(labelStyle);


    TextField tfQte = new TextField();


    grid.add(lblQte, 0, 4);


    grid.add(tfQte, 1, 4);





    Button btnTransferer = new Button("Transférer");


    btnTransferer.setPrefWidth(120);


    btnTransferer.setPrefHeight(40);


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


    TextField tfClient = new TextField();


    grid.add(lblClient, 0, 1);


    grid.add(tfClient, 1, 1);





    Label lblMagasin = new Label("Magasin :");


    lblMagasin.setStyle(labelStyle);


    TextField tfMagasin = new TextField();


    grid.add(lblMagasin, 0, 2);


    grid.add(tfMagasin, 1, 2);





    Label lblMois = new Label("Mois :");


    lblMois.setStyle(labelStyle);


    TextField tfMois = new TextField();


    grid.add(lblMois, 0, 3);


    grid.add(tfMois, 1, 3);





    Label lblAnnee = new Label("Année :");


    lblAnnee.setStyle(labelStyle);


    TextField tfAnnee = new TextField();


    grid.add(lblAnnee, 0, 4);


    grid.add(tfAnnee, 1, 4);





    Button btnObtenir = new Button("Obtenir");


    btnObtenir.setPrefWidth(120);


    btnObtenir.setPrefHeight(40);


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


    // Remplis la ComboBox avec la liste des magasins depuis la BD si besoin





    Button btnChoisir = new Button("Choisir");


    btnChoisir.setPrefWidth(120);


    btnChoisir.setPrefHeight(40);





    vbox.getChildren().addAll(titre, cbMagasins, btnChoisir);





    this.carre.setCenter(vbox);


    this.getChildren().setAll(carre, top);


}


}