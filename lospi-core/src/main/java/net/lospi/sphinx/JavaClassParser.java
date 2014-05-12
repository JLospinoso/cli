package net.lospi.sphinx;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

import java.io.CharArrayReader;
import java.io.Reader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class JavaClassParser {
    public List<JavaClass> generate(String source){
        List<JavaClass> result = new LinkedList<JavaClass>();
        JavaProjectBuilder builder = new JavaProjectBuilder();
        Reader reader = new CharArrayReader(source.toCharArray());
        builder.addSource(reader);
        Collection<JavaSource> sources = builder.getSources();
        for(JavaSource javaSource : sources){
            List<JavaClass> classes = javaSource.getClasses();
            result.addAll(classes);
        }
        return result;
    }
}
