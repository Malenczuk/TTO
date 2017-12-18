package tto;

import java.util.regex.Pattern;

public enum Sections {
    File,
    Section,
    Chapter,
    SubChapter,
    Article,
    Point,
    SubPoint,
    Character;

    public String sectionIndex(String index) {
        switch (this) {
            case File:
                return index;
            case Section:
                return "DZIAŁ " + index;
            case Chapter:
                return "Rozdział " + index;
            case SubChapter:
                return index;
            case Article:
                return "Art. " + index + ".";
            case Point:
                return index + ".";
            case SubPoint:
                return index + ")";
            case Character:
                return index + ")";
            default:
                return "";
        }
    }

    public String toRegex() {
        switch (this) {
            case Section:
                return "^DZIAŁ [IVXLCDM]+";
            case Chapter:
                return "^Rozdział (\\S+)";
            case SubChapter:
                return "^([A-ZŁĄĆŚÓĘŃŻŹ,]+)( ([A-ZŁĄĆŚÓĘŃŻŹ,]+))*";
            case Article:
                return "^Art[.] (\\d+)(\\w*)([–]+(\\d+)(\\w*))*[.]";
            case Point:
                return "^(\\d+)(\\w*)[.]";
            case SubPoint:
                return "^(\\d+)(\\w*)[)]";
            case Character:
                return "^(\\w+)(\\d*)[)]";
            default:
                return "";
        }
    }

    public Pattern toPattern() {
        switch (this) {
            case Section:
                return Pattern.compile(this.toRegex());
            case Chapter:
                return Pattern.compile(this.toRegex());
            case SubChapter:
                return Pattern.compile(this.toRegex());
            case Article:
                return Pattern.compile(this.toRegex());
            case Point:
                return Pattern.compile(this.toRegex());
            case SubPoint:
                return Pattern.compile(this.toRegex());
            case Character:
                return Pattern.compile(this.toRegex());
            default:
                return Pattern.compile("");
        }
    }
    public boolean checkIfTitle() {
        return (this == Section || this == Chapter);
    }

    public boolean checkIfNewLine() {
        return (this == Point || this == SubPoint || this == Character);
    }

    public Sections next() {
        switch (this) {
            case File:
                return Section;
            case Section:
                return Chapter;
            case Chapter:
                return SubChapter;
            case SubChapter:
                return Article;
            case Article:
                return Point;
            case Point:
                return SubPoint;
            case SubPoint:
                return Character;
            default:
                return null;
        }
    }

    public Sections prev() {
        switch (this) {
            case File:
                return null;
            case Section:
                return File;
            case Chapter:
                return Section;
            case SubChapter:
                return Chapter;
            case Article:
                return SubChapter;
            case Point:
                return Article;
            case SubPoint:
                return Point;
            case Character:
                return SubPoint;
            default:
                return null;
        }
    }
}
