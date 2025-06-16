package IHM.vues;
import main.*;

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
        stage.show();
    }

    public static void main(String[] args) {
        
    }
}
