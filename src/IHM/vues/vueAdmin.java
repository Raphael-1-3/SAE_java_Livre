package IHM.vues;

import javafx.application.Application;
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
import javafx.stage.Stage;

public class vueAdmin extends Application {
    // Boutons principaux
    private Button boutonParametres;
    private Button boutonMaison;
    private Button boutonDeco;

    private BorderPane carre;
    private Scene scene;

    @Override
    public void init() {
        this.boutonMaison = new Button();
        this.boutonParametres = new Button();
        this.boutonDeco = new Button();

        this.carre = cadre();

        this.boutonMaison.setGraphic(new ImageView());
        this.boutonMaison.setPrefSize(50, 20);

        this.boutonParametres.setGraphic(new ImageView());
        this.boutonParametres.setPrefSize(50, 20);

        this.boutonDeco.setGraphic(new ImageView());
        this.boutonDeco.setPrefSize(50, 20);
    }

    private Scene laScene() {
        StackPane fenetre = new StackPane();
        VBox top = top();
        BorderPane cadre = this.carre;
        fenetre.getChildren().addAll(cadre, top);
        StackPane.setAlignment(top, Pos.TOP_LEFT);
        StackPane.setAlignment(cadre, Pos.CENTER);
        fenetre.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.scene = new Scene(fenetre, 1920, 1080);
        return this.scene;
    }

    public void creerVendeur() {
        this.carre = cadre();
        
    }

    private VBox top() {
        VBox banniere = new VBox();
        HBox ligne = new HBox();
        HBox boutonsFixes = new HBox(25);
        boutonsFixes.setPadding(new Insets(25, 0, 0, 0));

        // Menu déroulant
        VBox contenuMenu = new VBox(15);
        Button bouton1 = new Button("Action 1");
        Button bouton2 = new Button("Action 2");
        contenuMenu.getChildren().addAll(bouton1, bouton2);

        TitledPane menuDeroulant = new TitledPane("Menu déroulant", contenuMenu);
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

    private BorderPane cadre() {
        BorderPane mid = new BorderPane();
        mid.setMaxSize(1300, 650);
        mid.setStyle("-fx-background-color: grey;");
        this.carre = mid;
        return mid;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Livre Express - Admin");
        primaryStage.setScene(this.laScene());
        scene.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
