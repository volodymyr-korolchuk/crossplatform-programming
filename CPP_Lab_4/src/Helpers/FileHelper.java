package Helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    public List<String> getFileContent(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            List<String> stringList = new ArrayList<>();
            String line;

            while((line = reader.readLine()) != null) {
                stringList.add(line);
            }

            return stringList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

