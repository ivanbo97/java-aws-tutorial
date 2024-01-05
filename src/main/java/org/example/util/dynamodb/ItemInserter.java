package org.example.util.dynamodb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.example.util.AwsService;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

public class ItemInserter extends AwsService {

  private static final Logger logger = Logger.getLogger(ItemInserter.class.getName());

  public static void main(String[] args) throws IOException {
    awsEnvSetup();

    try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().build()) {
      Map<String, AttributeValue> item = new HashMap<>();
      item.put("UserID", AttributeValue.builder().s("user321").build());
      item.put("FirstName", AttributeValue.builder().s("Jenny").build());
      item.put("LastName", AttributeValue.builder().s("Doe").build());
      item.put("Email", AttributeValue.builder().s("jenny@email.com").build());

      PutItemRequest putItemRequest =
          PutItemRequest.builder().tableName("UserTable").item(item).build();
      PutItemResponse putItemResponse = dynamoDbClient.putItem(putItemRequest);
      String requestId = putItemResponse.responseMetadata().requestId();
      logger.info("Item inserted Request ID : " + requestId);
    } catch (DynamoDbException es) {
      logger.severe(es.getMessage());
    }
  }
}
