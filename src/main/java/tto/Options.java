package tto;

import com.frequal.romannumerals.Converter;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

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
        optionParser.acceptsAll(Arrays.asList(characterOptions), "Index or range of Character to print (requires Article)").withRequiredArg();
        final String[] subPointOptions = {"s", "SubPoint"};
        optionParser.acceptsAll(Arrays.asList(subPointOptions), "Index or range of SubPoint to print (requires Article)").withRequiredArg();
        final String[] pointOptions = {"P", "Point"};
        optionParser.acceptsAll(Arrays.asList(pointOptions), "Index or range of Point to print (requires Article)").withRequiredArg();
        final String[] articleOptions = {"A", "Article"};
        optionParser.acceptsAll(Arrays.asList(articleOptions), "Index or range of Article to print").requiredIf("Point", "SubPoint", "Character").withRequiredArg();
        final String[] chapterOptions = {"C", "Chapter"};
        optionParser.acceptsAll(Arrays.asList(chapterOptions), "Index or range of Chapter to print").withRequiredArg();
        final String[] sectionOptions = {"S", "Section"};
        optionParser.acceptsAll(Arrays.asList(sectionOptions), "Index or range Section to print (works with table of contents)").withRequiredArg();
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
        Converter converter = new Converter();

        while ((section = section.next()) != null) {
            if (!optionSet.hasArgument(section.toString())) continue;

            String arg = optionSet.valueOf(section.toString()).toString();

            if (arg.matches("^(\\w)+[-](\\w)+$")) {
                String[] ranges = optionSet.valueOf(section.toString()).toString().split("[-]");

                if (arg.matches("^^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})[-]M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$"))
                    wrongRange = (converter.toNumber(ranges[0]) > converter.toNumber(ranges[1]));

                else if (arg.matches("^(\\d)*(\\D)*[-](\\d)*(\\D)*$")) {
                    wrongRange = isWrongRange(ranges);
                }

                if (wrongRange) throw new ArgumentException(arg + " seconds index must be equal or greater than first");

                if (hasRange) throw new ArgumentException("Only the last section's argument can be a range");
                hasRange = true;
            }


        }
    }

    private boolean isWrongRange(String[] ranges) {
        boolean wrongRange;
        int[] numeral = {0, 0};
        if (ranges[0].matches("(\\d)+"))
            numeral[0] = Integer.parseInt(ranges[0].replaceAll("(\\D)", ""));
        if (ranges[1].matches("(\\d)+"))
            numeral[1] = Integer.parseInt(ranges[1].replaceAll("(\\D)", ""));
        char[][] characters = {ranges[0].replaceAll("(\\d)", "").toCharArray(),
                ranges[1].replaceAll("(\\d)", "").toCharArray()
        };
        wrongRange = numeral[0] > numeral[1];
        if (numeral[0] == numeral[1]) {
            if (characters[0].length > characters[1].length) wrongRange = true;
            else if (characters[0].length == characters[1].length) {
                for (int i = 0; i < characters[0].length && !wrongRange; i++) {
                    wrongRange = ((int) characters[0][i]) > ((int) characters[1][i]);
                }
            }
        }
        return wrongRange;
    }

}
