package tto;

import joptsimple.OptionException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class TextToObject {
    public static void main(String[] args) {
        try {
            Options options = new Options(args);
            if (options.optionSet.has("File")) {
                Parser parser = new Parser(options.optionSet.valueOf("File").toString());
                ArrayList<String> file = parser.prepareFile();
                if (file != null) {
                    ObjectText OT = parser.parse(Sections.File, file);
                    if (OT != null) {
                        Printer printer = new Printer(options);
                        for (String x : printer.print(OT)) {
                            System.out.println(x);
                        }
                    } else System.out.println("Error while parsing");
                }
            }
        } catch (IOException | OptionException | ArgumentException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}