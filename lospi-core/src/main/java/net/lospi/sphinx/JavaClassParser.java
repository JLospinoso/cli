package net.lospi.sphinx;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.CharArrayReader;
import java.io.Reader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class JavaClassParser {
    private static final Logger logger = LoggerFactory.getLogger(JavaClassParser.class);

    public List<JavaClass> generate(String source){
        List<JavaClass> result = new LinkedList<JavaClass>();
        JavaProjectBuilder builder = new JavaProjectBuilder();
        Reader reader = new CharArrayReader(source.toCharArray());
        builder.addSource(reader);
        Collection<JavaSource> sources = builder.getSources();
        for(JavaSource javaSource : sources){
            List<JavaClass> classes = javaSource.getClasses();
            for(JavaClass javaClass : classes) {
                String name = javaClass.getName();
                logger.info("Parsed class: {}", name);
            }
            result.addAll(classes);
        }
        return result;
    }
}
