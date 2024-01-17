package org.example.util.dynamodb;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;
import org.example.util.AwsService;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

public class ItemModifier extends AwsService {

  private static final Logger logger = Logger.getLogger(ItemReader.class.getName());

  public static void main(String[] args) throws IOException {
    awsEnvSetup();

    try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().build()) {
      UpdateItemRequest updateRequest =
          UpdateItemRequest.builder()
              .tableName("UserTable")
              .key(
                  Collections.singletonMap("UserID", AttributeValue.builder().s("user123").build()))
              .updateExpression("SET Age = :newAge")
              .expressionAttributeValues(
                  Collections.singletonMap(":newAge", AttributeValue.builder().n("35").build()))
              .build();

      dynamoDbClient.updateItem(updateRequest);
      logger.info("Item updated successfully!");
    } catch (Exception e) {
      logger.severe("Error updating item : " + e.getMessage());
    }
  }
}
