package tto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class ObjectTextTest{

        @Test
        public void testCreate(){
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

}