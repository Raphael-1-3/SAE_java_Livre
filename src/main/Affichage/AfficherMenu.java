package main.Affichage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.math.RoundingMode;
public class AfficherMenu 
{
   public static String Menu(String titre, List<String> options) {
        // Trouver la longueur maximale parmi le titre et les options
        String motLePlusLong = Collections.max(options, Comparator.comparingInt(String::length));
        int maxOptionLength = motLePlusLong.length();
        int maxNumLength = String.valueOf(options.size()).length(); // taille du plus grand numéro (1, 10, 100...)
        int optionLineLength = maxNumLength + 2 + 1 + maxOptionLength; // ex: "10. OptionTexte"
        
        int titreLength = titre.length();
        int contentWidth = Math.max(optionLineLength, titreLength);
        int boxWidth = contentWidth + 4; // Ajout des marges et bords : "│ " + content + " │"

        StringBuilder str = new StringBuilder();

        // Ligne du haut
        str.append("╭").append("─".repeat(boxWidth - 2)).append("╮\n");

        // Ligne du titre, centré
        int paddingLeft = (boxWidth - 2 - titreLength) / 2;
        int paddingRight = boxWidth - 2 - titreLength - paddingLeft;
        str.append("│").append(" ".repeat(paddingLeft)).append(titre)
           .append(" ".repeat(paddingRight)).append("│\n");

        // Séparateur
        str.append("├").append("─".repeat(boxWidth - 2)).append("┤\n");

        // Lignes des options
        for (int i = 0; i < options.size(); i++) {
            String numero = (i + 1) + ".";
            String opt = options.get(i);
            String ligne = numero + " " + opt;
            int espaceFin = contentWidth - ligne.length();
            str.append("│ ").append(ligne).append(" ".repeat(espaceFin)).append(" │\n");
        }

        // Ligne du bas
        str.append("╰").append("─".repeat(boxWidth - 2)).append("╯\n");

        return str.toString();
    }
}
