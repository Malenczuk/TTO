package tto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SectionsTest {

    @Test
    void sectionIndex() {
        Assert.assertEquals("Test", Sections.File.sectionIndex("Test"));
        Assert.assertEquals("DZIAŁ 1", Sections.Section.sectionIndex("1"));
        Assert.assertEquals("Rozdział 1", Sections.Chapter.sectionIndex("1"));
        Assert.assertEquals("TEST", Sections.SubChapter.sectionIndex("TEST"));
        Assert.assertEquals("Art. 1.", Sections.Article.sectionIndex("1"));
        Assert.assertEquals("1.", Sections.Point.sectionIndex("1"));
        Assert.assertEquals("1)", Sections.SubPoint.sectionIndex("1"));
        Assert.assertEquals("a)", Sections.Character.sectionIndex("a"));
    }

    @Test
    void toRegex() {
        Assert.assertEquals("", Sections.File.toRegex());
        Assert.assertEquals("^DZIAŁ [IVXLCDM]+", Sections.Section.toRegex());
        Assert.assertEquals("^Rozdział (\\S+)", Sections.Chapter.toRegex());
        Assert.assertEquals("^([A-ZŁĄĆŚÓĘŃŻŹ,]+)( ([A-ZŁĄĆŚÓĘŃŻŹ,]+))*", Sections.SubChapter.toRegex());
        Assert.assertEquals("^Art[.] (\\d+)(\\w*)([–]+(\\d+)(\\w*))*[.]", Sections.Article.toRegex());
        Assert.assertEquals("^(\\d+)(\\w*)[.]", Sections.Point.toRegex());
        Assert.assertEquals("^(\\d+)(\\w*)[)]", Sections.SubPoint.toRegex());
        Assert.assertEquals("^(\\w+)(\\d*)[)]", Sections.Character.toRegex());
    }

    @Test
    void checkIfTitle() {
        Assert.assertFalse(Sections.File.checkIfTitle());
        Assert.assertTrue(Sections.Section.checkIfTitle());
        Assert.assertTrue(Sections.Chapter.checkIfTitle());
        Assert.assertFalse(Sections.SubChapter.checkIfTitle());
        Assert.assertFalse(Sections.Article.checkIfTitle());
        Assert.assertFalse(Sections.Point.checkIfTitle());
        Assert.assertFalse(Sections.SubPoint.checkIfTitle());
        Assert.assertFalse(Sections.Character.checkIfTitle());
    }

    @Test
    void checkIfNewLine() {
        Assert.assertFalse(Sections.File.checkIfNewLine());
        Assert.assertFalse(Sections.Section.checkIfNewLine());
        Assert.assertFalse(Sections.Chapter.checkIfNewLine());
        Assert.assertFalse(Sections.SubChapter.checkIfNewLine());
        Assert.assertFalse(Sections.Article.checkIfNewLine());
        Assert.assertTrue(Sections.Point.checkIfNewLine());
        Assert.assertTrue(Sections.SubPoint.checkIfNewLine());
        Assert.assertTrue(Sections.Character.checkIfNewLine());
    }

    @Test
    void next() {
        Assert.assertEquals(Sections.Section, Sections.File.next());
        Assert.assertEquals(Sections.Chapter, Sections.Section.next());
        Assert.assertEquals(Sections.SubChapter, Sections.Chapter.next());
        Assert.assertEquals(Sections.Article, Sections.SubChapter.next());
        Assert.assertEquals(Sections.Point, Sections.Article.next());
        Assert.assertEquals(Sections.SubPoint, Sections.Point.next());
        Assert.assertEquals(Sections.Character, Sections.SubPoint.next());
        Assert.assertEquals(null, Sections.Character.next());
    }

    @Test
    void prev() {
        Assert.assertEquals(null, Sections.File.prev());
        Assert.assertEquals(Sections.File, Sections.Section.prev());
        Assert.assertEquals(Sections.Section, Sections.Chapter.prev());
        Assert.assertEquals(Sections.Chapter, Sections.SubChapter.prev());
        Assert.assertEquals(Sections.SubChapter, Sections.Article.prev());
        Assert.assertEquals(Sections.Article, Sections.Point.prev());
        Assert.assertEquals(Sections.Point, Sections.SubPoint.prev());
        Assert.assertEquals(Sections.SubPoint, Sections.Character.prev());
    }
}