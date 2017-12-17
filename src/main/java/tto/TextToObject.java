package tto;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.util.ArrayList;

public class TextToObject {
    public static void main(String[] args) {
        boolean validArgs = true;
        if (args != null && args.length != 0) {
            validArgs = args[0] != null;
            if (args.length != 1)
                validArgs = args[1] != null;
            else
                validArgs = false;
        } else validArgs = false;

        if (validArgs) {
            Parser parser = new Parser(args[0]);
            ArrayList<String> file = parser.prepareFile();
            ObjectText OT = parser.parse(Sections.File, file);
            Printer printer = new Printer();
            for(String x : printer.print(OT, args)){
                System.out.println(x);
            }
        }else System.out.println("Not enough arguments");
    }
}