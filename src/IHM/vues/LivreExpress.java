package IHM.vues;
import main.app.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;
import java.util.List;

public class LivreExpress extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        stage.setTitle("LivreExpress");
        stage.setScene(scene);
        root.setPrefSize(1300, 700);
        root.setStyle("-fx-background-color : #d4d5d5;");
        Client client = new Client(0, "s", "s", "s", "s", "s", 0, "k", "s");
        vueClient vueclient = new  vueClient(this, client);
        //VueConnexion vueConnexion = new VueConnexion();
        //vueConnexion.fenetreInscription(root);
        scene.setRoot(vueclient);
        stage.show();
        System.out.println("MARCHE");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
