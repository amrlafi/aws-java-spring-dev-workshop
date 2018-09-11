package com.amazonaws.lambda;

import java.util.List;

import com.amazonaws.lambda.io.StepEventInput;
import com.amazonaws.lambda.io.StepEventOutput;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;

public class LambdaRekognitionHandler implements RequestHandler<StepEventInput, StepEventOutput> {
	
		private AmazonRekognition rekognitionClient;
	  private Regions region = Regions.US_EAST_1;
		

    @Override
    public StepEventOutput handleRequest(StepEventInput input, Context context) {
    	
    	StepEventOutput output  = new StepEventOutput();
  			List<Label> labels = null;
  			StringBuilder sb = new StringBuilder();
  			
  			this.initRekoginitionClient();
	
	    DetectLabelsRequest request = new DetectLabelsRequest()
	  		  .withImage(new Image()
	  		  .withS3Object(new S3Object()
	  		  .withName(input.getPrefix()).withBucket(input.getBucket())))
	  		  .withMaxLabels(10)
	  		  .withMinConfidence(75F);
	
       DetectLabelsResult result = rekognitionClient.detectLabels(request);
       labels = result.getLabels();
       
   		for (Label label: labels) {
   			if(sb.length() > 0 ) sb.append(",").append(label.getName());
   			else sb.append(label.getName());
   		}
	  		
   		output.setBucket(input.getBucket());
	  		output.setPrefix(input.getPrefix());
	  		output.setText(input.getText());
	  		output.setTranslated(input.getTranslated()); 		
	  		output.setSourceLangCode(input.getSourceLangCode());
	  		output.setTargetLangCode(input.getTargetLangCode());
	  		//set result
	   	output.setText(sb.toString());
	    return output;
    }
    
    private void initRekoginitionClient() {
    		rekognitionClient = AmazonRekognitionClientBuilder
 	         .standard()
 	         .withRegion(region)
 	         .build();
    } 

}
