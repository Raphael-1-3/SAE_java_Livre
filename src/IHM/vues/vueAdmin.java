package IHM.vues;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.BD.ActionBD;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData;

import java.util.List;

import javax.sound.sampled.Control;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout;

import org.hamcrest.collection.IsMapWithSize;

import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;

public class vueAdmin extends Application {
    // * importation du modele*/
    // private ActionBD actionBD;
    // * Le border pane qui sert a tout stocker */
    private BorderPane bp;
    // *le bouton Paramètre*/
    private Button boutonParametres;
    // *le bouton Accueil*/
    private Button boutonMaison;
    // *bouton de deconnexion */
    private Button boutonDeco;

    private Scene scene;

    @Override
    public void init() {
        // this.actionBD = new ActionBD(null);
        this.boutonMaison = new Button();
        this.boutonParametres = new Button();
        this.boutonDeco = new Button();

        ImageView menu = new ImageView();
        this.boutonMaison.setGraphic(menu);
        this.boutonMaison.setPrefSize(50, 20);
        // this.boutonMaison.setOnAction(new Deconnexion(ActionBD, this));

        ImageView reg = new ImageView();
        this.boutonParametres.setGraphic(reg);
        this.boutonParametres.setPrefSize(50, 20);
        // this.boutonParametres.setOnAction(new Reglages(ActionBD,this));

        ImageView deco = new ImageView();
        this.boutonDeco.setGraphic(deco);
        this.boutonDeco.setPrefSize(50, 20);

        this.bp = new BorderPane();

    }

    private Scene laScene() {
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.top());
        fenetre.setCenter(this.bp);
        fenetre.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.scene = new Scene(fenetre, 1920, 1080);
        return this.scene;
    }
    // *créer le bordereau */

    private VBox top() {
        VBox banniere = new VBox();
        HBox ligne = new HBox();
        HBox boutonsFixes = new HBox(25);
        boutonsFixes.setPadding(new Insets(25, 0, 0, 0));
        VBox contenuMenu = new VBox(15);
        Button bouton1 = new Button("Action 1");
        Button bouton2 = new Button("Action 2");
        contenuMenu.getChildren().addAll(bouton1, bouton2);

        TitledPane menuDeroulant = new TitledPane("Menu déroulant", contenuMenu);
        menuDeroulant.setExpanded(false);
        menuDeroulant.setMaxWidth(80);
        // Met le fond du menu déroulant de la même couleur que le reste quand il est
        // ouvert
        contenuMenu.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        bouton1.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color : #3f4353;" +
                        "-fx-background-radius : 20;" +
                        "-fx-border-color : #df9d53;" +
                        "-fx-border-width : 2;" +
                        "-fx-border-radius : 20;");
        bouton2.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color : #3f4353;" +
                        "-fx-background-radius : 20;" +
                        "-fx-border-color : #df9d53;" +
                        "-fx-border-width : 2;" +
                        "-fx-border-radius : 20;");
        boutonDeco.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color : #3f4353;" +
                        "-fx-background-radius : 20;" +
                        "-fx-border-color : #df9d53;" +
                        "-fx-border-width : 2;" +
                        "-fx-border-radius : 20;");
        boutonParametres.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color : #3f4353;" +
                        "-fx-background-radius : 20;" +
                        "-fx-border-color : #df9d53;" +
                        "-fx-border-width : 2;" +
                        "-fx-border-radius : 20;");
        boutonMaison.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color : #3f4353;" +
                        "-fx-background-radius : 20;" +
                        "-fx-border-color : #df9d53;" +
                        "-fx-border-width : 2;" +
                        "-fx-border-radius : 20;");
        ImageView logo = new ImageView("file:./img/logo.jpg");
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        Label t = new Label("Livre Express - Admin");
        t.setFont(new Font("Times New Roman", 60));
        t.setStyle("-fx-text-fill: #3f4353");
        t.setPadding(new Insets(10, 0, 0, 0));
        t.setTextFill(Color.BLACK);
        t.setAlignment(Pos.TOP_LEFT);
        t.setPadding(new Insets(0, 500, 0, 15));

        Rectangle bordure = new Rectangle(logo.getFitWidth(), logo.getFitHeight());
        bordure.setArcWidth(30);
        bordure.setArcHeight(30);
        logo.setClip(bordure);

        boutonsFixes.getChildren().addAll(this.boutonMaison, this.boutonParametres, this.boutonDeco);
        ligne.getChildren().addAll(logo, t, boutonsFixes);
        ligne.setAlignment(Pos.CENTER_LEFT);

        banniere.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Line sep = new Line(0, 0, 1700, 0);
        sep.setStrokeWidth(2);
        sep.setStroke(Color.BLACK);
        HBox boxLigne = new HBox();
        boxLigne.setPadding(new Insets(10, 0, 10, 100));
        boxLigne.getChildren().addAll(sep);
        menuDeroulant.setPadding(new Insets(0, 0, 0, 0));
        banniere.getChildren().addAll(ligne, boxLigne, menuDeroulant);
        banniere.setPadding(new Insets(25, 0, 0, 0));

        return banniere;
    }

    // *la fenetre ou les données sont affichées et les champs d input également */
    private Pane fenetreExecution() {
        BorderPane mid = new BorderPane();
        VBox vbox = new VBox(30);
        return mid;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Livre Express - Admin");
        primaryStage.setScene(this.laScene());
        scene.getStylesheets().add(getClass().getResource("style_md.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}