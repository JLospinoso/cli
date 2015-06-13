package net.lospi.sphinx;

import java.io.*;
import java.net.URISyntaxException;

public class Utils {
    public static void toFile(String fileName, String content) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    public static String fromFile(String fileName) throws IOException, URISyntaxException {
        InputStream is = new FileInputStream(fileName);
        return streamToString(fileName, is);
    }

    public static String fromResource(String fileName) throws IOException, URISyntaxException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        return streamToString(fileName, is);
    }

    private static String streamToString(String fileName, InputStream is) throws FileNotFoundException {
        if(is==null){
            throw new FileNotFoundException("Could not find file " + fileName);
        }
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
