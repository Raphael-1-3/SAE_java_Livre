package IHM.vues;

import IHM.controleurs.ControleurVendeur.ControleurVendeur;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.BD.*;
import main.app.Vendeur;

public class VueVendeur extends Pane {
    private LivreExpress app;
    private ActionBD modele;
    private Vendeur vendeur;
    public VBox createTitre(Button boutonQuitter) {
        VBox vb = new VBox();
        HBox hb = new HBox();
        hb.setPadding(new Insets(20));

        Label vendeur = new Label("Vendeur");
        vendeur.setFont(new Font("Times New Roman", 60));
        vendeur.setStyle("-fx-text-fill: #3f4353");
        vendeur.setPadding(new Insets(0, 0, 0, 20));

        ImageView logo = new ImageView(new Image("file:./img/logo.jpg"));
        logo.setFitWidth(100);
        logo.setFitHeight(100);

        Rectangle bordure = new Rectangle(logo.getFitWidth(), logo.getFitHeight());
        bordure.setArcWidth(30);
        bordure.setArcHeight(30);
        logo.setClip(bordure);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        hb.getChildren().addAll(logo, vendeur, spacer, boutonQuitter);

        Line sep = new Line(0, 0, 1100, 0);
        sep.setStrokeWidth(2);
        sep.setStroke(Color.DARKGREY);
        VBox.setMargin(sep, new Insets(0, 0, 0, 20));

        vb.getChildren().addAll(hb, sep);
        return vb;
    }

    public BorderPane createResultat() {
        BorderPane resultat = new BorderPane();
        resultat.setPrefSize(500, 340);
        resultat.setStyle("-fx-background-color: grey;");
        return resultat;
    }

    // --- Méthodes d'affichage de vues, sans logique métier, juste UI ---

    public void afficherFormulaireAjoutLivre(BorderPane resultat) {
        VBox formulaire = new VBox(10);
        formulaire.setPadding(new Insets(20));
        formulaire.setStyle("-fx-background-color: grey;");

        TextField champISBN = new TextField();
        champISBN.setPromptText("ISBN");

        TextField champTitre = new TextField();
        champTitre.setPromptText("Titre");

        TextField champNbPages = new TextField();
        champNbPages.setPromptText("Nombre de pages");

        TextField champDatePubli = new TextField();
        champDatePubli.setPromptText("Date de publication");

        TextField champPrix = new TextField();
        champPrix.setPromptText("Prix");

        Button boutonValider = new Button("Valider");

        formulaire.getChildren().addAll(champISBN, champTitre, champNbPages, champDatePubli, champPrix, boutonValider);
        resultat.setCenter(formulaire);
    }

    public void afficherRechercheParMagasin(BorderPane resultat) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: grey;");

        Label labelMag = new Label("Entrer le nom du magasin :");
        labelMag.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        TextField champMagasin = new TextField();
        champMagasin.setPromptText("Nom du magasin");

        Button validerMag = new Button("Valider");

        vbox.getChildren().addAll(labelMag, champMagasin, validerMag);
        resultat.setCenter(vbox);
    }

    public void afficherRechercheLivre(BorderPane resultat, String magasin) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: grey;");

        Label labelLivre = new Label("Rechercher un livre dans : " + magasin);
        labelLivre.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        TextField champRechercheLivre = new TextField();
        champRechercheLivre.setPromptText("Nom du livre");

        Button boutonRecherche = new Button("Rechercher");

        vbox.getChildren().addAll(labelLivre, champRechercheLivre, boutonRecherche);
        resultat.setCenter(vbox);
    }

    public void afficherPasserCommande(BorderPane resultat) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: grey;");

        Label labelClient = new Label("Nom du client :");
        labelClient.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        TextField champClient = new TextField();
        champClient.setPromptText("Nom du client");
        champClient.setPrefWidth(300);

        Label labelLivre = new Label("Nom du livre :");
        labelLivre.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        TextField champLivre = new TextField();
        champLivre.setPromptText("Nom du livre");
        champLivre.setPrefWidth(300);

        Label labelMagasin = new Label("Nom du magasin :");
        labelMagasin.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        TextField champMagasin = new TextField();
        champMagasin.setPromptText("Nom du magasin");
        champMagasin.setPrefWidth(300);

        Button boutonCommander = new Button("Commander");
        boutonCommander.setPrefWidth(150);

        vbox.getChildren().addAll(
                labelClient, champClient,
                labelLivre, champLivre,
                labelMagasin, champMagasin,
                boutonCommander);

        resultat.setCenter(vbox);
    }

    public void afficherTransfererUnLivre(BorderPane resultat) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: grey;");

        Label labelISBN = new Label("ISBN du livre :");
        labelISBN.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        TextField champISBN = new TextField();
        champISBN.setPromptText("ISBN");

        Label labelMagasinDepart = new Label("Magasin départ :");
        labelMagasinDepart.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        TextField champMagasinDepart = new TextField();
        champMagasinDepart.setPromptText("Magasin départ");

        Label labelMagasinArrivee = new Label("Magasin arrivée :");
        labelMagasinArrivee.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        TextField champMagasinArrivee = new TextField();
        champMagasinArrivee.setPromptText("Magasin arrivée");

        Label labelQte = new Label("Quantité :");
        labelQte.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        TextField champQte = new TextField();
        champQte.setPromptText("Quantité");

        Button boutonTransferer = new Button("Transférer");
        boutonTransferer.setPrefWidth(150);

        vbox.getChildren().addAll(
                labelISBN, champISBN,
                labelMagasinDepart, champMagasinDepart,
                labelMagasinArrivee, champMagasinArrivee,
                labelQte, champQte,
                boutonTransferer);

        resultat.setCenter(vbox);
    }

    public void afficherFacture(BorderPane resultat) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: grey;");

        Label labelClient = new Label("Nom du client :");
        labelClient.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        TextField champClient = new TextField();
        champClient.setPromptText("Nom du client");
        champClient.setPrefWidth(300);

        Label labelMois = new Label("Mois (1-12) :");
        labelMois.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        TextField champMois = new TextField();
        champMois.setPromptText("Mois");
        champMois.setPrefWidth(100);

        Label labelAnnee = new Label("Année :");
        labelAnnee.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        TextField champAnnee = new TextField();
        champAnnee.setPromptText("Année");
        champAnnee.setPrefWidth(100);

        Button boutonAfficher = new Button("Afficher la facture");
        boutonAfficher.setPrefWidth(150);

        vbox.getChildren().addAll(
                labelClient, champClient,
                labelMois, champMois,
                labelAnnee, champAnnee,
                boutonAfficher
        );

        resultat.setCenter(vbox);
    }

    
    public VueVendeur(LivreExpress app, ActionBD modele, Vendeur vendeur) {
        super();
        super.setStyle("-fx-background-color : #d4d5d5");
        this.app = app;
        this.modele = modele;
        this.vendeur = vendeur;
        Button boutonQuitter = new Button("Quitter");
        VBox titre = createTitre(boutonQuitter);
        BorderPane resultat = createResultat();

      
        resultat.setLayoutX((1200 - 500) / 2);
        resultat.setLayoutY((800 - 340) / 2);

        ComboBox<String> menuCombo = new ComboBox<>();
        menuCombo.getItems().addAll(
                "Ajouter un livre",
                "Modifier le stock",
                "Regarde dispo",
                "Commande pour client",
                "Transferer un Livre",
                "Obtenir facture"
        );
        menuCombo.setPromptText("☰ Menu Vendeur");
        menuCombo.setStyle("-fx-background-radius: 20; -fx-border-color: #df9d53; -fx-border-radius: 20;");
        menuCombo.setLayoutX(10);
        menuCombo.setLayoutY(170);
        menuCombo.setPrefWidth(200);

        
        ControleurVendeur controleur = new ControleurVendeur(resultat, this);

        menuCombo.setOnAction(controleur);

        boutonQuitter.setOnAction(e -> Platform.exit());

        super.getChildren().addAll(titre, menuCombo, resultat);
    }
}
