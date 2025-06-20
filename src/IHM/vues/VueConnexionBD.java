package IHM.vues;

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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;
import java.util.List;

import IHM.controleurs.ControleurAcceuil.*;
public class VueConnexionBD {

    private TextField tfServ;
    private TextField tfUser;
    private PasswordField pwUser;

    private LivreExpress app;
    public VueConnexionBD(LivreExpress app)
    {
        this.app = app;

        this.tfServ = new TextField();
        this.tfServ.setMinWidth(350);
        this.tfServ.setMaxWidth(350);

        this.tfUser = new TextField();
        this.tfUser.setMinWidth(350);
        this.tfUser.setMaxWidth(350);
        
        this.pwUser = new PasswordField();
        this.pwUser.setMinWidth(350);
        this.pwUser.setMaxWidth(350);



        // ------------ Connexion ---------------------

        //Instanciation du TextField email de Connexion
        
    }

    public TextField getTfServ() {
        return tfServ;
    }

    public TextField getTfUser() {
        return tfUser;
    }

    public PasswordField getPwUser() {
        return pwUser;
    }

    public Alert popUpUtilisateurExistePas()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText("L'identifiant ou le mot de passe ne sont pas correctes");
        return alert;
    }

    public Alert popUpPasUnNbr()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer un nombre dans le champ Code Postal");
        return alert;
    }

    public Alert popUpClientCree()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Votre compte a ete cree avec succes");
        return alert;
    }

    public Alert popUpChampsVides()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs");
        return alert;
    }

    public void fenetreConnexion()
    {

        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        scene.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");
        stage.setTitle("Connexion a la base de donnees");

        VBox vbH = new VBox();
        Label lbCo = new Label();
        lbCo.setStyle("-fx-font-weight : bold;" + 
        "-fx-font-size : 20px;");
        vbH.setAlignment(Pos.CENTER);
        vbH.getChildren().add(lbCo);

        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);

        Label lbNomServ = new Label("Nom du serveur");
        lbNomServ.setPadding(new Insets(20, 0, 10, 0));

        Label lbNom = new Label("Nom de l'utilisateur");
        lbNomServ.setPadding(new Insets(20, 0, 10, 0));

        Label lbPasswd = new Label("Mot de passe");
        lbPasswd.setPadding(new Insets(20, 0, 10, 0));

        HBox boutonsBas = new HBox(110);

        Button connecte = new Button("Se connecter");
        connecte.setFont(new Font("Times new Roman", 20));
        connecte.setPadding(new Insets(3, 10, 3, 10));
        connecte.setPrefWidth(150);
        
        boutonsBas.getChildren().addAll(connecte);
        boutonsBas.setPadding(new Insets(50, 0, 30, 0));
        boutonsBas.setAlignment(Pos.CENTER);

        connecte.setOnAction(new ControleurConnexionBD(this.app));

        vb.getChildren().addAll(lbNomServ, this.tfServ, lbNom, this.tfUser, lbPasswd, this.pwUser, boutonsBas);
        root.setCenter(vb);
        
        
        stage.show();
    }

}
