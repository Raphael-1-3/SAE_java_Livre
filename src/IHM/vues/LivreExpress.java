package IHM.vues;
import main.BD.ActionBD;
import main.BD.ConnexionMySQL;
import main.app.*;

import javafx.application.Application;
import javafx.application.Platform;
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
import java.util.Scanner;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class LivreExpress extends Application {

    private VueConnexion vueCo;
    private VueClient vueclient;
    private VueVendeur vueVendeur;
    private VueAdmin vueAdmin;
    private VueConnexionBD vueCoBD;
    private ActionBD modele;
    private BorderPane root;
    private Scene scene;
    private Stage st;

    @Override
    public void init()
    {
        /* System.out.println("Connexion base");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrer le nom du serveur : ");
        String nomServeur = scanner.nextLine();

        System.out.print("Entrer le login : ");
        String nomLogin = scanner.nextLine();

        System.out.print("Entrer le mot de passe : ");
        String motDePasse = scanner.nextLine();
        scanner.close();*/
        ConnexionMySQL connexion = null;

        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream("src/dbUser/db.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load properties: " + e.getMessage());
            return;
        }
        String serv = props.getProperty("db.nomServ");
        String user = props.getProperty("db.user");
        String passwd = props.getProperty("db.password");
        String base = props.getProperty("db.base");

        try {
            connexion = new ConnexionMySQL();
            //connexion.connecter("laptop-robin", "LibrairieJava", "root", "robin");
            connexion.connecter(serv, base, user, passwd); //"localhost", "LibrairieJava", "root", "raphe"
            if (connexion.isConnecte()) 
            {
                this.modele = new ActionBD(connexion);
                System.out.println("Connexion réussie !");
            }
        }
        catch (Exception e)
        {
            System.out.println("ERREUR" + e);
        }
        this.vueCo = new VueConnexion(this);
        root = new BorderPane();
    }

    @Override
    public void start(Stage stage) throws Exception{
        this.st = stage;
        if (this.modele == null)
        {
            this.vueCoBD = new VueConnexionBD(this);
            this.vueCoBD.fenetreConnexion();
        }
        else
        {
            this.scene = new Scene(this.root);
            stage.setTitle("LivreExpress");
            stage.setScene(scene);
            Image logo = new Image("file:./img/logo.jpg");
            stage.getIcons().add(logo);
            this.root.setPrefSize(1300, 700);
            this.root.setStyle("-fx-background-color : #d4d5d5;");
            scene.getStylesheets().add("file:./src/IHM/styles/globalCSS.css");
            //vueClient vueclient = new  vueClient(this, client);

            vueCo.fenetreAccueil(root);
            //scene.setRoot(vueclient);
            //scene.setRoot(vuead);
            stage.setScene(scene);
            stage.show();
            System.out.println("MARCHE");
        }

    }

    public void setCo(ConnexionMySQL co)
    {
        this.modele = new ActionBD(co);
    }

    public Stage getStage()
    {
        return this.st;
    }

    public VueConnexion getVueConnexion ()
    {
        return this.vueCo;
    }

    public VueConnexionBD getVueConnexionBD()
    {
        return this.vueCoBD;
    }

    public VueVendeur getVueVendeur()
    {
        return this.vueVendeur;
    }

    public VueClient getVueClient() {
        return this.vueclient;
    }

    public VueAdmin getVueAdmin()
    {
        return this.vueAdmin;
    }

    public void setVueClient(VueClient vueclient) {
        this.vueclient = vueclient;
    }

    public void setVueVendeur(VueVendeur vueVendeur)
    {
        this.vueVendeur = vueVendeur;
    }

    public void setVueAdmin(VueAdmin vue)
    {
        this.vueAdmin = vue;
    }

    public Scene getScene()
    {
        return this.scene;
    }

    public ActionBD getModele()
    {
        return this.modele;
    }

    public BorderPane getRoot()
    {
        return this.root;
    }

    public void quiiter()
    {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
