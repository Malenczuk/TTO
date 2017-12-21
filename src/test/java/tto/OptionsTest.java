package tto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;


class OptionsTest {

    @Test
    public void testCreate() throws IOException, ArgumentException, ParseException{
        String[] test = {"-F", "src" + File.separator + "test" + File.separator + "resources" + File.separator + "tto" + File.separator + "tto/test.txt", "-A", "1-1"};
        Options options = new Options(test);
        Assert.assertTrue(options.optionSet.has("File"));
        Assert.assertTrue(options.optionSet.hasArgument("File"));
    }
}