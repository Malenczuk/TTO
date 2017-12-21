package tto;

import com.frequal.romannumerals.Converter;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Options {
    public final OptionSet optionSet;
    public final OptionParser optionParser;

    public Options(String[] args) throws IOException, ArgumentException, OptionException, ParseException {
        optionParser = new OptionParser();
        final String[] fileOptions = {"F", "File"};
        optionParser.acceptsAll(Arrays.asList(fileOptions), "Path and name of file (required)").withRequiredArg().required();
        final String[] tableOptions = {"T", "Table"};
        optionParser.acceptsAll(Arrays.asList(tableOptions), "Print table of contents.");
        final String[] subOptions = {"N", "NoSubSections"};
        optionParser.acceptsAll(Arrays.asList(subOptions), "Print without subsections");
        final String[] characterOptions = {"c", "Character"};
        optionParser.acceptsAll(Arrays.asList(characterOptions), "Index or range of Characters to print (requires Article)").withRequiredArg();
        final String[] subPointOptions = {"s", "SubPoint"};
        optionParser.acceptsAll(Arrays.asList(subPointOptions), "Index or range of SubPoints to print (requires Article)").withRequiredArg();
        final String[] pointOptions = {"p", "Point"};
        optionParser.acceptsAll(Arrays.asList(pointOptions), "Index or range of Points to print (requires Article)").withRequiredArg();
        final String[] articleOptions = {"A", "Article"};
        optionParser.acceptsAll(Arrays.asList(articleOptions), "Index or range of Articles to print").requiredIf("Point", "SubPoint", "Character").withRequiredArg();
        final String[] chapterOptions = {"C", "Chapter"};
        optionParser.acceptsAll(Arrays.asList(chapterOptions), "Index or range of Chapters to print").withRequiredArg();
        final String[] sectionOptions = {"S", "Section"};
        optionParser.acceptsAll(Arrays.asList(sectionOptions), "Index or range Sections to print (works with table of contents)").withRequiredArg();
        final String[] helpOptions = {"h", "help"};
        optionParser.acceptsAll(Arrays.asList(helpOptions), "Display help/usage information").forHelp();
        optionParser.allowsUnrecognizedOptions();
        optionSet = optionParser.parse(args);

        this.checkOptions();
    }


    private void checkOptions() throws IOException, ArgumentException, ParseException {
        if (optionSet.nonOptionArguments().size() > 0) {
            optionParser.printHelpOn(System.out);
            String message = "Wrong arguments: ";
            for (int i = 0; i < optionSet.nonOptionArguments().size(); i++) {
                message = message.concat("\"" + optionSet.nonOptionArguments().get(i) + "\", ");
            }
            throw new ArgumentException(message);
        }
        if (optionSet.has("help")) optionParser.printHelpOn(System.out);

        checkRanges();
    }

    private void checkRanges() throws ParseException, ArgumentException {
        Sections section = Sections.File;
        boolean hasRange = false;
        boolean wrongRange = false;


        while ((section = section.next()) != null) {
            if (!optionSet.hasArgument(section.toString())) continue;

            if (hasRange) throw new ArgumentException("Only the last section's argument can be a range");
            String arg = optionSet.valueOf(section.toString()).toString();
            if (arg.matches(".*[-].*")) {
                String[] ranges = optionSet.valueOf(section.toString()).toString().split("[-]");

                if (arg.matches("^([MCLXVI])+\\D*[-]([MCLXVI])+\\D*$"))
                    wrongRange = isWrongRange(rangesToNumeric(ranges));

                else if (arg.matches("^(\\d)+(\\D)*[-](\\d)+(\\D)*$") || arg.matches("^(\\D)+[-](\\D)+$")) {
                    wrongRange = isWrongRange(ranges);
                } else throw new ArgumentException(arg + " wrong range");

                if (wrongRange) throw new ArgumentException(arg + " seconds index must be equal or greater than first");
                hasRange = true;
            }


        }
    }

    private boolean isWrongRange(String[] ranges) {
        boolean wrongRange;
        int[] numeral = {0, 0};

        if (ranges[0].matches("^(\\d)+.*"))
            numeral[0] = Integer.parseInt(ranges[0].replaceAll("(\\D)", ""));

        if (ranges[1].matches("^(\\d)+.*"))
            numeral[1] = Integer.parseInt(ranges[1].replaceAll("(\\D)", ""));

        char[][] characters = {ranges[0].replaceAll("(\\d)", "").toCharArray(),
                ranges[1].replaceAll("(\\d)", "").toCharArray()
        };
        wrongRange = numeral[0] > numeral[1];
        if (numeral[0] == numeral[1]) {
            if (characters[0].length != 0 && characters[1].length == 0) return true;
            boolean equal = true;
            for (int i = 0; i < characters[0].length && i < characters[1].length && equal; i++) {
                equal = ((int) Character.toLowerCase(characters[0][i])) == ((int) Character.toLowerCase(characters[1][i]));
                if ((int) Character.toLowerCase(characters[0][i]) < (int) Character.toLowerCase(characters[1][i]))
                    return false;
            }
            wrongRange = !equal || (equal && characters[0].length > characters[1].length);
        }
        return wrongRange;
    }

    private String[] rangesToNumeric(String[] ranges) throws ParseException {
        Converter converter = new Converter();
        Matcher matcher;
        Pattern pattern = Pattern.compile("^[MCLXVI]+");
        for (int i = 0; i < ranges.length; i++) {
            matcher = pattern.matcher(ranges[i]);
            if (matcher.find())
                ranges[i] = (converter.toNumber(matcher.group()) + ranges[i].replaceFirst("^[MCLXVI]+", ""));
        }
        return ranges;
    }

}
