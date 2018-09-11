package com.amazonaws.lambda;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.lambda.io.RekoEventInput;
import com.amazonaws.lambda.io.RekoEventOutput;
import com.amazonaws.lambda.io.TransEventInput;
import com.amazonaws.lambda.io.TransEventOutput;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

public class LambdaTranslateHandler implements RequestHandler<TransEventInput, TransEventOutput> {
	
	
	private AmazonTranslate translate;
  private Regions region = Regions.US_EAST_1;
	
  @Override
  public TransEventOutput handleRequest(TransEventInput input, Context context) {
  	
  	TransEventOutput output = new TransEventOutput();
  
  this.initTranslateClient();
  
  try {

   TranslateTextRequest request = new TranslateTextRequest()
	    .withText(input.getText())
	    .withSourceLanguageCode(input.getSourceLangCode())
	    .withTargetLanguageCode(input.getTargetLangCode());
   
		TranslateTextResult result  = translate.translateText(request);
		
		output.setTranslated(result.getTranslatedText());
		
  } catch(Exception e) {

  	  	output.setError_message(e.getMessage());		
  }
  
  return output;

}
  
  private void initTranslateClient() {
	  	translate = AmazonTranslateClientBuilder
					.standard()
		       .withRegion(region)
	         .build();
  }
}
