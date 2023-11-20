package org.example;


import org.example.util.AwsService;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.AttachUserPolicyRequest;
import software.amazon.awssdk.services.iam.model.AttachUserPolicyResponse;
import software.amazon.awssdk.services.iam.model.IamException;

import java.io.IOException;
import java.util.Optional;

import static org.example.util.AwsCustomPolicies.getMyOwnPolicyArn;

public class AwsUserPolicyManager extends AwsService {
    public static void main(String[] args) throws IOException {
        awsEnvSetup();
        IamClient iamCLient = null;
        try {
            iamCLient = IamClient.builder().build();
            attachPolicyToUser(iamCLient, "JavaMaster");
        } catch (IamException exc) {
            System.out.println(exc);
        } finally {
            System.out.println("A failure occurred while attaching policy to user");
            Optional.ofNullable(iamCLient).ifPresent(iamClient -> iamClient.close());
        }
    }

    private static void attachPolicyToUser(IamClient iamCLient, String username) throws IOException {
        AttachUserPolicyRequest attachUserPolicyRequest = AttachUserPolicyRequest.builder().userName("JavaMaster").policyArn(getMyOwnPolicyArn()).build();
        AttachUserPolicyResponse attachUserPolicyResponse = iamCLient.attachUserPolicy(attachUserPolicyRequest);
        System.out.printf("Policy attached to user %s successfully! %n", username);
    }
}