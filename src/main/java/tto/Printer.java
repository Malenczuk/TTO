package tto;

import java.util.ArrayList;

public class Printer {
    private ArrayList<String> toPrint = new ArrayList<>();

    public  ArrayList<String> print(ObjectText objectText, String[] args) {


        if (args[1].equals("0")) printContentsPage(objectText);
        printSection(findObject((findObject(findObject(objectText,args[3],Sections.Article),args[5],Sections.Point)),args[7],Sections.SubPoint));

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
        if(node.section.checkIfNewLine()) {
            toPrint.add((node.section.sectionIndex(node.index) + " " +node.text.get(i++)));
        }else toPrint.add(node.section.sectionIndex(node.index));
        if(node.title != null) toPrint.add(node.title);
        while(i < node.text.size()){
            toPrint.add(node.text.get(i++));
        }
    }

    private void printWithSubSections(ObjectText node){
        printSection(node);
        for(ObjectText x : node.subSections){
            printWithSubSections(x);
        }
    }

    private void printContentsPage(ObjectText node) {
        if (node.section.ordinal() < 4) {
            toPrint.add(node.section.sectionIndex(node.index));
            if(node.title != null) toPrint.add(node.title);
            for (ObjectText x : node.subSections) {
                printContentsPage(x);
            }
        }
    }
}
