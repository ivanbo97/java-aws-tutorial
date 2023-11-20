package org.example;


import org.example.util.AwsService;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.UpdateUserRequest;
import software.amazon.awssdk.services.iam.model.UpdateUserResponse;

import java.io.IOException;
import java.util.Optional;

public class AwsUserUpdater extends AwsService {
    public static void main(String[] args) throws IOException {
        awsEnvSetup();
        IamClient iamCLient = null;
        try {
            iamCLient = IamClient.builder().build();
            UpdateUserRequest updateUserRequest = UpdateUserRequest.builder().userName("javauser").newUserName("JavaMaster").build();
            UpdateUserResponse updateUserResponse = iamCLient.updateUser(updateUserRequest);
            System.out.println(updateUserResponse);
        } catch (IamException exc) {
            System.out.println(exc);
        } finally {
            Optional.ofNullable(iamCLient).ifPresent(iamClient -> iamClient.close());
        }
    }
}