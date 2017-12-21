package tto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static java.util.Arrays.asList;


class ParserTest {

    @Test
    void prepareFile() {
        Parser parser = new Parser("src" + File.separator + "test" + File.separator + "resources" + File.separator + "tto" + File.separator + "test.txt");
        ArrayList<String> file = parser.prepareFile();
        String[] test = {"USTAWA", "z dnia 16 lutego 2007 r.", "o ochronie konkurencji i konsumentów", "DZIAŁ I",
                "Przepisy ogólne", "Rozdział 1", "Zakaz nadużywania pozycji dominującej", "Art. 1.", "1.",
                "Ustawa określa warunki rozwoju i ochrony", "konkurencji oraz zasady", "1)",
                "umów, w szczególności licencji, a także innych niż umowy praktyk", "a)",
                "informacji technicznych lub technologicznych,"
        };
        Assert.assertArrayEquals(test, file.toArray());
    }

    @Test
    void parse() {
        Parser parser = new Parser("src" + File.separator + "test" + File.separator + "resources" + File.separator + "tto" + File.separator + "test.txt");
        String[] test = {"USTAWA", "z dnia 16 lutego 2007 r.", "o ochronie konkurencji i konsumentów", "DZIAŁ I",
                "Przepisy ogólne", "Rozdział 1", "Zakaz nadużywania pozycji dominującej", "Art. 1.", "1.",
                "Ustawa określa warunki rozwoju i ochrony", "konkurencji oraz zasady", "1)",
                "umów, w szczególności licencji, a także innych niż umowy praktyk", "a)",
                "informacji technicznych lub technologicznych,"
        };
        ArrayList<String> file = new ArrayList<>(asList(test));
        ObjectText node = parser.parse(Sections.File, file);
        Assert.assertEquals(test[0], node.section.sectionIndex(node.index));
        Assert.assertEquals(test[1],node.text.get(0));
        Assert.assertEquals(test[2],node.text.get(1));
        node = node.subSections.get(0);
        Assert.assertEquals(test[3], node.section.sectionIndex(node.index));
        Assert.assertEquals(test[4],node.title);
        node = node.subSections.get(0);
        Assert.assertEquals(test[5], node.section.sectionIndex(node.index));
        Assert.assertEquals(test[6],node.title);
        node = node.subSections.get(0);
        Assert.assertEquals(test[7], node.section.sectionIndex(node.index));
        node = node.subSections.get(0);
        Assert.assertEquals(test[8], node.section.sectionIndex(node.index));
        Assert.assertEquals(test[9],node.text.get(0));
        Assert.assertEquals(test[10],node.text.get(1));
        node = node.subSections.get(0);
        Assert.assertEquals(test[11], node.section.sectionIndex(node.index));
        Assert.assertEquals(test[12],node.text.get(0));
        node = node.subSections.get(0);
        Assert.assertEquals(test[13], node.section.sectionIndex(node.index));
        Assert.assertEquals(test[14],node.text.get(0));

    }
}