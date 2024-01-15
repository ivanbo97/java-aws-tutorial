package org.example.util.dynamodb;

import java.io.IOException;
import java.util.logging.Logger;
import org.example.util.AwsService;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

public class GSICreator extends AwsService {

  private static final Logger logger = Logger.getLogger(ItemReader.class.getName());

  public static void main(String[] args) throws IOException {
    awsEnvSetup();

    try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().build()) {
      GlobalSecondaryIndex gsi =
          GlobalSecondaryIndex.builder()
              .indexName("AgeIndex")
              .keySchema(
                  KeySchemaElement.builder().attributeName("UserID").keyType(KeyType.HASH).build(),
                  KeySchemaElement.builder().attributeName("Age").keyType(KeyType.RANGE).build())
              .projection(Projection.builder().projectionType(ProjectionType.ALL).build())
              .provisionedThroughput(
                  ProvisionedThroughput.builder()
                      .readCapacityUnits(5L)
                      .writeCapacityUnits(5L)
                      .build())
              .build();

      CreateGlobalSecondaryIndexAction gsiAction =
          CreateGlobalSecondaryIndexAction.builder()
              .indexName(gsi.indexName())
              .keySchema(gsi.keySchema())
              .projection(gsi.projection())
              .provisionedThroughput(gsi.provisionedThroughput())
              .build();

      UpdateTableRequest updateTableRequest =
          UpdateTableRequest.builder()
              .tableName("UserTable")
              .attributeDefinitions(
                  AttributeDefinition.builder()
                      .attributeName("Age")
                      .attributeType(ScalarAttributeType.N)
                      .build(),
                  AttributeDefinition.builder()
                      .attributeName("UserID")
                      .attributeType(ScalarAttributeType.S)
                      .build())
              .globalSecondaryIndexUpdates(
                  GlobalSecondaryIndexUpdate.builder().create(gsiAction).build())
              .build();

      dynamoDbClient.updateTable(updateTableRequest);
      logger.info("GSI added to existing table");

    } catch (Exception exc) {
      logger.severe("Error creating table : " + exc.getMessage());
    }
  }
}
