package org.example;

import java.io.IOException;
import java.util.Optional;
import org.example.util.AwsService;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.ListPoliciesRequest;
import software.amazon.awssdk.services.iam.model.ListPoliciesResponse;
import software.amazon.awssdk.services.iam.model.Policy;

public class AwsPolicyViewer extends AwsService {
  public static void main(String[] args) throws IOException {
    awsEnvSetup();
    IamClient iamCLient = null;
    try {
      iamCLient = IamClient.builder().build();
      ListPoliciesRequest listPoliciesRequest = ListPoliciesRequest.builder().build();
      ListPoliciesResponse listPoliciesResponse = iamCLient.listPolicies(listPoliciesRequest);

      for (Policy policy : listPoliciesResponse.policies()) {
        System.out.println(policy.policyName());
      }
    } catch (IamException exc) {
      System.out.println(exc);
    } finally {
      Optional.ofNullable(iamCLient).ifPresent(iamClient -> iamClient.close());
    }
  }
}
