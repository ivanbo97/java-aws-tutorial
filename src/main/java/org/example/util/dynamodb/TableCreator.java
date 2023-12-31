package org.example.util.dynamodb;

import java.io.IOException;
import java.util.logging.Logger;
import org.example.util.AwsService;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

public class TableCreator extends AwsService {

  private static final Logger logger = Logger.getLogger(TableCreator.class.getName());

  public static void main(String[] args) throws IOException {
    awsEnvSetup();
    try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().build()) {

      AttributeDefinition partitionKey =
          AttributeDefinition.builder()
              .attributeName("UserID")
              .attributeType(ScalarAttributeType.S)
              .build();
      KeySchemaElement keySchemaElement =
          KeySchemaElement.builder().attributeName("UserID").keyType(KeyType.HASH).build();

      // Define read/write capacity
      ProvisionedThroughput provisionedThroughput =
          ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build();

      CreateTableRequest createTableRequest =
          CreateTableRequest.builder()
              .tableName("UserTable")
              .attributeDefinitions(partitionKey)
              .keySchema(keySchemaElement)
              .provisionedThroughput(provisionedThroughput)
              .build();

      CreateTableResponse createTableResponse = dynamoDbClient.createTable(createTableRequest);
      logger.info("Table created : " + createTableResponse.tableDescription());

    } catch (DynamoDbException exc) {
      logger.severe("Error during DynamoDB table creation : " + exc.getMessage());
    }
  }
}
