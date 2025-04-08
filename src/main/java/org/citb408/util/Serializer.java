package org.citb408.util;

import java.io.*;

public class Serializer {
    public static <T extends Serializable> void serialize(String filePath, T item) throws IOException {
        File dir = new File("receipts");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(item);
        }
    }
}
