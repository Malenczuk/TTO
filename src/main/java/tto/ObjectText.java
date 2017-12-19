package tto;

import com.frequal.romannumerals.Converter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectText {
    public final Sections section;
    public final String index;
    public final String title;
    public final ArrayList<String> text;
    public final ArrayList<ObjectText> subSections;

    public ObjectText(Sections section, String index, String title, ArrayList<String> text, ArrayList<ObjectText> subSections) {
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

    public ObjectText findObject(String index, Sections section) throws ParseException {
        if (this.section == section && (this.index.equalsIgnoreCase(index) || this.checkRoman(index))) return this;
        if (this.section.ordinal() < this.searchDepth(section)) {
            ObjectText found = null;
            for (ObjectText subNode : this.subSections) {
                if ((found = subNode.findObject(index, section)) != null) return found;
            }
        }
        return null;
    }

    private boolean checkRoman(String ind) throws ParseException {
        Converter converter = new Converter();
        Matcher matcher;
        Pattern pattern = Pattern.compile("^[MCLXVI]+");
        if (ind.matches("^[MCLXVI]+\\D*$")) {
            matcher = pattern.matcher(ind);
            if (matcher.find())
                return this.index.equalsIgnoreCase(Integer.toString(converter.toNumber(matcher.group())) + ind.replaceFirst("^[MCLXVI]+", ""));
        } else if (this.index.matches("^[MCLXVI]+\\D*$")) {
            matcher = pattern.matcher(this.index);
            if (matcher.find())
                return ind.equalsIgnoreCase(Integer.toString(converter.toNumber(matcher.group())) + this.index.replaceFirst("^[MCLXVI]+", ""));
        }
        return false;
    }

    private int searchDepth(Sections section) {
        if (this.section.ordinal() >= Sections.Point.ordinal()) return this.section.ordinal() + 1;
        return section.ordinal();
    }

    public boolean indexEquals(String ind) throws ParseException {
        return (this.index.equalsIgnoreCase(ind) || this.checkRoman(ind));
    }
}
