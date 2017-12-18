package tto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


class OptionsTest {

    @Test
    public void testCreate() throws IOException, ArgumentException{
        String[] test = {"-F","src" + File.separator + "test" + File.separator + "java" + File.separator + "tto" + File.separator + "test.txt"};
        Options options = new Options(test);
        Assert.assertTrue(options.optionSet.has("File"));
        Assert.assertTrue(options.optionSet.hasArgument("File"));
    }
}