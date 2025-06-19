package IHM.vues;

import main.*;
import IHM.controleurs.ControleurAdmin.*;
import IHM.controleurs.ControleurClient.ControleurChosirMagasin;
import main.BD.ActionBD;
import main.app.*;
import main.BD.*;
import main.Exceptions.*;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import junit.framework.TestFailure;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;


public class VueAdmin extends BorderPane
{
    private ActionBD modele;
    private Administrateur admin;
    private LivreExpress LEApp;
    private TextField barRecherche;
    private BorderPane centre;
    private HBox TitrePage;
    private HBox contenaBox;
    private VBox box1;
    private VBox box2;
    private VBox box3;
    private PasswordField pwField;
    private TextField tfAdresse;
    private TextField tfVille;
    private TextField tfCodePostal;
    private Magasin MagChoisi;
    private ComboBox<String> selectionAction;
    private ComboBox<String> selectionStat;
    private TextField recherchStat;
    private ComboBox<String> selectionRecherche;
    private Livre livreChoisi;
    private List<Livre> listeSuggestions;

    private TextField tfEmail = new TextField();
    private PasswordField pfMDP = new PasswordField();
    private TextField tfNom = new TextField();
    private TextField tfPrenom = new TextField();
    private TextField tfIdMag = new TextField();
    private TextField tfNomLib = new TextField();
    private TextField tfVilleLib = new TextField();
    private TextField tfISBN = new TextField();
    private TextField tfTitre = new TextField();
    private TextField tfAuteur = new TextField();
    private TextField tfEditeur = new TextField();
    private TextField tfPrix = new TextField();
    private TextField tfNbPages = new TextField();
    private TextField tfLivre = new TextField();
    private TextField tfMagasin = new TextField();
    private TextField tfClient = new TextField();
    private TextField tfMagDep = new TextField();
    private TextField tfMagArr = new TextField();
    private TextField tfQte = new TextField();
    private TextField tfMois = new TextField();
    private TextField tfAnnee = new TextField();

    /**
     * Instancie la fenetre liee admin
     * @param LEApp
     */
    public VueAdmin(LivreExpress LEApp, ActionBD modele, Administrateur admin) throws SQLException
    {
        super();
        this.listeSuggestions = new ArrayList<>();
        this.selectionRecherche = new ComboBox<>();
        this.selectionAction = new ComboBox<>();
        this.recherchStat = new TextField();
        this.LEApp = LEApp;
        this.admin = admin;
        this.modele = modele;
        this.barRecherche = new TextField();
        this.contenaBox = new HBox();
        this.box1 = new VBox();
        this.box1.setPrefSize(400, 400);
        this.box2 = new VBox();
        this.box2.setPrefSize(400, 400);
        this.box3 = new VBox();
        this.box3.setPrefSize(400, 400);
        this.centre = new BorderPane();
        this.TitrePage = new HBox();
        this.TitrePage.setPrefHeight(25);
        this.box1.setStyle("-fx-background-radius : 15px;" + 
        "-fx-background-color : #f9f9f9;" + 
        "-fx-background : #f9f9f9;");
        this.contenaBox.setStyle("-fx-background-radius : 15px;" + 
        "-fx-background-color : #f9f9f9;" + 
        "-fx-background : #f9f9f9;" + 
        "-fx-border-width : 2px;" + 
        "-fx-border-color : #df9f53;" + 
        "-fx-border-radius : 15px;");
        this.box2.setStyle("-fx-background-radius : 15px;" + 
        "-fx-background-color : #f9f9f9;" + 
        "-fx-background : #f9f9f9;" + 
        "-fx-border-width : 0 0 0 2px;" + 
        "-fx-border-color : #df9f53");
        this.box3.setStyle("-fx-background-color: #f9f9f9;" + 
        "-fx-background-radius : 15px;" +
        "-fx-border-width : 0 0 0 2px;" +
        "-fx-border-color : #df9f53;" + 
        "-fx-background : #f9f9f9;");
        this.setCenter(this.centre);
        BorderPane.setMargin(this.centre, new Insets(60, 120, 60, 60));
        this.centre.setMaxSize(1200, 450);
        this.centre.setMinSize(1200, 450);
        BorderPane.setAlignment(this.centre, Pos.CENTER);
        this.setPrefSize(1300, 700);
        this.top();
        this.pannelSelectionMag();
        

        this.pwField = new PasswordField();
        this.pwField.setMaxWidth(380);
        this.tfAdresse = new TextField();
        this.tfAdresse.setMaxWidth(380);
        this.tfCodePostal = new TextField();
        this.tfCodePostal.setMaxWidth(380);
        this.tfVille = new TextField();
        this.tfVille.setMaxWidth(380);
        //this.setBottom(this.bottom());

        this.barRecherche.setDisable(true);
        this.selectionAction.setDisable(true);


    }
    
    public void activer()
    {
        this.barRecherche.setDisable(false);
        this.selectionAction.setDisable(false);
    }

    public Alert popUpChampsVides()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs");
        return alert;
    }

    public Alert popUpActionEffectuee()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Action effectuee !");
        return alert;
    }

    public Alert popUpPasUnNbr()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer un nombre dans le code postal");
        return alert;
    }

    public void top() throws SQLException
    {
        VBox top = new VBox();
        HBox sstop1 = new HBox();
        HBox sstop2 = new HBox();

        // ssHbox 1 ----
        Label titre = new Label("Admistrateur");
        titre.setFont(new Font("Times new Roman", 50));
        titre.setPadding(new Insets(15, 15, 0, 0));
        titre.setMinWidth(350);
        titre.setMaxWidth(350);
        ImageView logo = new ImageView(new Image("file:./img/logo.jpg"));
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        Rectangle bordure = new Rectangle(logo.getFitWidth(), logo.getFitHeight());
        bordure.setArcWidth(30);
        bordure.setArcHeight(30);
        logo.setClip(bordure);
        Label nomUtil = new Label("Connecté en tant que " + this.admin.getNom() + " " + this.admin.getPrenom());
        nomUtil.setFont(new Font("Times new Roman", 25));
        nomUtil.setPadding(new Insets(35, 0, 0, 0));
        nomUtil.setMaxWidth(400);
        nomUtil.setPrefWidth(400);
        nomUtil.setMinWidth(400);
        HBox boutonsDroite = new HBox(10);
        Button store = new Button("");
        ImageView storeIcon = new ImageView(new Image("file:./img/store.png"));
        storeIcon.setFitHeight(40);
        storeIcon.setFitWidth(40);
        store.setGraphic(storeIcon);
        store.setPrefSize(50, 50);
        store.setOnAction(new ControleurAllerChoisirMag(this.LEApp, this.modele));
        Button param = new Button("");
        ImageView paramIcon = new ImageView(new Image("file:./img/param.png"));
        paramIcon.setFitHeight(40);
        paramIcon.setFitWidth(40);
        param.setGraphic(paramIcon);
        param.setPadding(new Insets(0, 0, 0, 0));
        param.setPrefSize(50, 50);
        
        //param.setOnAction(new ControleurAllerParametres(this.modele, this.LEApp));
        Button deconnexion = new Button();
        ImageView decoIcon = new ImageView(new Image("file:./img/deco.png"));
        decoIcon.setFitWidth(40);
        decoIcon.setFitHeight(40);
        deconnexion.setGraphic(decoIcon);
        deconnexion.setText(""); 
        deconnexion.setFont(new Font("Times new Roman", 20));
        deconnexion.setPadding(new Insets(0, 0, 0, 0));
        deconnexion.setPrefWidth(50);
        deconnexion.setPrefHeight(50);
        deconnexion.setOnAction(new ControleurRetourAccueil(this.modele, this.LEApp));

        boutonsDroite.setPadding(new Insets(30, 0, 0, 105));
        boutonsDroite.setAlignment(Pos.TOP_RIGHT);
        
        boutonsDroite.getChildren().addAll(store, deconnexion, param);

        sstop1.setPadding(new Insets(3, 3, 3, 3));
        sstop1.setSpacing(10);
        sstop1.getChildren().addAll(logo, titre, nomUtil, boutonsDroite);

        // ssHBox 2 
        this.selectionAction.getItems().addAll(
            "Créer un vendeur",
            "Ajouter une librairie",
            "Panneau de Bord",
            "Ajouter un livre",
            "Regarder les disponibilités",
            "Passer une commande pour un Client",
            "Transférer un livre",
            "Obtenir les factures",
            "Choisir un magasin"
            
        );
        selectionAction.setOnAction(new ControleurChangerPage(modele, LEApp, selectionAction));
        this.selectionAction.setPromptText("Choisissez une action");
        this.selectionAction.setPadding(new Insets(0, 0, 0, 15));

        this.selectionAction.setPromptText("Selectionner un magasin");

    
        sstop2.setPadding(new Insets(10, 0, 10, 50));
        sstop2.setSpacing(15);
        sstop2.getChildren().addAll(this.selectionAction);
        //
        HBox boxLigne = new HBox();
        Line sep = new Line(0, 0, 1100, 0);
        boxLigne.setPadding(new Insets(5, 0, 5, 100));
        boxLigne.getChildren().addAll(sep);

        this.setStyle("-fx-background-color : #d4d5d5;");
        //
        //sstop1.setStyle("-fx-background-color: blue;");
        //sstop2.setStyle("-fx-background-color: lightblue;");
        //boxLigne.setStyle("-fx-background-color: lightgreen;");
        //

        top.getChildren().addAll(sstop1, boxLigne, sstop2);
        this.setTop(top);
    }




    public void afficheInfoMag()
    {
        
        VBox infoMag = new VBox();
        infoMag.setSpacing(10);
        infoMag.setPadding(new Insets(15));
        Label nomMag = new Label("Nom : " + this.MagChoisi.getNomMag());
        nomMag.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        Label ville = new Label("Ville : " + this.MagChoisi.getVilleMag());
        Label idMag = new Label("ID Magasin : " + this.MagChoisi.getIdmag());

        infoMag.getChildren().addAll(nomMag, ville, idMag);
        this.centre.setLeft(infoMag);
        try {
            List<Vendeur> vendeurs = this.modele.getVendeurMagasin(this.MagChoisi);
            VBox vboxVendeurs = new VBox();
            vboxVendeurs.setSpacing(5);
            Label lblVendeurs = new Label("Vendeurs :");
            lblVendeurs.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
            vboxVendeurs.getChildren().add(lblVendeurs);

            if (vendeurs != null && !vendeurs.isEmpty()) {
            for (Vendeur v : vendeurs) {
                Label vendeurLabel = new Label(v.getNom() + " " + v.getPrenom() + " (ID: " + v.getId() + ")");
                vboxVendeurs.getChildren().add(vendeurLabel);
            }
            } else {
            vboxVendeurs.getChildren().add(new Label("Aucun vendeur dans ce magasin."));
            }

            infoMag.getChildren().add(vboxVendeurs);
        } catch (SQLException e) {
            infoMag.getChildren().add(new Label("Erreur lors du chargement des vendeurs."));
        }
    }

    public void creerVendeur() 
    {
        // Création du formulaire pour ajouter un vendeur en utilisant les attributs déjà créés
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px;");

        Label titre = new Label("Créer un Vendeur");
        titre.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));

        tfNom.setPromptText("Nom");
        tfPrenom.setPromptText("Prénom");
        tfEmail.setPromptText("Email");
        pfMDP.setPromptText("Mot de passe");

        Button btnCreer = new Button("Créer");
        btnCreer.setOnAction(new ControleurCreerVendeur(modele, LEApp));

        form.getChildren().addAll(titre, tfNom, tfPrenom, tfEmail, pfMDP, btnCreer);
        this.centre.setCenter(form);
    }

    public void ajouterLibrairie() throws SQLException
    {
        HBox topcenter = new HBox();
        this.selectionRecherche.getItems().addAll(
            "Rechercher par nom de livre",
            "Rechercher par auteur",
            "Rechercher par classification",
            "Rechercher par éditeur", 
            "Rechecher par magasin"
        );
        this.selectionRecherche.setPadding(new Insets(0, 0, 0, 15));
        
        Button executerAction = new Button("Exécuter");
        executerAction.setPrefWidth(100);
        this.selectionRecherche.setPromptText("Choisissez une action"); 
        VBox layout = new VBox(10); 
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(this.selectionRecherche, executerAction);

        this.barRecherche = new TextField();
        this.barRecherche.setPromptText("Rechercher...");

        ControleurRechercheDynamique controleurRecherche = new ControleurRechercheDynamique(this.LEApp, this.modele);
        this.centre.setCenter(controleurRecherche.getListeSuggestions());

        this.barRecherche.setPrefWidth(600); // Largeur préférée
        topcenter.setPadding(new Insets(10, 0, 10, 50));
        topcenter.setSpacing(15);
        topcenter.getChildren().addAll(this.selectionRecherche, this.barRecherche);
        this.centre.setTop(topcenter);
    }

    public void panneauDeBord() 
    {
        // Création d'une ComboBox pour les statistiques du panneau de bord
        this.selectionStat = new ComboBox<>();
        this.selectionStat.getItems().addAll(
            "NombreDeLivreVendueParMagasinParAns",
            "chiffreAffaireParClassificationParAns",
            "CAMagasinParMoisParAnnee",
            "CAVenteEnLigneEnMagasinParAnnee",
            "nombreAuteurParEditeur",
            "nombreClientParVilleQuiOntAcheterAuteur",
            "valeurStockMagasin",
            "statsCAParClientParAnnee",
            "auteurLePlusVenduParAnnee"
        );
        this.selectionStat.setPromptText("Choisissez une statistique");
        HBox hboxCombo = new HBox(this.selectionStat, this.recherchStat);
        this.recherchStat.setPromptText("entre une annee ou un auteur en fonction");
        hboxCombo.setAlignment(Pos.CENTER); 
        hboxCombo.setPadding(new Insets(10)); 
        this.centre.setTop(hboxCombo);
        this.selectionStat.setOnAction(new ControleurSelectionGraphique(modele, LEApp));
        this.contenaBox.getChildren().addAll(box1, box2, box3);
        this.centre.setCenter(contenaBox);
        
    }

    public void ajouterLivre() 
    {
        
    }

    public void regarderDisponibilites() 
    {
        // Code pour afficher les disponibilités
    }

    public void passerCommandeClient() 
    {
        // Code pour afficher la vue de commande client
    }

    public void transfererLivre() 
    {
        // Code pour afficher la vue de transfert de livre
    }

    public void obtenirFactures() 
    {
        // Code pour afficher les factures
    }


    public void afficheGraphiqueNombreDeLivreVendueParMagasinParAns(HashMap<Integer, HashMap<Magasin, Integer>> donnees) 
    {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Magasin");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de livres vendus");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Nombre de livres vendus par magasin");

        for (Map.Entry<Integer, HashMap<Magasin, Integer>> entreeAnnee : donnees.entrySet()) 
        {
            Integer annee = entreeAnnee.getKey();
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(annee.toString());

            for (Map.Entry<Magasin, Integer> entreeMagasin : entreeAnnee.getValue().entrySet()) 
            {
                Magasin magasin = entreeMagasin.getKey();
                Integer nbLivres = entreeMagasin.getValue();
                serie.getData().add(new XYChart.Data<>(magasin.getNomMag(), nbLivres));
            }

            barChart.getData().add(serie);
        }
        barChart.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        Stage stage = new Stage();
        BorderPane root = new BorderPane(barChart);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Nombre de livre vendue par magasins par ans");
        stage.show();
        
    }

    public void afficheGraphiqueChiffreAffaireParClassificationParAns(HashMap<Classification, Integer> donnees) 
    {
        PieChart pieChart = new PieChart();

        for (Classification classification : donnees.keySet()) {
            String nom = classification.getNomClass(); 
            int montant = donnees.get(classification);
            pieChart.getData().add(new PieChart.Data(nom, montant));
        }

        pieChart.setTitle("Chiffre d'affaire 2025 par thème");

        VBox chart = new VBox(pieChart);
        Stage stage = new Stage();
        BorderPane root = new BorderPane(chart);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Chiffre d'affaire 2025 par thème");
        stage.show();
    }
    
    
    public void afficheGraphiqueCAMagasinParMoisParAnnee(HashMap<Integer, HashMap<Magasin, Integer>> donnees) 
    {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Mois");
    
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Chiffre d'affaires (€)");
        
        AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle("Évolution CA des magasins par mois");
        
        Map<String, XYChart.Series<String, Number>> seriesMap = new HashMap<>();
        
        for (int mois = 1; mois <= 12; mois++) {
            String moisStr = String.valueOf(mois);
            HashMap<Magasin, Integer> caParMag = donnees.get(mois);
            if (caParMag != null) {
                for (Map.Entry<Magasin, Integer> entry : caParMag.entrySet()) {
                    String nomMag = entry.getKey().getNomMag(); 
                    int ca = entry.getValue();
                
                    
                    XYChart.Series<String, Number> serie = seriesMap.computeIfAbsent(nomMag, k -> {
                        XYChart.Series<String, Number> s = new XYChart.Series<>();
                        s.setName(k);
                        return s;
                    });
                
                    serie.getData().add(new XYChart.Data<>(moisStr, ca));
                }
            }
        }
    
        areaChart.getData().addAll(seriesMap.values());
        Stage stage = new Stage();
        BorderPane root = new BorderPane(areaChart);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Graphique CA des Magasins");
        stage.show();
    }
    
    public void pannelSelectionMag()
    {
        try
        {   
            this.centre.getChildren().clear();
            ScrollPane sp = new ScrollPane();
            sp.setMaxSize(1200, 450);
            sp.setMinSize(1200, 450);
            TilePane contenu = new TilePane();
            contenu.setPadding(new Insets(5));
            contenu.setMaxSize(1180, 450);
            contenu.setMaxSize(1180, 450);
            contenu.setHgap(60);
            contenu.setVgap(30);
            for (Magasin m : this.modele.getAllMagasins())
            {
                VBox vb = new VBox();
                vb.setStyle("-fx-border-color : black;" + 
                "-fx-border-width : 2px;" + 
                "-fx-border-radius : 10px;" + 
                "-fx-background-color : #fcfcfc;" + 
                "-fx-background-radius : 10px;");
                HBox.setMargin(vb, new Insets(10));
                vb.setPrefSize(350, 100);
                vb.setMaxSize(350, 100);
                vb.setMinSize(350, 100);
                Label nom = new Label(m.getNomMag());
                nom.setFont(new Font(20));
                nom.setPadding(new Insets(20, 0, 0, 75));
                nom.setStyle("-fx-font-weight : bold;");
                Label ville = new Label(m.getVilleMag());
                ville.setFont(new Font(20));
                ville.setPadding(new Insets(10, 0, 20, 75));
                vb.getChildren().addAll(nom, ville);
                vb.setOnMouseClicked(new ControleurSelectionMag(this.modele, this.LEApp, m));
                contenu.getChildren().add(vb);
            }
                HBox hb = new HBox();
                hb.setStyle("-fx-border-color : black;" + 
                "-fx-border-width : 2px;" + 
                "-fx-border-radius : 10px;" + 
                "-fx-background-color : #fcfcfc;" + 
                "-fx-background-radius : 10px;");
                HBox.setMargin(hb, new Insets(10));
                hb.setPrefSize(350, 100);
                hb.setMaxSize(350, 100);
                hb.setMinSize(350, 100);
                ImageView icone = new ImageView(new Image("file:./img/add.png"));
                icone.setFitHeight(80);
                icone.setFitWidth(80);
                Label aj = new Label("Ajouter un Magasin");
                aj.setFont(new Font(20));
                aj.setPadding(new Insets(20, 0, 0, 15));
                aj.setStyle("-fx-font-weight : bold;");
                hb.getChildren().addAll(icone, aj);
                hb.setOnMouseClicked(new ControleurAllerCreerMag(this.LEApp, this.modele));
                contenu.getChildren().add(hb);
            sp.setContent(contenu);
            this.centre.setCenter(sp);
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

    public void afficheGraphiqueCAVenteEnLigneEnMagasinParAnnee(HashMap<Integer, HashMap<String, Integer>> donnees) 
    {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Annee");
    
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Chiffre d'affaires (€)");
        
        AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle("Évolution CA des ventes en lignes et magasin");
        
        Map<String, XYChart.Series<String, Number>> seriesMap = new HashMap<>();
        
        for (int annee = 2000; annee <= 2025;  ++annee) {
            String anneeStr = String.valueOf(annee);
            HashMap<String, Integer> caParVent = donnees.get(annee);
            if (caParVent != null) {
                for (Map.Entry<String, Integer> entry : caParVent.entrySet()) {
                    String typeVente = entry.getKey();
                    int ca = entry.getValue();
                
                    
                    XYChart.Series<String, Number> serie = seriesMap.computeIfAbsent(typeVente, k -> {
                        XYChart.Series<String, Number> s = new XYChart.Series<>();
                        s.setName(k);
                        return s;
                    });
                
                    serie.getData().add(new XYChart.Data<>(anneeStr, ca));
                }
            }
        }
    
        areaChart.getData().addAll(seriesMap.values());
        Stage stage = new Stage();
        BorderPane root = new BorderPane(areaChart);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Graphique CA des Magasins");
        stage.show();
    }

    public void afficheGraphiqueNombreAuteurParEditeur(HashMap<Editeur, Integer> donnees) 
    {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Nombre d'auteurs");
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Éditeur");

        BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Dix éditeurs les plus importants en nombre d'auteurs");

        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (Map.Entry<Editeur, Integer> entry : donnees.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getValue(), entry.getKey().getNomEdit()));
        }
        barChart.getData().add(series);
        barChart.setLegendVisible(false);

        Stage stage = new Stage();
        Scene scene = new Scene(barChart, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Graphique 5");
        stage.show();
    }
    
    public void afficheGraphiqueNombreClientParVilleQuiOntAcheterAuteur(HashMap<String, Integer> donnees) 
    {
        //TODO plus tard avec recherche dynamique auteur 
    }
    public void afficheGraphiqueValeurStockMagasin(HashMap<Magasin, Integer> donnees) 
    {
        PieChart pieChart = new PieChart();

        for (Map.Entry<Magasin, Integer> entry : donnees.entrySet()) {
            Magasin magasin = entry.getKey();
            int montant = entry.getValue();
            String label = magasin.getNomMag() + " : " + montant;
            pieChart.getData().add(new PieChart.Data(label, montant));
        }

        pieChart.setTitle("Valeur du stock par magasin");

        VBox chart = new VBox(pieChart);
        Stage stage = new Stage();
        BorderPane root = new BorderPane(chart);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Valeur stock magasin");
        stage.show();
    }
    public void afficheGraphiqueStatsCAParClientParAnnee(HashMap<Integer, HashMap<String, Double>> donnees) 
    {
        // Axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Année");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("CA par client");

        // LineChart
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Evolution CA Total par client");

        // Séries
        XYChart.Series<String, Number> serieMax = new XYChart.Series<>();
        serieMax.setName("Max - maximum");
        XYChart.Series<String, Number> serieMin = new XYChart.Series<>();
        serieMin.setName("Min - minimum");
        XYChart.Series<String, Number> serieMoy = new XYChart.Series<>();
        serieMoy.setName("Moyenne - moyenne");

        // Remplir les séries
        List<Integer> annees = new ArrayList<>(donnees.keySet());
        Collections.sort(annees);
        for (Integer annee : annees) {
            HashMap<String, Double> stats = donnees.get(annee);
            serieMax.getData().add(new XYChart.Data<>(annee.toString(), stats.get("max")));
            serieMin.getData().add(new XYChart.Data<>(annee.toString(), stats.get("min")));
            serieMoy.getData().add(new XYChart.Data<>(annee.toString(), stats.get("avg")));
        }

        lineChart.getData().addAll(serieMax, serieMin, serieMoy);

        // Affichage dans une nouvelle fenêtre
        Stage stage = new Stage();
        Scene scene = new Scene(lineChart, 700, 400);
        stage.setScene(scene);
        stage.setTitle("Graphique 8");
        stage.show(); 
    }
    public void afficheGraphiqueAuteurLePlusVenduParAnnee(HashMap<Integer, HashMap<Auteur, Integer>> donnees) {}


    public void setMagChoisi(Magasin magChoisi) { this.MagChoisi = magChoisi; }
    public TextField getRecherchStat() { return this.recherchStat; }
    public ComboBox<String> getSelectionStat() { return this.selectionStat; }
    public Magasin getMagChoisi() { return this.MagChoisi; }
    public ActionBD getModele() { return this.modele; }
    public LivreExpress getLEApp() { return this.LEApp; }
    public TextField getbarRecherche() { return this.barRecherche; }
    public Administrateur getclient() { return this.admin; }
    public PasswordField getPwField() { return this.pwField; }
    public TextField getTfAdresse() { return this.tfAdresse; }
    public TextField getTfVille() { return this.tfVille; }
    public TextField getTfCodePostal() {return this.tfCodePostal;}
    public TextField getTfEmail() { return tfEmail; }
    public PasswordField getPfMDP() { return pfMDP; }
    public TextField getTfNom() { return tfNom; }
    public TextField getTfPrenom() { return tfPrenom; }
    public TextField getTfIdMag() { return tfIdMag; }
    public TextField getTfNomLib() { return tfNomLib; }
    public TextField getTfVilleLib() { return tfVilleLib; }
    public TextField getTfISBN() { return tfISBN; }
    public TextField getTfTitre() { return tfTitre; }
    public TextField getTfAuteur() { return tfAuteur; }
    public TextField getTfEditeur() { return tfEditeur; }
    public TextField getTfPrix() { return tfPrix; }
    public TextField getTfNbPages() { return tfNbPages; }
    public TextField getTfLivre() { return tfLivre; }
    public TextField getTfMagasin() { return tfMagasin; }
    public TextField getTfClient() { return tfClient; }
    public TextField getTfMagDep() { return tfMagDep; }
    public TextField getTfMagArr() { return tfMagArr; }
    public TextField getTfQte() { return tfQte; }
    public TextField getTfMois() { return tfMois; }
    public TextField getTfAnnee() { return tfAnnee; }
    public ComboBox<String> getSelectionAction() { return this.selectionAction; }

}
