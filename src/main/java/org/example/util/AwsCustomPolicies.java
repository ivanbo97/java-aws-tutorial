package org.example.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class AwsCustomPolicies {
    public static String getMyOwnPolicyArn() throws IOException {
        String filePath = "C:\\dev\\tutorials\\AWS\\MY_OWN_POLICY_ARN.txt";
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
