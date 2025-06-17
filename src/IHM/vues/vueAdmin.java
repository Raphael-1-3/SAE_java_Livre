package IHM.vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class vueAdmin extends StackPane {

    // Boutons principaux
    private final Button boutonParametres = new Button();
    private final Button boutonMaison = new Button();
    private final Button boutonDeco = new Button();

    private BorderPane carre;
    private final VBox top;
    private final StackPane fenetre = new StackPane();
    private final Scene scene;

    public vueAdmin() {
        this.carre = createCadre();
        this.top = createTop();

        boutonMaison.setGraphic(new ImageView());
        boutonMaison.setPrefSize(50, 20);

        boutonParametres.setGraphic(new ImageView());
        boutonParametres.setPrefSize(50, 20);

        boutonDeco.setGraphic(new ImageView());
        boutonDeco.setPrefSize(50, 20);

        this.scene = createScene();
        this.getChildren().addAll(carre, top);
    }

    private Scene createScene() {
        fenetre.getChildren().addAll(carre, top);
        StackPane.setAlignment(top, Pos.TOP_LEFT);
        StackPane.setAlignment(carre, Pos.CENTER);
        fenetre.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(fenetre, 1920, 1080);
        scene.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");
        return scene;
    }

    public void creerVendeur() {
        this.carre = createCadre();
    }

    private VBox createTop() {
        VBox banniere = new VBox();
        HBox ligne = new HBox();
        HBox boutonsFixes = new HBox(25);
        boutonsFixes.setPadding(new Insets(25, 0, 0, 0));

        // Menu déroulant
        VBox contenuMenu = new VBox(15);
        Button bouton1 = new Button("Action 1");
        Button bouton2 = new Button("Action 2");
        contenuMenu.getChildren().addAll(bouton1, bouton2);

        TitledPane menuDeroulant = new TitledPane("Actions", contenuMenu);
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

        boutonsFixes.getChildren().addAll(boutonMaison, boutonParametres, boutonDeco);

        ligne.getChildren().addAll(logo, titre, boutonsFixes);
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
}
