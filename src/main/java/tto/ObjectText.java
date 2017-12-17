package tto;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectText {
    public final Sections section;
    public final String index;
    public final String title;
    public final ArrayList<String> text;
    public final ArrayList<ObjectText> subSections;

    public ObjectText(Sections section, String index, String title, ArrayList<String> text,ArrayList<ObjectText> subSections) {
        this.section = section;
        this.index = createIndex(index);
        this.title = title;
        this.text = text;
        this.subSections = subSections;
    }


    private String createIndex(String x) {
        String index = "";
        if (this.section == Sections.File || this.section == Sections.SubChapter) index = x;
        else if (this.section == Sections.Section || this.section == Sections.Chapter || this.section == Sections.Article) {
            Matcher matcher = Pattern.compile("(\\S+)$").matcher(x);
            if (matcher.find()) {
                index = matcher.group();
                if (this.section == Sections.Article) index = index.substring(0, index.length() - 1);
            }
        } else {
            index = x.substring(0, x.length() - 1);
        }
        return index;
    }


    public void indexPrint() {
        if (!this.section.checkIfNewLine()) {
            System.out.println(this.section.sectionIndex(this.index));
            if (this.title != null) System.out.println(this.title);
        } else System.out.print(this.section.sectionIndex(this.index));

    }

    public void elemPrint() {
        this.indexPrint();
        for (String x : this.text) {
            System.out.println(" " + x);
        }
    }

    public void subPrint() {
        this.elemPrint();
        for (ObjectText x : this.subSections) {
            x.subPrint();
        }
    }
}
