package org.example;

import java.io.IOException;
import java.util.Optional;
import org.example.util.AwsService;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.ListUsersRequest;
import software.amazon.awssdk.services.iam.model.ListUsersResponse;
import software.amazon.awssdk.services.iam.model.User;

public class AwsIamUserViewer extends AwsService {
  public static void main(String[] args) throws IOException {
    awsEnvSetup();
    IamClient iamCLient = null;
    try {
      iamCLient = IamClient.builder().build();
      ListUsersRequest listUsersRequest = ListUsersRequest.builder().build();
      ListUsersResponse listUsersResponse = iamCLient.listUsers(listUsersRequest);

      for (User user : listUsersResponse.users()) {
        System.out.println(user.userName());
      }
    } catch (IamException exc) {
      System.out.println(exc);
    } finally {
      Optional.ofNullable(iamCLient).ifPresent(iamClient -> iamClient.close());
    }
  }
}
