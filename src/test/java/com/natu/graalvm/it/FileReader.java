package com.natu.graalvm.it;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    public static String readFromResources(String fileName) throws IOException {
        String filePath = getResourceFilePath(fileName);
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    private static String getResourceFilePath(String fileName) {
        ClassLoader classLoader = FileReader.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }
        Path resourcePath = Paths.get(resource.getFile());
        return resourcePath.toString();
    }
}
