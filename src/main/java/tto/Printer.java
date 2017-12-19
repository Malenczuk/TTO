package tto;

import com.frequal.romannumerals.Converter;
import java.text.ParseException;
import java.util.ArrayList;

public class Printer {
    private ArrayList<String> toPrint = new ArrayList<>();
    private Options options;
    private Converter converter = new Converter();

    public Printer(Options options) {
        this.options = options;
    }

    public ArrayList<String> print(ObjectText objectText) throws ParseException{
        if (options.optionSet.has("Table")) {
            contentPage(objectText);
        } else {
            rangeOfSections(objectText);
        }
        return toPrint;
    }

    private void contentPage(ObjectText node) throws ParseException{
        Sections section;
        if (options.optionSet.has("Section")) {
            node = node.findObject(options.optionSet.valueOf("Section").toString(), section = Sections.Section);
            if (node != null)
                this.printContentsPage(node);
            else
                notFound(section, options.optionSet.valueOf(section.toString()).toString());
        } else {
            printContentsPage(node);
        }
    }

    private void rangeOfSections(ObjectText node) throws ParseException{
        Sections section = node.section;
        String[] range = null;
        Sections rangeSection = null;
        while ((section = section.next()) != null) {
            if (node != null && options.optionSet.has(section.toString())) {
                if (options.optionSet.valueOf(section.toString()).toString().matches("(\\d*\\D*)+[-](\\d*\\D*)+")) {
                    range = options.optionSet.valueOf(section.toString()).toString().split("[-]", 2);
                    if (node.findObject(range[0], section) == null) notFound(section, range[0]);
                    if (node.findObject(range[1], section) == null) notFound(section, range[1]);
                    rangeSection = section;
                } else {
                    node = node.findObject(options.optionSet.valueOf(section.toString()).toString(), section);
                    if (node == null)
                        notFound(section, options.optionSet.valueOf(section.toString()).toString());
                }
            }
        }

        if (node != null && toPrint.isEmpty())
            if (range != null) this.printRangeOfSection(node, range[0], range[1], rangeSection, false);
            else printSection(node);
    }

    private void notFound(Sections section, String index) {
        String message = section.toString() + " with index " + index + " not found";
        while ((section = section.prev()) != Sections.File && section != null) {
            if (options.optionSet.has(section.toString()))
                message = message.concat(" in " + section.toString() + " " + options.optionSet.valueOf(section.toString()));
        }

        toPrint.add(message);
    }


    private void printSection(ObjectText node) {
        int i = 0;
        if (node.section.checkIfNewLine()) {
            if (!node.text.isEmpty()) toPrint.add((node.section.sectionIndex(node.index) + " " + node.text.get(i++)));
            else toPrint.add((node.section.sectionIndex(node.index)));
        } else toPrint.add(node.section.sectionIndex(node.index));
        if (node.title != null) toPrint.add(node.title);
        while (i < node.text.size()) {
            toPrint.add(node.text.get(i++));
        }

        if (!options.optionSet.has("NoSubSections")) {
            printSubSections(node);
        }
    }

    private void printSubSections(ObjectText node) {
        for (ObjectText x : node.subSections) {
            printSection(x);
        }
    }

    private boolean printRangeOfSection(ObjectText node, String from, String to, Sections section, boolean inRange) throws ParseException{
        if (node.section.equals(section)) {
            if (node.indexEquals(from))
                inRange = true;
            if (inRange)
                printSection(node);
            if (node.indexEquals(to))
                inRange = false;
            return inRange;
        }
        if (node.section.ordinal() < section.ordinal()) {
            for (ObjectText subNode : node.subSections) {
                inRange = printRangeOfSection(subNode, from, to, section, inRange);
            }
        }
        return inRange;
    }

    private void printContentsPage(ObjectText node) {
        if (node.section.ordinal() < 4) {
            toPrint.add(node.section.sectionIndex(node.index));
            if (node.title != null) toPrint.add(node.title);
            for (ObjectText subNode : node.subSections) {
                printContentsPage(subNode);
            }
        }
    }
}
