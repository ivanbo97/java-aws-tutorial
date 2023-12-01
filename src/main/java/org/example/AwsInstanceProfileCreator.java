package org.example;


import org.example.util.AwsService;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.CreatePolicyRequest;
import software.amazon.awssdk.services.iam.model.CreatePolicyResponse;
import software.amazon.awssdk.services.iam.model.IamException;

import java.io.IOException;
import java.util.Optional;

public class AwsInstanceProfileCreator extends AwsService {
    public static void main(String[] args) throws IOException {
        awsEnvSetup();
        IamClient iamCLient = null;
        try {
            iamCLient = IamClient.builder().build();

            String policyJson = "{\n" +
                    "  \"Version\": \"2012-10-17\",\n" +
                    "  \"Statement\": [\n" +
                    "    {\n" +
                    "      \"Effect\": \"Allow\",\n" +
                    "      \"Action\": \"s3:ListBucket\",\n" +
                    "      \"Resource\": \"arn:aws:s3:::*\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";


            CreatePolicyRequest policyRequest = CreatePolicyRequest.builder().policyName("MyOwnPolicy").policyDocument(policyJson).description("This policy was created as part of AWS Udemy course from my local machine through AWS Java SDK").build();
            CreatePolicyResponse createPolicyResponse = iamCLient.createPolicy(policyRequest);
            System.out.println("Custom policy is created with ARN: " + createPolicyResponse.policy().arn());

        } catch (IamException exc) {
            System.out.println(exc);
        } finally {
            Optional.ofNullable(iamCLient).ifPresent(iamClient -> iamClient.close());
        }
    }
}