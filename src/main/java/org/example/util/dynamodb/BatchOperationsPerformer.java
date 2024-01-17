package org.example.util.dynamodb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.example.util.AwsService;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

public class BatchOperationsPerformer extends AwsService {

  private static final Logger logger = Logger.getLogger(BatchOperationsPerformer.class.getName());

  public static void main(String[] args) throws IOException {
    awsEnvSetup();

    try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().build()) {

      List<WriteRequest> writeRequests = new ArrayList<>();

      Map<String, AttributeValue> item1 = getItem("user123", "Jane", "White", "25");
      Map<String, AttributeValue> item2 = getItem("user125", "Bob", "Roberts", "27");

      writeRequests.add(getWriteRequest(item1));
      writeRequests.add(getWriteRequest(item2));

      BatchWriteItemRequest batchWriteRequest =
          BatchWriteItemRequest.builder().requestItems(Map.of("UserTable", writeRequests)).build();

      BatchWriteItemResponse batchWriteResponse = dynamoDbClient.batchWriteItem(batchWriteRequest);
      logger.info(
          "Batch execute  with success " + batchWriteResponse.responseMetadata().requestId());

    } catch (DynamoDbException exc) {
      logger.severe("Error executing write items batch " + exc.getMessage());
    }
  }

  private static Map<String, AttributeValue> getItem(
      String userId, String fName, String lName, String age) {
    return Map.of(
        "UserID",
        AttributeValue.builder().s(userId).build(),
        "FirstName",
        AttributeValue.builder().s(fName).build(),
        "LastName",
        AttributeValue.builder().s(lName).build(),
        "Age",
        AttributeValue.builder().n(age).build());
  }

  private static WriteRequest getWriteRequest(Map<String, AttributeValue> item) {
    return WriteRequest.builder().putRequest(PutRequest.builder().item(item).build()).build();
  }
}
