package Helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    public static List<String> ReadFile(String path) {
        List<String> fileContent = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while((line = br.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return fileContent;
    }

}
