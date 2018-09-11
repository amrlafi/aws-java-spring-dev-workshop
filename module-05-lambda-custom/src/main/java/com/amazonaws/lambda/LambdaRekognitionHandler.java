package com.amazonaws.lambda;

import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.lambda.io.RekoEventInput;
import com.amazonaws.lambda.io.RekoEventOutput;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;

public class LambdaRekognitionHandler implements RequestHandler<RekoEventInput, RekoEventOutput> {
	
		private AmazonRekognition rekognitionClient;
	  private Regions region = Regions.US_EAST_1;
		

    @Override
    public RekoEventOutput handleRequest(RekoEventInput input, Context context) {
    	
    	RekoEventOutput output  = new RekoEventOutput();
  			List<Label> labels = null;
  			StringBuilder sb = new StringBuilder();
  			
  			this.initRekoginitionClient();
	
	    DetectLabelsRequest request = new DetectLabelsRequest()
	  		  .withImage(new Image()
	  		  .withS3Object(new S3Object()
	  		  .withName(input.getPath()).withBucket(input.getBucket())))
	  		  .withMaxLabels(10)
	  		  .withMinConfidence(75F);
	
	    try {
	       DetectLabelsResult result = rekognitionClient.detectLabels(request);
	       labels = result.getLabels();
	       
	   		for (Label label: labels) {
	   			if(sb.length() > 0 ) sb.append(",").append(label.getName());
	   			else sb.append(label.getName());
	   		}
	   		
	   		output.setText(sb.toString());

	    } catch(AmazonRekognitionException e) {
	       e.printStackTrace();
	       output.setError_message("FAIL" + e.getErrorMessage());
	    }
	    
	    return output;
    }
    
    private void initRekoginitionClient() {
    		rekognitionClient = AmazonRekognitionClientBuilder
 	         .standard()
 	         .withRegion(region)
 	         .build();
    } 

}
