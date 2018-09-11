package com.amazonaws.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.Writer;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.LambdaLogger;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


import com.amazonaws.lambda.io.StepEventOutput;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.Writer;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.LambdaLogger;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


public class Translate implements RequestStreamHandler {
    JSONParser parser = new JSONParser();

  	private AmazonTranslate translate;
    private Regions region = Regions.US_EAST_1;

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        LambdaLogger logger = context.getLogger();
        logger.log("Loading Java Lambda handler of ProxyWithStream");

    	  this.initTranslateClient();
    	  
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject responseJson = new JSONObject();
        String text = "you";
        String sourceLang = "en";
        String targetLang = "es";
        String responseCode = "200";

        try {
          	JSONObject event = (JSONObject)parser.parse(reader);
          
            if (event.get("body") != null) {
                JSONObject body = (JSONObject)parser.parse((String)event.get("body"));
                if ( body.get("text") != null) {
                    text = (String)body.get("text");
                }
                
                if ( body.get("sourceLangCode") != null) {
                	sourceLang = (String)body.get("sourceLangCode");
                }
                
                if ( body.get("targetLangCode") != null) {
                	targetLang = (String)body.get("targetLangCode");
                }
                
            }

//            String greeting = "Good " + time + ", " + name + " of " + city + ". ";
//            if (day!=null && day != "") greeting += "Happy " + day + "!";
            
        	  TranslateTextRequest request = new TranslateTextRequest()
        		    .withText(text)
        		    .withSourceLanguageCode(sourceLang)
        		    .withTargetLanguageCode(targetLang);
        	   
        		TranslateTextResult result  = translate.translateText(request);

            JSONObject responseBody = new JSONObject();
            responseBody.put("translated", result.getTranslatedText());
            responseBody.put("message", "hello");

            JSONObject headerJson = new JSONObject();
            headerJson.put("x-custom-header", "my custom header value");

            responseJson.put("isBase64Encoded", false);
            responseJson.put("statusCode", responseCode);
            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toString());  

        } catch(ParseException pex) {
            responseJson.put("statusCode", "400");
            responseJson.put("exception", pex);
        }

        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
    }
    
    private void initTranslateClient() {
	  	translate = AmazonTranslateClientBuilder
					.standard()
		       .withRegion(region)
	         .build();
  }
}
