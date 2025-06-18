package IHM.vues;

import IHM.controleurs.ControleurVendeur;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.image.ImageView;

public class VueVendeur extends Application {

    private VBox createTitre(Button boutonquitter) {
        VBox vb = new VBox();
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 0, 0, 20));

        Label vendeur = new Label("Vendeur");
        vendeur.setFont(new Font("Times New Roman", 60));
        vendeur.setStyle("-fx-text-fill: #3f4353");
        vendeur.setPadding(new Insets(10, 0, 0, 400));

        ImageView logo = new ImageView(new Image("file:./img/logo.jpg"));
        logo.setFitWidth(100);
        logo.setFitHeight(100);

        Rectangle bordure = new Rectangle(logo.getFitWidth(), logo.getFitHeight());
        bordure.setArcWidth(30);
        bordure.setArcHeight(30);
        logo.setClip(bordure);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        hb.getChildren().addAll(logo, vendeur, spacer, boutonquitter);

        Line sep = new Line(0, 0, 1100, 0);
        sep.setStrokeWidth(2);
        sep.setStroke(Color.DARKGREY);
        VBox.setMargin(sep, new Insets(20, 0, 0, 100));

        vb.getChildren().addAll(hb, sep);
        return vb;
    }

    private BorderPane createResultat() {
        BorderPane resultat = new BorderPane();
        resultat.setPrefSize(500, 340);
        resultat.setStyle("-fx-background-color: grey;");
        resultat.setLayoutX((1200 - 500) / 2);
        resultat.setLayoutY((800 - 140) / 2);
        return resultat;
    }

    // Affiche le formulaire d'ajout de livre
    public void afficherFormulaireAjoutLivre(BorderPane resultat) {
        VBox formulaire = new VBox(10);
        formulaire.setPadding(new Insets(20));
        formulaire.setStyle("-fx-background-color: grey; -fx-padding: 20;");

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
       
        boutonValider.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Formulaire soumis (sans BD) !");
            alert.showAndWait();
        });

        formulaire.getChildren().addAll(champISBN, champTitre, champNbPages, champDatePubli, champPrix, boutonValider);
        resultat.setCenter(formulaire);
    }

   
    public void clearResultat(BorderPane resultat) {
        resultat.setCenter(null);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Button boutonquitter = new Button("quitter");
        VBox titre = createTitre(boutonquitter);
        BorderPane resultat = createResultat();

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

   
        menuCombo.setOnAction(e -> {
            String choix = menuCombo.getValue();
            switch (choix) {
                case "Ajouter un livre":
                    afficherFormulaireAjoutLivre(resultat);
                    break;
                default:
                    clearResultat(resultat);
                    break;
            }
        });

        boutonquitter.setOnAction(e -> Platform.exit());

        root.getChildren().addAll(titre, menuCombo, resultat);
        root.setStyle("-fx-background-color: lightgrey;");

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");

        primaryStage.setTitle("Vue Vendeur");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
