package org.example.util.dynamodb;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.example.util.AwsService;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

/**
 * Demo of how to get data in Dynamo DB:
 *
 * <ul>
 *   <li>Using Query Operation approach (can get multiple items)
 *   <li>Using Get Operation approach (gets a single item)
 * </ul>
 */
public class ItemReader extends AwsService {

  private static final Logger logger = Logger.getLogger(ItemReader.class.getName());

  private static final DynamoDbClient dynamoDbClient = DynamoDbClient.builder().build();

  private static void logQueryResponse(List<Map<String, AttributeValue>> returnedItems) {
    if (returnedItems.isEmpty()) {
      logger.info("Query returned 0 results");
      return;
    }

    logger.info("Query Result:");
    returnedItems.forEach(item -> logger.info(item.toString()));
  }

  public static void main(String[] args) throws IOException {
    try {
      awsEnvSetup();
      useQueryOperation();
      useGetOperation();
      getItemByAgeCondition();
    } catch (DynamoDbException ex) {
      logger.severe(ex.getMessage());
      ex.printStackTrace();
    } finally {
      dynamoDbClient.close();
    }
  }

  private static void useQueryOperation() {
    QueryRequest queryRequest =
        QueryRequest.builder()
            .tableName("UserTable")
            .keyConditionExpression("UserID = :userId")
            .expressionAttributeValues(
                Map.of(":userId", AttributeValue.builder().s("user321").build()))
            .build();
    QueryResponse response = dynamoDbClient.query(queryRequest);

    logQueryResponse(response.items());
  }

  private static void useGetOperation() {
    GetItemRequest getItemRequest =
        GetItemRequest.builder()
            .tableName("UserTable")
            .key(Map.of("UserID", AttributeValue.builder().s("user123").build()))
            .build();
    GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);
    logger.info("Get Operation Result:\n" + getItemResponse.item().toString());
  }

  private static void getItemByAgeCondition() {
    QueryRequest queryRequest =
        QueryRequest.builder()
            .tableName("UserTable")
            .keyConditionExpression("UserID = :userId")
            .filterExpression("Age > :ageLimit")
            .expressionAttributeValues(
                Map.of(
                    ":userId",
                    AttributeValue.builder().s("user321").build(),
                    ":ageLimit",
                    AttributeValue.builder().n("26").build()))
            .build();
    QueryResponse response = dynamoDbClient.query(queryRequest);
    logger.info("Results of Query based on conditions:");
    logQueryResponse(response.items());
  }
}
