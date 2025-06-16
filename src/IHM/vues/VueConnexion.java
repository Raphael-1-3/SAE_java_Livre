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

import main.*;
public class VueConnexion {
    
    public void setTop(BorderPane root, String titre)
    {
        VBox vb = new VBox();
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 0, 0, 20));
        Label bienvenue = new Label(titre);
        bienvenue.setFont(new Font("Times new Roman", 60));
        bienvenue.setStyle("-fx-text-fill: #3f4353");
        bienvenue.setPadding(new Insets(10, 0, 0, 400));
        ImageView logo = new ImageView(new Image("file:./img/logo.jpg"));
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        Rectangle bordure = new Rectangle(logo.getFitWidth(), logo.getFitHeight());
        bordure.setArcWidth(30);
        bordure.setArcHeight(30);
        logo.setClip(bordure);
        hb.getChildren().addAll(logo, bienvenue);
        Line sep = new Line(0, 0, 1100, 0);
        
        sep.setStrokeWidth(2);
        sep.setStroke(Color.DARKGREY);
        HBox boxLigne = new HBox();
        boxLigne.setPadding(new Insets(10, 0, 100, 100));
        boxLigne.getChildren().addAll(sep);
        vb.getChildren().addAll(hb, boxLigne);
        root.setTop(vb);
        
    }
    
    public void fenetreAccueil(BorderPane root)
    {
        setTop(root, "Bienvenue");
        VBox vb = new VBox(100);
        Button connexion = new Button("Connexion");
        connexion.setFont(new Font("Times new Roman", 30));
        connexion.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 20;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 20;"
            );
        connexion.setPadding(new Insets(0, 130, 0, 130));
        connexion.setPrefWidth(410);
        Button inscription = new Button("Inscription");
        inscription.setFont(new Font("Times new Roman", 30));
        inscription.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 20;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 20;"
            );
        inscription.setPadding(new Insets(0, 130, 0, 130));
        inscription.setPrefWidth(410);
        Button quitter = new Button("Quitter");
        quitter.setFont(new Font("Times new Roman", 30));
        quitter.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 20;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 20;"
            );
        quitter.setPadding(new Insets(0, 130, 0, 130));
        quitter.setPrefWidth(410);

        vb.getChildren().addAll(connexion, inscription, quitter);
        vb.setAlignment(Pos.CENTER);
        root.setCenter(vb);
    }
    public void fenetreConnexion(BorderPane root)
    {
        setTop(root, "Connexion");
        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);

        Label lbEmail = new Label("Email");
        lbEmail.setPadding(new Insets(20, 0, 10, 0));
        lbEmail.setFont(new Font("Times new Roman", 30));
        lbEmail.setStyle("-fx-text-fill: #3f4353");

        TextField email = new TextField();
        email.setPadding(new Insets(0, 10, 0, 10));
        email.setFont(new Font("Times new Roman", 30));
        email.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 15;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 15;"
            );
        email.setPrefColumnCount(4);
        email.setPrefWidth(410);
        email.setMaxWidth(410);

        Label lbPasswd = new Label("Mot de passe");
        lbPasswd.setPadding(new Insets(20, 0, 10, 0));
        lbPasswd.setFont(new Font("Times new Roman", 30));
        lbPasswd.setStyle("-fx-text-fill: #3f4353");

        PasswordField mdp = new PasswordField();
        mdp.setPadding(new Insets(0, 10, 0, 10));
        mdp.setFont(new Font("Times new Roman", 30));
        mdp.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 15;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 15;"
            );
        mdp.setPrefColumnCount(4);
        mdp.setPrefWidth(410);
        mdp.setMaxWidth(410);

        HBox boutonsBas = new HBox(110);

        Button accueil = new Button("Accueil");
        accueil.setFont(new Font("Times new Roman", 20));
        accueil.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 20;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 20;"
            );
        accueil.setPadding(new Insets(3, 10, 3, 10));
        accueil.setPrefWidth(150);
        Button connecte = new Button("Se connecter");
        connecte.setFont(new Font("Times new Roman", 20));
        connecte.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 20;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 20;"
            );
        connecte.setPadding(new Insets(3, 10, 3, 10));
        connecte.setPrefWidth(150);
        
        boutonsBas.getChildren().addAll(accueil, connecte);
        boutonsBas.setPadding(new Insets(50, 0, 30, 0));
        boutonsBas.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(lbEmail, email, lbPasswd, mdp, boutonsBas);
        root.setCenter(vb);
    }

    public void fenetreInscription(BorderPane root)
    {
        setTop(root, "Inscription");
        BorderPane centre = new BorderPane();
        VBox gauche = new VBox();
        gauche.setPadding(new Insets(10, 10, 0, 150));
        gauche.setAlignment(Pos.TOP_LEFT);
        VBox droite = new VBox();
        droite.setPadding(new Insets(10, 150, 0, 10));
        droite.setAlignment(Pos.TOP_LEFT);

        Label lbEmail = new Label("Email");
        lbEmail.setPadding(new Insets(20, 0, 10, 0));
        lbEmail.setFont(new Font("Times new Roman", 30));
        lbEmail.setStyle("-fx-text-fill: #3f4353");

        TextField email = new TextField();
        email.setPadding(new Insets(0, 10, 0, 10));
        email.setFont(new Font("Times new Roman", 30));
        email.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 15;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 15;"
            );
        email.setPrefColumnCount(4);
        email.setPrefWidth(410);
        email.setMaxWidth(410);

        Label lbPasswd = new Label("Mot de passe");
        lbPasswd.setPadding(new Insets(20, 0, 10, 0));
        lbPasswd.setFont(new Font("Times new Roman", 30));
        lbPasswd.setStyle("-fx-text-fill: #3f4353");

        PasswordField mdp = new PasswordField();
        mdp.setPadding(new Insets(0, 10, 0, 10));
        mdp.setFont(new Font("Times new Roman", 30));
        mdp.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 15;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 15;"
            );
        mdp.setPrefColumnCount(4);
        mdp.setPrefWidth(410);
        mdp.setMaxWidth(410);

        Label lbNom = new Label("Nom");
        lbNom.setPadding(new Insets(20, 0, 10, 0));
        lbNom.setFont(new Font("Times new Roman", 30));
        lbNom.setStyle("-fx-text-fill: #3f4353");

        TextField nom = new TextField();
        nom.setPadding(new Insets(0, 10, 0, 10));
        nom.setFont(new Font("Times new Roman", 30));
        nom.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 15;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 15;"
            );
        nom.setPrefColumnCount(4);
        nom.setPrefWidth(410);
        nom.setMaxWidth(410);

        Label lbPrenom = new Label("Prenom");
        lbPrenom.setPadding(new Insets(20, 0, 10, 0));
        lbPrenom.setFont(new Font("Times new Roman", 30));
        lbPrenom.setStyle("-fx-text-fill: #3f4353");

        TextField prenom = new TextField();
        prenom.setPadding(new Insets(0, 10, 0, 10));
        prenom.setFont(new Font("Times new Roman", 30));
        prenom.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 15;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 15;"
            );
        prenom.setPrefColumnCount(4);
        prenom.setPrefWidth(410);
        prenom.setMaxWidth(410);

        HBox boutonsBas = new HBox(180);

        Button accueil = new Button("Accueil");
        accueil.setFont(new Font("Times new Roman", 20));
        accueil.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 20;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 20;"
            );
        accueil.setPadding(new Insets(3, 10, 3, 10));
        accueil.setPrefWidth(150);
        Button connecte = new Button("S'inscrire");
        connecte.setFont(new Font("Times new Roman", 20));
        connecte.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 20;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 20;"
            );
        connecte.setPadding(new Insets(3, 10, 3, 10));
        connecte.setPrefWidth(150);
        
        boutonsBas.getChildren().addAll(accueil, connecte);
        boutonsBas.setPadding(new Insets(50, 0, 30, 0));
        boutonsBas.setAlignment(Pos.CENTER);

        Label lbCodePos = new Label("Code Postal");
        lbCodePos.setPadding(new Insets(20, 0, 10, 0));
        lbCodePos.setFont(new Font("Times new Roman", 30));
        lbCodePos.setStyle("-fx-text-fill: #3f4353");

        TextField codePost = new TextField();
        codePost.setPadding(new Insets(0, 10, 0, 10));
        codePost.setFont(new Font("Times new Roman", 30));
        codePost.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 15;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 15;"
            );
        codePost.setPrefColumnCount(4);
        codePost.setPrefWidth(410);
        codePost.setMaxWidth(410);

        Label lbVille = new Label("Ville");
        lbVille.setPadding(new Insets(20, 0, 10, 0));
        lbVille.setFont(new Font("Times new Roman", 30));
        lbVille.setStyle("-fx-text-fill: #3f4353");

        TextField ville = new TextField();
        ville.setPadding(new Insets(0, 10, 0, 10));
        ville.setFont(new Font("Times new Roman", 30));
        ville.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 15;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 15;"
            );
        ville.setPrefColumnCount(4);
        ville.setPrefWidth(410);
        ville.setMaxWidth(410);

        Label lbAdresse = new Label("Adresse");
        lbAdresse.setPadding(new Insets(20, 0, 10, 0));
        lbAdresse.setFont(new Font("Times new Roman", 30));
        lbAdresse.setStyle("-fx-text-fill: #3f4353");

        TextField adresse = new TextField();
        adresse.setPadding(new Insets(0, 10, 0, 10));
        adresse.setFont(new Font("Times new Roman", 30));
        adresse.setStyle(
            "-fx-text-fill: white;" + 
            "-fx-background-color : #3f4353;" +
            "-fx-background-radius : 15;" +
            "-fx-border-color : #df9d53;" + 
            "-fx-border-width : 2;" + 
            "-fx-border-radius : 15;"
            );
        adresse.setPrefColumnCount(4);
        adresse.setPrefWidth(410);
        adresse.setMaxWidth(410);

        droite.getChildren().addAll(lbCodePos, codePost, lbVille, ville, lbAdresse, adresse);
        centre.setRight(droite);

        gauche.getChildren().addAll(lbEmail, email, lbPasswd, mdp, lbNom, nom, lbPrenom, prenom);
        centre.setLeft(gauche);

        centre.setBottom(boutonsBas);
        root.setCenter(centre);
    }
}
