package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Executable{
    public static void main (String [] args)
    {
        List<String> maListe = new ArrayList<>();
        maListe.add("");
        maListe.add("Banane");
        maListe.add("Cerise");
        maListe.add("Orange");
        Commande.changerModeReception();
    }
}