package tto;

import java.io.IOException;
import java.util.ArrayList;

public class TextToObject {
    public static void main(String[] args) {
        try {
            Options options = new Options(args);
            if (options.optionSet.has("file")) {
                Parser parser = new Parser(options.optionSet.valueOf("file").toString());
                ArrayList<String> file = parser.prepareFile();
                ObjectText OT = parser.parse(Sections.File, file);
                Printer printer = new Printer(options);
                for (String x : printer.print(OT)) {
                    System.out.println(x);
                }
            }
        } catch (IOException e){
            System.err.println(e);
        }
    }
}