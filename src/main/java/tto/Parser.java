package tto;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private ArrayList<String> file = new ArrayList<>();
    private String filePath;

    public Parser(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<String> prepareFile(){
        BufferedReader reader = null;
        String line = null;
        try {
            reader = new BufferedReader(new FileReader(this.filePath));
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("©"))
                    reader.readLine();
                else if (!(line.length() < 2) && !line.matches("^Dz[.]U[.].*")) {
                    line = sectionToNewLine(line);
                    line = wordJoin(reader, line);
                    this.file.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found");
            return null;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return file;
    }

    private String sectionToNewLine(String line) {
        Sections section = Sections.Article;
        Matcher matcher;
        while (section != null) {
            if (line.matches(section.toRegex() + "(\\s+).+")) {
                matcher = Pattern.compile(section.toRegex()).matcher(line);
                if (matcher.find()) {
                    this.file.add(matcher.group());
                    line = (line.replaceFirst(section.toRegex() + "(\\s*)", ""));
                }
            }
            section = section.next();
        }
        return line;
    }

    private String wordJoin(BufferedReader reader, String line) throws IOException {
        while (line.endsWith("-")) {
            String[] x = reader.readLine().split(" ", 2);
            line = line.substring(0, line.length() - 1) + x[0];
            if (x.length == 2) {
                this.file.add(line);
                line = x[1];
            }
        }
        return line;
    }

    public ObjectText parse(Sections nSection, ArrayList<String> subText) {
        if (subText == null || subText.isEmpty()) return null;
        int i = 0;
        String index = subText.get(i++);
        String title = null;
        ArrayList<String> text = new ArrayList<>();

        if (nSection.checkIfTitle())
            title = (subText.get(i++));

        Sections section;
        boolean endOfText = false;
        while (checkList(i, subText) && !endOfText) {
            section = nSection;
            while ((section = section.next()) != null)
                if (subText.get(i).matches(section.toRegex()))
                    endOfText = true;
            if (!endOfText) {
                text.add(subText.get(i++));
            }
        }

        ArrayList<ObjectText> subSections = createSubSections(subText, i);

        return new ObjectText(nSection, index, title, text, subSections);
    }

    private ArrayList<ObjectText> createSubSections(ArrayList<String> subText, int i) {
        ArrayList<ObjectText> subSections = new ArrayList<>();
        Sections section = Sections.Section;
        while (section != null && subSections.isEmpty()) {
            while (checkList(i, subText) && subText.get(i).matches(section.toRegex())) {
                ArrayList<String> filex = new ArrayList<>();
                filex.add(subText.get(i++));
                while (checkList(i, subText) && !subText.get(i).matches(section.toRegex())) {
                    filex.add(subText.get(i++));
                }
                subSections.add(this.parse(section, filex));
            }
            section = section.next();
        }
        return subSections;
    }

    private boolean checkList(int i, ArrayList text) {
        return i < text.size() && text.get(i) != null;
    }

}
