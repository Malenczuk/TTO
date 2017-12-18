package tto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

class PrinterTest {

    @Test
    void print() throws IOException, ArgumentException, ParseException {
        String[] test1 = {"-F", "src" + File.separator + "test" + File.separator + "java" + File.separator + "tto" + File.separator + "test.txt", "-T"};
        Options options = new Options(test1);
        Parser parser = new Parser(options.optionSet.valueOf("File").toString());
        ObjectText node = parser.parse(Sections.File, parser.prepareFile());
        Printer printer = new Printer(options);
        ArrayList<String> toPrint = printer.print(node);
        String[] toTest1 = {"USTAWA", "DZIAŁ I", "Przepisy ogólne", "Rozdział 1", "Zakaz nadużywania pozycji dominującej"};
        Assert.assertArrayEquals(toTest1, toPrint.toArray());

        String[] test2 = {"-F", "src" + File.separator + "test" + File.separator + "java" + File.separator + "tto" + File.separator + "test.txt", "-T", "-S", "I"};
        options = new Options(test2);
        printer = new Printer(options);
        toPrint = printer.print(node);
        String[] toTest2 = {"DZIAŁ I", "Przepisy ogólne", "Rozdział 1", "Zakaz nadużywania pozycji dominującej"};
        Assert.assertArrayEquals(toTest2, toPrint.toArray());

        String[] test3 = {"-F", "src" + File.separator + "test" + File.separator + "java" + File.separator + "tto" + File.separator + "test.txt", "-T", "-S", "II"};
        options = new Options(test3);
        printer = new Printer(options);
        toPrint = printer.print(node);
        String[] toTest3 = {"Section with index II not found"};
        Assert.assertArrayEquals(toTest3, toPrint.toArray());

        String[] test4 = {"-F", "src" + File.separator + "test" + File.separator + "java" + File.separator + "tto" + File.separator + "test.txt", "-A", "1"};
        options = new Options(test4);
        printer = new Printer(options);
        toPrint = printer.print(node);
        String[] toTest4 = {"Art. 1.", "1. Ustawa określa warunki rozwoju i ochrony", "konkurencji oraz zasady", "1) umów, w szczególności licencji, a także innych niż umowy praktyk", "a) informacji technicznych lub technologicznych,"};
        Assert.assertArrayEquals(toTest4, toPrint.toArray());

        String[] test5 = {"-F", "src" + File.separator + "test" + File.separator + "java" + File.separator + "tto" + File.separator + "test.txt", "-N" , "-A", "1", "-P", "1"};
        options = new Options(test5);
        printer = new Printer(options);
        toPrint = printer.print(node);
        String[] toTest5 = {"1. Ustawa określa warunki rozwoju i ochrony", "konkurencji oraz zasady"};
        Assert.assertArrayEquals(toTest5, toPrint.toArray());

        String[] test6 = {"-F", "src" + File.separator + "test" + File.separator + "java" + File.separator + "tto" + File.separator + "test.txt", "-A", "1", "-P", "1", "-s", "1", "-c", "a-a"};
        options = new Options(test6);
        printer = new Printer(options);
        toPrint = printer.print(node);
        String[] toTest6 = {"a) informacji technicznych lub technologicznych,"};
        Assert.assertArrayEquals(toTest6, toPrint.toArray());

        String[] test7 = {"-F", "src" + File.separator + "test" + File.separator + "java" + File.separator + "tto" + File.separator + "test.txt", "-A", "1", "-P", "1", "-s", "1", "-c", "b"};
        options = new Options(test7);
        printer = new Printer(options);
        toPrint = printer.print(node);
        String[] toTest7 = {"Character with index b not found in SubPoint 1 in Point 1 in Article 1"};
        Assert.assertArrayEquals(toTest7, toPrint.toArray());
    }
}