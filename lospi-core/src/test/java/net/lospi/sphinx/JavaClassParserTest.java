package net.lospi.sphinx;

import com.thoughtworks.qdox.model.JavaClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static net.lospi.sphinx.Utils.fromResource;
import static org.fest.assertions.api.Assertions.assertThat;

public class JavaClassParserTest {
    private String source;

    @BeforeMethod
    public void setUp() throws Exception {
        source = fromResource("sample-class.txt");
    }

    @Test
    public void parse() throws Exception {
        JavaClassParser generator = new JavaClassParser();
        List<JavaClass> result = generator.generate(source);
        assertThat(result).isNotNull();
    }
}