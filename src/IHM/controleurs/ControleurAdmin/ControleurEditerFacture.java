package IHM.controleurs.ControleurAdmin;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.BD.ActionBD;
import main.Exceptions.PasDeTelUtilisateurException;
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
import javafx.fxml.FXML;

import java.io.IOException;
import java.lang.classfile.attribute.LocalVariableTypeTableAttribute;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import IHM.controleurs.ControleurVendeur.ControleurPasserCommande;
import IHM.vues.*;
import main.app.*;

public class ControleurEditerFacture implements EventHandler<ActionEvent>{
    private LivreExpress app;
    private ActionBD modele;
    private Client cli;

    public ControleurEditerFacture(LivreExpress app, ActionBD modele, Client c)
    {
        this.app = app;
        this.modele = modele;
        this.cli = c;
    }

    public void handle(ActionEvent event)
    {
        String an = this.app.getVueAdmin().getTfAnnee().getText();
        String mois = this.app.getVueAdmin().getTfMois().getText();

        if (an.isEmpty() || mois.isEmpty())
        {
            this.app.getVueAdmin().popUpChampsVides().show();
        }
        else
        {
            try (PDDocument doc = new PDDocument())
            {
                Integer annee = Integer.parseInt(an);
                Integer moisInt = Integer.parseInt(mois);
                String fac = "";
                if (this.cli == null)
                {
                    fac = this.modele.factureMagasin(this.app.getVueAdmin().getMagChoisi(), moisInt, annee);
                }
                else
                {
                    fac = this.modele.factureClient(cli, moisInt, annee);
                }
                PDPage page = new PDPage();
                doc.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(fac);
                contentStream.endText();
                contentStream.close();

                doc.save("Facture.pdf");
                this.app.getVueAdmin().afficherPopUpFactures(fac);
            }
            catch (NumberFormatException e)
            {
                this.app.getVueAdmin().popUpPasUnNbr().show();
            }
            catch (SQLException e)
            {
                System.out.println("Erreur SQL" + e);
            }
            catch (IOException e)
            {
                System.out.println("Erreur lors de l'edition en pdf");
            }
        }
    }
}
