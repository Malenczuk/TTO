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
        optionParser.acceptsAll(Arrays.asList(subPointOptions), "Index or range SubPoint to print (requires Article)").withRequiredArg();
        final String[] pointOptions = {"P", "Point"};
        optionParser.acceptsAll(Arrays.asList(pointOptions), "Index or range Point to print (requires Article)").withRequiredArg();
        final String[] articleOptions = {"A", "Article"};
        optionParser.acceptsAll(Arrays.asList(articleOptions), "Index or range Article to print").requiredIf("Point", "SubPoint", "Character").withRequiredArg();
        final String[] chapterOptions = {"C", "Chapter"};
        optionParser.acceptsAll(Arrays.asList(chapterOptions), "Index or range Chapter to print").withRequiredArg();
        final String[] sectionOptions = {"S", "Section"};
        optionParser.acceptsAll(Arrays.asList(sectionOptions), "Index or range Section to print").withRequiredArg();
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

        Sections section = Sections.File;
        boolean hasRange = false;
        while ((section = section.next()) != null) {
            if (optionSet.hasArgument(section.toString()) && optionSet.valueOf(section.toString()).toString().matches("(\\D*\\d*)+[-](\\D*\\d*)+")) {
                String[] ranges = optionSet.valueOf(section.toString()).toString().split("[-]");
                if (optionSet.valueOf(section.toString()).toString().matches("^^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})[-]M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {
                    Converter converter = new Converter();
                    if (converter.toNumber(ranges[0]) > converter.toNumber(ranges[1]))
                        throw new ArgumentException(optionSet.valueOf(section.toString()) + " seconds index must be equal or greater than first");
                }
                if (optionSet.valueOf(section.toString()).toString().matches("^(\\d)+[-](\\d)+$"))
                    if (Integer.parseInt(ranges[0]) > Integer.parseInt(ranges[1]))
                        throw new ArgumentException(optionSet.valueOf(section.toString()) + " seconds index must be equal or greater than first");

                if (hasRange) throw new ArgumentException("Only the last section's argument can be a range");
                hasRange = true;
            }


        }
    }

}
