package net.lospi.sphinx;

import com.thoughtworks.qdox.model.JavaClass;
import net.lospi.sphinx.JavaClassParser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

import static net.lospi.sphinx.Utils.fromFile;
import static org.fest.assertions.api.Assertions.assertThat;

public class JavaClassParserTest {
    private String source;

    @BeforeMethod
    public void setUp() throws Exception {
        source = fromFile("sample-class.txt");
    }

    @Test
    public void parse() throws Exception {
        JavaClassParser generator = new JavaClassParser();
        List<JavaClass> result = generator.generate(source);
        assertThat(result).isNotNull();
    }
}