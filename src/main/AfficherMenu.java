package main;

import java.util.List;
import java.math.RoundingMode;
public class AfficherMenu {
    public static String Menu(String titre, List<String> options)
    {
        String str = "╭────────────────────────────╮ \n"
        + "│" + " ".repeat(14 - ((int) (titre.length()/2))) + titre + " ".repeat(14 - ((int) Math.round(titre.length()/2.0))) + "│\n"
        + "├────────────────────────────┤\n";
        int i = 1;
        for (String opt : options)
        {
            String istr = ""  +i;
            str = str + "│ " + i + ". " + opt + " ".repeat(25 - (istr.length() + opt.length())) + "│\n";
            i++;
        }
        str  = str + "╰────────────────────────────╯\n";
        return str;
    }
}
