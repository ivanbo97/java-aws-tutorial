package org.example;

import java.io.IOException;
import java.util.Optional;
import org.example.util.AwsService;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.CreateUserRequest;
import software.amazon.awssdk.services.iam.model.CreateUserResponse;
import software.amazon.awssdk.services.iam.model.IamException;

public class AwsUserCreator extends AwsService {
  public static void main(String[] args) throws IOException {
    awsEnvSetup();
    IamClient iamCLient = null;
    try {
      iamCLient = IamClient.builder().build();
      CreateUserRequest userRequest = CreateUserRequest.builder().userName("AWSNewbie").build();

      CreateUserResponse userResponse = iamCLient.createUser(userRequest);
    } catch (IamException exc) {
      System.out.println(exc);
    } finally {
      Optional.ofNullable(iamCLient).ifPresent(iamClient -> iamClient.close());
    }
  }
}
