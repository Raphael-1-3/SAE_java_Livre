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
import main.*;
public class VueConnexion {

    private TextField tfCo;
    private PasswordField pwCo;

    private TextField tfEmail;
    private PasswordField pwInsc;
    private TextField tfNom;
    private TextField tfPrenom;

    private TextField tfCodePo;
    private TextField tfVille;
    private TextField tfAdresse;

    private LivreExpress app;
    public VueConnexion(LivreExpress app)
    {
        this.app = app;


        // ------------ Connexion ---------------------

        //Instanciation du TextField email de Connexion
        this.tfCo = new TextField();
        this.tfCo.setPadding(new Insets(0, 10, 0, 10));
        this.tfCo.setFont(new Font("Times new Roman", 30));
        this.tfCo.setPrefColumnCount(4);
        this.tfCo.setPrefWidth(410);
        this.tfCo.setMaxWidth(410);

        //Instanciation du TextField password de Connexion
        this.pwCo = new PasswordField();
        this.pwCo.setPadding(new Insets(0, 10, 0, 10));
        this.pwCo.setFont(new Font("Times new Roman", 30));
        this.pwCo.setPrefColumnCount(4);
        this.pwCo.setPrefWidth(410);
        this.pwCo.setMaxWidth(410);
        this.pwCo.setOnKeyPressed(new controleurConnexionClavier(this.app, this.app.getModele()));
        // --------------- Inscription -----------------------
        //Instanciation du TextField email de Inscription
        this.tfEmail = new TextField();
        this.tfEmail.setPadding(new Insets(0, 10, 0, 10));
        this.tfEmail.setFont(new Font("Times new Roman", 30));
        this.tfEmail.setPrefColumnCount(4);
        this.tfEmail.setPrefWidth(410);
        this.tfEmail.setMaxWidth(410);

        //Instanciation du PasswordField mdp de Inscription
        this.pwInsc = new PasswordField();
        this.pwInsc.setPadding(new Insets(0, 10, 0, 10));
        this.pwInsc.setFont(new Font("Times new Roman", 30));
        this.pwInsc.setPrefColumnCount(4);
        this.pwInsc.setPrefWidth(410);
        this.pwInsc.setMaxWidth(410);

        //Instanciation du TextField nom de Inscription
        this.tfNom = new TextField();
        this.tfNom.setPadding(new Insets(0, 10, 0, 10));
        this.tfNom.setFont(new Font("Times new Roman", 30));
        this.tfNom.setPrefColumnCount(4);
        this.tfNom.setPrefWidth(410);
        this.tfNom.setMaxWidth(410);

       //Instanciation du TextField prenom de Inscription
        this.tfPrenom = new TextField();
        this.tfPrenom.setPadding(new Insets(0, 10, 0, 10));
        this.tfPrenom.setFont(new Font("Times new Roman", 30));
        this.tfPrenom.setPrefColumnCount(4);
        this.tfPrenom.setPrefWidth(410);
        this.tfPrenom.setMaxWidth(410); 
        
        //Instanciation du TextField code Postal de Inscription
        this.tfCodePo = new TextField();
        this.tfCodePo.setPadding(new Insets(0, 10, 0, 10));
        this.tfCodePo.setFont(new Font("Times new Roman", 30));
        this.tfCodePo.setPrefColumnCount(4);
        this.tfCodePo.setPrefWidth(410);
        this.tfCodePo.setMaxWidth(410);

        //Instanciation du TextField ville de Inscription 
        this.tfVille = new TextField();
        this.tfVille.setPadding(new Insets(0, 10, 0, 10));
        this.tfVille.setFont(new Font("Times new Roman", 30));
        this.tfVille.setPrefColumnCount(4);
        this.tfVille.setPrefWidth(410);
        this.tfVille.setMaxWidth(410);

        //Instanciation du TextField adresse de Inscription
        this.tfAdresse = new TextField();
        this.tfAdresse.setPadding(new Insets(0, 10, 0, 10));
        this.tfAdresse.setFont(new Font("Times new Roman", 30));
        this.tfAdresse.setPrefColumnCount(4);
        this.tfAdresse.setPrefWidth(410);
        this.tfAdresse.setMaxWidth(410);
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

    

    public TextField getTfCo() {
        return tfCo;
    }

    public PasswordField getPwCo() {
        return pwCo;
    }

    public TextField getTfEmail() {
        return tfEmail;
    }

    public PasswordField getPwInsc() {
        return pwInsc;
    }

    public TextField getTfNom() {
        return tfNom;
    }

    public TextField getTfPrenom() {
        return tfPrenom;
    }

    public TextField getTfCodePo() {
        return tfCodePo;
    }

    public TextField getTfVille() {
        return tfVille;
    }

    public TextField getTfAdresse() {
        return tfAdresse;
    }
    
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
        boxLigne.setPadding(new Insets(10, 0, 0, 100));
        boxLigne.getChildren().addAll(sep);
        vb.getChildren().addAll(hb, boxLigne);
        root.setTop(vb);
        
    }

    public void reset()
    {
        this.tfCo.setText("");
        this.pwCo.setText("");
        this.tfEmail.setText("");
        this.pwInsc.setText("");
        this.tfNom.setText("");
        this.tfPrenom.setText("");
        this.tfCodePo.setText("");
        this.tfVille.setText("");
        this.tfAdresse.setText("");
    }
    
    public void fenetreAccueil(BorderPane root)
    {
        this.app.getScene().setRoot(root);
        setTop(root, "Bienvenue");
        VBox vb = new VBox(100);
        Button connexion = new Button("Connexion");
        connexion.setFont(new Font("Times new Roman", 30));
        connexion.setPadding(new Insets(0, 130, 0, 130));
        connexion.setPrefWidth(410);
        
        Button inscription = new Button("Inscription");
        inscription.setFont(new Font("Times new Roman", 30));
        inscription.setPadding(new Insets(0, 130, 0, 130));
        inscription.setPrefWidth(410);
        Button quitter = new Button("Quitter");
        quitter.setFont(new Font("Times new Roman", 30));
        quitter.setPadding(new Insets(0, 130, 0, 130));
        quitter.setPrefWidth(410);

        connexion.setOnAction(new controleurAccueil(this.app));
        inscription.setOnAction(new controleurAccueil(this.app));
        quitter.setOnAction(new controleurAccueil(this.app));

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


        Label lbPasswd = new Label("Mot de passe");
        lbPasswd.setPadding(new Insets(20, 0, 10, 0));
        lbPasswd.setFont(new Font("Times new Roman", 30));
        lbPasswd.setStyle("-fx-text-fill: #3f4353");

        HBox boutonsBas = new HBox(110);

        Button accueil = new Button("Accueil");
        accueil.setFont(new Font("Times new Roman", 20));
        accueil.setPadding(new Insets(3, 10, 3, 10));
        accueil.setPrefWidth(150);
        Button connecte = new Button("Se connecter");
        connecte.setFont(new Font("Times new Roman", 20));
        connecte.setPadding(new Insets(3, 10, 3, 10));
        connecte.setPrefWidth(150);
        
        boutonsBas.getChildren().addAll(accueil, connecte);
        boutonsBas.setPadding(new Insets(50, 0, 30, 0));
        boutonsBas.setAlignment(Pos.CENTER);

        accueil.setOnAction(new controleurConnexion(app, this.app.getModele()));
        connecte.setOnAction(new controleurConnexion(app, this.app.getModele()));

        vb.getChildren().addAll(lbEmail, this.tfCo, lbPasswd, this.pwCo, boutonsBas);
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
        lbEmail.setPadding(new Insets(0, 0, 10, 0));
        lbEmail.setFont(new Font("Times new Roman", 30));
        lbEmail.setStyle("-fx-text-fill: #3f4353");

        Label lbPasswd = new Label("Mot de passe");
        lbPasswd.setPadding(new Insets(20, 0, 10, 0));
        lbPasswd.setFont(new Font("Times new Roman", 30));
        lbPasswd.setStyle("-fx-text-fill: #3f4353");

        Label lbNom = new Label("Nom");
        lbNom.setPadding(new Insets(20, 0, 10, 0));
        lbNom.setFont(new Font("Times new Roman", 30));
        lbNom.setStyle("-fx-text-fill: #3f4353");

        Label lbPrenom = new Label("Prenom");
        lbPrenom.setPadding(new Insets(20, 0, 10, 0));
        lbPrenom.setFont(new Font("Times new Roman", 30));
        lbPrenom.setStyle("-fx-text-fill: #3f4353");

        HBox boutonsBas = new HBox(180);

        Button accueil = new Button("Accueil");
        accueil.setFont(new Font("Times new Roman", 20));
        accueil.setStyle(
            "-fx-background-radius : 20;" +
            "-fx-border-radius : 20;"
            );
        accueil.setPadding(new Insets(3, 10, 3, 10));
        accueil.setPrefWidth(150);
        Button connecte = new Button("S'inscrire");
        connecte.setFont(new Font("Times new Roman", 20));
        connecte.setStyle(
            "-fx-background-radius : 20;" +
            "-fx-border-radius : 20;"
            );
        connecte.setPadding(new Insets(3, 10, 3, 10));
        connecte.setPrefWidth(150);
        
        accueil.setOnAction(new controleurInscription(this.app, this.app.getModele()));
        connecte.setOnAction(new controleurInscription(this.app, this.app.getModele()));
        boutonsBas.getChildren().addAll(accueil, connecte);
        boutonsBas.setPadding(new Insets(50, 0, 30, 0));
        boutonsBas.setAlignment(Pos.CENTER);

        Label lbCodePos = new Label("Code Postal");
        lbCodePos.setPadding(new Insets(20, 0, 10, 0));
        lbCodePos.setFont(new Font("Times new Roman", 30));
        lbCodePos.setStyle("-fx-text-fill: #3f4353");

        Label lbVille = new Label("Ville");
        lbVille.setPadding(new Insets(20, 0, 10, 0));
        lbVille.setFont(new Font("Times new Roman", 30));
        lbVille.setStyle("-fx-text-fill: #3f4353");

        Label lbAdresse = new Label("Adresse");
        lbAdresse.setPadding(new Insets(20, 0, 10, 0));
        lbAdresse.setFont(new Font("Times new Roman", 30));
        lbAdresse.setStyle("-fx-text-fill: #3f4353");

        droite.getChildren().addAll(lbCodePos, this.tfCodePo, lbVille, this.tfVille, lbAdresse, this.tfAdresse);
        centre.setRight(droite);

        gauche.getChildren().addAll(lbEmail, this.tfEmail, lbPasswd, this.pwInsc, lbNom, this.tfNom, lbPrenom, this.tfPrenom);
        centre.setLeft(gauche);

        centre.setBottom(boutonsBas);
        root.setCenter(centre);
    }
}
