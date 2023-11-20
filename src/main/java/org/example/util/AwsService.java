package org.example.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AwsService {

    public static void awsEnvSetup() throws IOException {
        String filePath = "C:\\dev\\tutorials\\AWS\\AWS_ACCESS_KEY.txt";
        String awsAccessKey = new String(Files.readAllBytes(Paths.get(filePath)));
        System.setProperty("AWS_ACCESS_KEY_ID", awsAccessKey);
    }
}
