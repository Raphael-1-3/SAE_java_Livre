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
        Rectangle bordure = new Rectangle(logo.getFitWidth(), logo.getFitHeight());
        bordure.setArcWidth(30);
        bordure.setArcHeight(30);
        logo.setClip(bordure);
        hb.getChildren().addAll(logo, vendeur);
        Line sep = new Line(0, 0, 1100, 0);
        
        sep.setStrokeWidth(2);
        sep.setStroke(Color.DARKGREY);




   

    vb.getChildren().addAll(hb);

    return vb;
}



    private BorderPane createResultat() {
        BorderPane resultat = new BorderPane();
        resultat.setPrefSize(500, 340);
        resultat.setStyle("-fx-background-color: grey;");
        resultat.setLayoutX((800 - 500) / 2);
        resultat.setLayoutY(600 - 340 - 80);
        return resultat;
    }

   @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        VBox titre = createTitre();
        BorderPane resultat = createResultat();

        VBox vbox = new VBox();
        vbox.setPrefSize(200, 40);
        vbox.setStyle("-fx-background-color: lightgrey;");
        Button bouton1 = new Button("Ajouter un livre");
        Button bouton2 = new Button("Modifier le stock");
        Button bouton3 = new Button("Regarde dispo");
        Button bouton4 = new Button("Commande pour client");
        Button bouton5 = new Button("Transferer un Livre");
        Button bouton6 = new Button("Obtenir facture");
        vbox.getChildren().addAll(bouton1, bouton2,bouton3,bouton4,bouton5,bouton6);

        TitledPane menuDeroulant = new TitledPane("☰", vbox);
        menuDeroulant.setExpanded(false);
        menuDeroulant.setPrefWidth(80);
        menuDeroulant.setLayoutX(0);       
        menuDeroulant.setLayoutY(170);     

        root.getChildren().addAll(titre, menuDeroulant, resultat);
        root.setStyle("-fx-background-color: lightgrey;");

        Scene scene = new Scene(root, 1200, 800);

        primaryStage.setTitle("Vue Vide");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
