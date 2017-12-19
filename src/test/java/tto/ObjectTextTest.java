package tto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;


class ObjectTextTest {

    @Test
    public void testCreate() {
        ArrayList<String> text = new ArrayList<>();
        ArrayList<ObjectText> subSections = new ArrayList<>();
        ObjectText node = new ObjectText(Sections.Article, "Art. 1.", null, text, subSections);
        Assert.assertEquals("1", "1");
        node = new ObjectText(Sections.Article, "Rozdział 1", null, text, subSections);
        Assert.assertEquals("1", "1");
        node = new ObjectText(Sections.SubPoint, "1)", null, text, subSections);
        Assert.assertEquals("1", "1");
        node = new ObjectText(Sections.File, "1", null, text, subSections);
        Assert.assertEquals("1", "1");
    }


    @Test
    void findObject() throws ParseException {
        ArrayList<ObjectText> subSections = new ArrayList<>();
        ObjectText subNode = new ObjectText(Sections.Point, "1)", null, null, null);
        subSections.add(subNode);
        ObjectText node = new ObjectText(Sections.Article, "Art. 1.", null, null, subSections);
        Assert.assertEquals(node, node.findObject("1", Sections.Article));
        Assert.assertEquals(node, node.findObject("I", Sections.Article));
        Assert.assertEquals(subNode, node.findObject("1", Sections.Point));
    }

    @Test
    void indexEquals() throws ParseException {
        ObjectText node = new ObjectText(Sections.Article, "Art. 1.", null, null, null);
        Assert.assertTrue(node.indexEquals("1"));
        Assert.assertTrue(node.indexEquals("I"));
    }
}