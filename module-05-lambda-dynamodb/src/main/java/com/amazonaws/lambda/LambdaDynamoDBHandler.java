package com.amazonaws.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.lambda.io.StepEventInput;
import com.amazonaws.lambda.io.StepEventOutput;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaDynamoDBHandler implements RequestHandler<StepEventInput, StepEventOutput> {

	private AmazonDynamoDB amazonDynamoDB;
//	static DynamoDB dynamoDB;
  private String TABLE_NAME = "PhotoInfo";
  private Regions region = Regions.US_EAST_1;
  
  @Override
  public StepEventOutput handleRequest(StepEventInput input, Context context) {
  		this.initDynamoDbClient();
  		StepEventOutput output = new StepEventOutput();
  		PutItemResult result = persistData(input);
  			
  		output.setBucket(input.getBucket());
  		output.setPrefix(input.getPrefix());
  		output.setText(input.getText());
  		output.setTranslated(input.getTranslated()); 		
  		output.setSourceLangCode(input.getSourceLangCode());
  		output.setTargetLangCode(input.getTargetLangCode());
	  		
  		return output;

  	}
    	
  private PutItemResult persistData(StepEventInput input) throws ConditionalCheckFailedException {
    
    Map<String, AttributeValue> item1 = new HashMap<String, AttributeValue>();
    item1.put("id", new AttributeValue().withS(UUID.randomUUID().toString()));
    item1.put("bucket", new AttributeValue().withS(input.getPrefix()));
    item1.put("prefix", new AttributeValue().withS(input.getPrefix()));
    item1.put("text", new AttributeValue().withS(input.getText()));
    item1.put("translated", new AttributeValue().withS(input.getTranslated()));
  	
 		PutItemRequest request = new PutItemRequest().withTableName(TABLE_NAME).withItem(item1);
 		PutItemResult output = amazonDynamoDB.putItem(request);
 		return output;
  }
     
  private void initDynamoDbClient() {
  		amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
  }    	

}
