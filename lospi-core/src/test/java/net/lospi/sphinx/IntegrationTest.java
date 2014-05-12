package net.lospi.sphinx;

import com.thoughtworks.qdox.model.JavaClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static net.lospi.sphinx.Utils.fromFile;
import static org.fest.assertions.api.Assertions.assertThat;

public class IntegrationTest {
    private String source;

    @BeforeMethod
    public void setUp() throws Exception {
        source = fromFile("sample-class.txt");
    }

    @Test
    public void parse() throws Exception {
        JavaClassParser parser = new JavaClassParser();
        List<JavaClass> result = parser.generate(source);
        TestClassGenerator generator = new TestClassGenerator();
        String testClass = generator.generate(result.get(0));
        System.out.print(testClass);
        assertThat(testClass).isNotNull();
    }

}