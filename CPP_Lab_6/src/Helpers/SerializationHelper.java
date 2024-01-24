package Helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerializationHelper {
    public static void serialize(List<Object> list, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(list);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T> List<T> deserialize(String fileName) {
        List<T> deserializedCollection = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                deserializedCollection = (List<T>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage() +  " " + Arrays.toString(e.getStackTrace()));
        }

        return deserializedCollection;
    }

}
