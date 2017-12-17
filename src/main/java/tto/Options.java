package tto;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.util.Arrays;

public class Options {
    public final OptionSet optionSet;

    public Options(String[] args) throws IOException {
        OptionParser optionParser = new OptionParser();
        final String[] fileOptions = {"f", "file"};
        optionParser.acceptsAll(Arrays.asList(fileOptions), "Path and name of file.").withRequiredArg().required();
        final String[] tableOptions = {"t", "table"};
        optionParser.acceptsAll(Arrays.asList(tableOptions), "Print table of contents.");
        final String[] characterOptions = {"l", "character"};
        optionParser.acceptsAll(Arrays.asList(characterOptions), "Index of character to print (requires subpoint)").withRequiredArg();
        final String[] subpointOptions = {"q", "subpoint"};
        optionParser.acceptsAll(Arrays.asList(subpointOptions), "Index of subpoint to print (requires point)").requiredIf("character").withRequiredArg();
        final String[] pointOptions = {"p", "point"};
        optionParser.acceptsAll(Arrays.asList(pointOptions), "Index of point to print (requires article)").requiredIf("subpoint").withRequiredArg();
        final String[] articleOptions = {"a", "article"};
        optionParser.acceptsAll(Arrays.asList(articleOptions), "Index of article to print").requiredIf("point").withRequiredArg();
        final String[] chapterOptions = {"c", "chapter"};
        optionParser.acceptsAll(Arrays.asList(chapterOptions), "Index of chapter to print").withRequiredArg();
        final String[] sectionOptions = {"s", "section"};
        optionParser.acceptsAll(Arrays.asList(sectionOptions), "Index of section to print").withRequiredArg();
        final String[] helpOptions = {"h", "help"};
        optionParser.acceptsAll(Arrays.asList(helpOptions), "Display help/usage information").forHelp();
        optionSet = optionParser.parse(args);
        if (optionSet.has("help")) optionParser.printHelpOn(System.out);
    }

}
