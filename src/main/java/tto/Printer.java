package tto;

import java.util.ArrayList;

public class Printer {
    private ArrayList<String> toPrint = new ArrayList<>();
    private Options options;

    public Printer(Options options) {
        this.options = options;
    }

    public ArrayList<String> print(ObjectText objectText) {
        ObjectText node = objectText;
        Sections section = Sections.File;
        if (options.optionSet.has("table")) {
            if (options.optionSet.has("section")) {
                node = findObject(node, options.optionSet.valueOf("section").toString(), section = Sections.Section);
                if (node != null)
                    this.printContentsPage(node);
                else
                    toPrint.add(section.toString() + " with index " + options.optionSet.valueOf("section") + " not found.");
            } else {
                printContentsPage(node);
            }
        } else {
            if (node != null && options.optionSet.has("section")) {
                node = findObject(node, options.optionSet.valueOf("section").toString(), section = Sections.Section);
                if (node == null) ;
            }
            if (node != null && options.optionSet.has("chapter")) {
                node = findObject(node, options.optionSet.valueOf("chapter").toString(), section = Sections.Chapter);
                if (node == null) ;
            }
            if (node != null && options.optionSet.has("article")) {
                node = findObject(node, options.optionSet.valueOf("article").toString(), section = Sections.Article);
                if (node == null) ;
            }
            if (node != null && options.optionSet.has("point")) {
                node = findObject(node, options.optionSet.valueOf("point").toString(), section = Sections.Point);
                if (node == null) ;
            }
            if (node != null && options.optionSet.has("subpoint")) {
                node = findObject(node, options.optionSet.valueOf("subpoint").toString(), section = Sections.SubPoint);
                if (node == null) ;
            }
            if (node != null && options.optionSet.has("character")) {
                node = findObject(node, options.optionSet.valueOf("character").toString(), section = Sections.Character);
                if (node == null) ;
            }

            if (node != null)
                this.printWithSubSections(node);
        }


        return toPrint;
    }

    private ObjectText findObject(ObjectText node, String index, Sections section) {
        if (node.section == section && node.index.equals(index)) return node;

        ObjectText found = null;
        if (node.section.ordinal() < section.ordinal()) {
            for (ObjectText x : node.subSections) {
                if ((found = findObject(x, index, section)) != null) break;
            }
        }
        return found;
    }

    private void printSection(ObjectText node) {
        int i = 0;
        if (node.section.checkIfNewLine()) {
            toPrint.add((node.section.sectionIndex(node.index) + " " + node.text.get(i++)));
        } else toPrint.add(node.section.sectionIndex(node.index));
        if (node.title != null) toPrint.add(node.title);
        while (i < node.text.size()) {
            toPrint.add(node.text.get(i++));
        }
    }

    private void printWithSubSections(ObjectText node) {
        printSection(node);
        for (ObjectText x : node.subSections) {
            printWithSubSections(x);
        }
    }

    private void printContentsPage(ObjectText node) {
        if (node.section.ordinal() < 4) {
            toPrint.add(node.section.sectionIndex(node.index));
            if (node.title != null) toPrint.add(node.title);
            for (ObjectText x : node.subSections) {
                printContentsPage(x);
            }
        }
    }
}
