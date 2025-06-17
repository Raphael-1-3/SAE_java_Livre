package IHM.vues;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.shape.Line;
import javafx.scene.image.ImageView;

public class VueVendeur extends Application {

    private void styliserBouton(Button bouton) {
        bouton.setFont(new Font("Times new Roman", 10));
        bouton.setStyle(
            "-fx-text-fill: white;" +
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 20;" +
            "-fx-border-color : #df9d53;" +
            "-fx-border-width : 2;" +
            "-fx-border-radius : 20;"
        );
    }

    private VBox createTitre() {
        VBox vb = new VBox();
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 0, 0, 20));

        Label vendeur = new Label("Vendeur");
        vendeur.setFont(new Font("Times new Roman", 60));
        vendeur.setStyle("-fx-text-fill: #3f4353");
        vendeur.setPadding(new Insets(10, 0, 0, 400));

        ImageView logo = new ImageView(new Image("file:./img/logo.jpg"));
        logo.setFitWidth(100);
        logo.setFitHeight(100);

        Button boutonquitter = new Button("quitter");
        styliserBouton(boutonquitter);

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

        vb.getChildren().addAll(hb, sep);

        return vb;
    }

    private BorderPane createResultat() {
        BorderPane resultat = new BorderPane();
        resultat.setPrefSize(500, 340);
        resultat.setStyle("-fx-background-color: grey;");
        resultat.setLayoutX((1200 - 500) / 2);
        resultat.setLayoutY((800 - 340) - 80);
        return resultat;
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        VBox titre = createTitre();
        BorderPane resultat = createResultat();

        VBox vbox = new VBox();
        vbox.setPrefSize(200, 40);
        // on enlève la couleur de fond ici aussi par sécurité
        vbox.setStyle("-fx-background-color: transparent;");

        Button bouton1 = new Button("Ajouter un livre");
        Button bouton2 = new Button("Modifier le stock");
        Button bouton3 = new Button("Regarde dispo");
        Button bouton4 = new Button("Commande pour client");
        Button bouton5 = new Button("Transferer un Livre");
        Button bouton6 = new Button("Obtenir facture");

        styliserBouton(bouton1);
        styliserBouton(bouton2);
        styliserBouton(bouton3);
        styliserBouton(bouton4);
        styliserBouton(bouton5);
        styliserBouton(bouton6);

        vbox.getChildren().addAll(bouton1, bouton2, bouton3, bouton4, bouton5, bouton6);

        TitledPane menuDeroulant = new TitledPane("☰", vbox);
        menuDeroulant.setExpanded(false);
        menuDeroulant.setPrefWidth(200);
        menuDeroulant.setLayoutX(0);
        menuDeroulant.setLayoutY(170);
        menuDeroulant.setStyle("-fx-background-color: transparent;"); // ✅ MODIF IMPORTANTE

        root.getChildren().addAll(titre, menuDeroulant, resultat);
        root.setStyle("-fx-background-color: lightgrey;");

        Scene scene = new Scene(root, 1200, 800);

        primaryStage.setTitle("Vue Vendeur");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}