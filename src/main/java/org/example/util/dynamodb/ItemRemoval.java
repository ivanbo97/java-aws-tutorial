package org.example.util.dynamodb;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;
import org.example.util.AwsService;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

public class ItemRemoval extends AwsService {

  private static final Logger logger = Logger.getLogger(ItemInserter.class.getName());

  public static void main(String[] args) throws IOException {
    awsEnvSetup();

    try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().build()) {
      DeleteItemRequest deleteItemRequest =
          DeleteItemRequest.builder()
              .tableName("UserTable")
              .key(
                  Collections.singletonMap("UserID", AttributeValue.builder().s("user123").build()))
              .build();

      DeleteItemResponse deleteResponse = dynamoDbClient.deleteItem(deleteItemRequest);
      logger.info(
          "Item deleted successfully. Request id: "
              + deleteResponse.responseMetadata().requestId());
    } catch (Exception e) {
      logger.severe("Error deleting item : " + e.getMessage());
    }
  }
}
