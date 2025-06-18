package IHM.vues;

import IHM.controleurs.ControleurAdmin.ControleurChangerPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

public class VueAdmin extends StackPane {
    private Administrateur admin;
    private ActionBD modele;

    // Boutons principaux
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
        this.modele = modele;
        this.carre = createCadre();
        this.top = createTop();

        boutonMaison.setGraphic(new ImageView("file:./img/deco.png"));
        boutonMaison.setPrefSize(50, 20);

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

        super.getChildren().addAll(carre, top);
        StackPane.setAlignment(this.top, Pos.TOP_LEFT);
    }

    public TextField getTfEmail() { return tfEmail; }
    public PasswordField getPfMDP() { return pfMDP; }
    public TextField getTfNom() { return tfNom; }
    public TextField getTfPrenom() { return tfPrenom; }
    public TextField getTfIdMag() { return tfIdMag; }

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
        grid.add(lblNom, 0, 2);

        tfNom.setPrefWidth(300);
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
        boutonsFixes.setPadding(new Insets(0, 0, 0, 450));
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
            }
        }
        return banniere;
    }

    private BorderPane createCadre() {
        BorderPane mid = new BorderPane();
        mid.setMaxSize(1300, 650);
        mid.setStyle("-fx-background-color: grey;");
        return mid;
    }

    // Les autres méthodes (ajouterLibrairie, panneauDeBord, ajouterLivre, etc.) restent inchangées
    // ... (copiez-les ici sans les espaces superflus)
}
