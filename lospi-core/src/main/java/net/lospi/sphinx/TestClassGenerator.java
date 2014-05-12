package net.lospi.sphinx;

import com.google.common.base.Joiner;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaConstructor;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TestClassGenerator {
    Set<String> imports, fields, tests, mocks;

    public String generate(JavaClass javaClass){
        imports = new HashSet<String>();
        fields = new HashSet<String>();
        tests = new HashSet<String>();
        mocks = new HashSet<String>();
        imports.add("import org.testng.annotations.BeforeMethod;\n");
        imports.add("import org.testng.annotations.Test;\n");
        imports.add("import static org.fest.assertions.api.Assertions.assertThat;\n");
        imports.add("import static org.mockito.Mockito.mock;\n");

        List<JavaConstructor> constructors = javaClass.getConstructors();
        if(constructors.size() > 1) {
            throw new IllegalArgumentException(javaClass.getName() + " does not have a single constructor.");
        }
        JavaConstructor constructor = constructors.get(0);
        List<String> parametersInConstructor = new LinkedList<String>();
        for(JavaParameter javaParameter : constructor.getParameters()) {
            parametersInConstructor.add(javaParameter.getName());
            ingestParameter(javaParameter);
        }
        StringBuilder constructorStatementBuilder = new StringBuilder("\t\t")
                .append(javaClass.getName())
                .append(" underStudy = new ")
                .append(javaClass.getName())
                .append("(")
                .append(Joiner.on(", ").join(parametersInConstructor))
                .append(");\n");
        String constructionStatement = constructorStatementBuilder.toString();
        List<JavaMethod> methods = javaClass.getMethods();
        for(JavaMethod javaMethod : methods){
            if(!javaMethod.isPublic()){
                continue;
            }
            List<JavaParameter> parameters = javaMethod.getParameters();
            List<String> parametersInMethod = new LinkedList<String>();
            for(JavaParameter javaParameter : parameters){
                ingestParameter(javaParameter);
                parametersInMethod.add(javaParameter.getName());
            }
            StringBuilder newTest = new StringBuilder("\t@Test\n")
                .append("\tpublic void ")
                .append(javaMethod.getName())
                .append(" {\n")
                .append(constructionStatement)
                .append("\t\t")
                .append(javaMethod.getReturnType().getValue())
                .append(" result = underStudy.")
                .append(javaMethod.getName())
                .append("(")
                .append(Joiner.on(", ").join(parametersInMethod))
                .append(");\n")
                .append("\t\tassertThat(result).isEqualTo(expected);\n")
                .append("\t}\n\n");
            tests.add(newTest.toString());
        }

        StringBuilder sb = new StringBuilder("package ")
                .append(javaClass.getPackage().getName())
                .append(";\n\n");
        for(String importStatement : imports){
            sb.append(importStatement);
        }
        sb.append("\n").append("public class ")
                .append(javaClass.getName())
                .append("Test {\n");
        for(String field : fields){
            sb.append(field);
        }
        sb.append("\n\t@BeforeMethod\n\tpublic void setUp() {\n");
        for(String mock : mocks){
            sb.append(mock);
        }
        sb.append("\t}\n\n");

        //TODO: Setup
        for(String test : tests){
            sb.append(test);
        }
        sb.append("}");
        return sb.toString();
    }

    private void ingestParameter(JavaParameter javaParameter) {
        StringBuilder newField = new StringBuilder("\tprivate ")
                .append(javaParameter.getType().getValue())
                .append(" ")
                .append(javaParameter.getName())
                .append(";\n");
        fields.add(newField.toString());

        if(!javaParameter.getJavaClass().isPrimitive()) {
            StringBuilder newImport = new StringBuilder("import ")
                    .append(javaParameter.getFullyQualifiedName())
                    .append(";\n");
            imports.add(newImport.toString());
        }

        StringBuilder newMock = new StringBuilder("\t\t")
                .append(javaParameter.getName())
                .append(" = ")
                .append("mock(")
                .append(javaParameter.getType().getValue())
                .append(".class);\n");
        mocks.add(newMock.toString());
    }
}
