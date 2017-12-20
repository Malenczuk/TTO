package tto;

import joptsimple.OptionException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class TextToObject {
    public ArrayList<String> run(String[] args) {
        try {
            Options options = new Options(args);
            if (options.optionSet.has("File")) {
                Parser parser = new Parser(options.optionSet.valueOf("File").toString());
                ArrayList<String> file = parser.prepareFile();
                ObjectText OT = parser.parse(Sections.File, file);
                if (OT != null) {
                    Printer printer = new Printer(options);
                    return printer.print(OT);
                } else {
                    System.out.println("Empty file");
                    System.exit(0);
                }

            }
        } catch (IOException | OptionException | ArgumentException | ParseException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return null;
    }
}