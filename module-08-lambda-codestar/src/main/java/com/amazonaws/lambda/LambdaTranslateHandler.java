package com.amazonaws.lambda;

import com.amazonaws.lambda.io.StepEventInput;
import com.amazonaws.lambda.io.StepEventOutput;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

public class LambdaTranslateHandler implements RequestHandler<StepEventInput, StepEventOutput> {
	
	
	private AmazonTranslate translate;
  private Regions region = Regions.US_EAST_1;
	
  @Override
  public StepEventOutput handleRequest(StepEventInput input, Context context) {
  	
	  	StepEventOutput output = new StepEventOutput();
	  
	  this.initTranslateClient();
	  
	  TranslateTextRequest request = new TranslateTextRequest()
		    .withText(input.getText())
		    .withSourceLanguageCode(input.getSourceLangCode())
		    .withTargetLanguageCode(input.getTargetLangCode());
	   
		TranslateTextResult result  = translate.translateText(request);
	
		output.setBucket(input.getBucket());
		output.setPrefix(input.getPrefix());
		output.setText(input.getText());
		output.setTranslated(input.getTranslated()); 		
		output.setSourceLangCode(input.getSourceLangCode());
		output.setTargetLangCode(input.getTargetLangCode());
		//set result
		output.setTranslated(result.getTranslatedText());
	  
		return output;

  }
  
  private void initTranslateClient() {
	  	translate = AmazonTranslateClientBuilder
					.standard()
		       .withRegion(region)
	         .build();
  }
}
