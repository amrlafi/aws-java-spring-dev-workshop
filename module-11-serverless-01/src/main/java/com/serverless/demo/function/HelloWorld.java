package com.serverless.demo.function;

import com.serverless.demo.io.CustomEventOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.event.S3EventNotification.S3Entity;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;



public class HelloWorld implements RequestHandler<S3Event, Void> {
	  // Create clients
	  AmazonS3 s3Client = AmazonS3Client.builder().build();

    @Override
    public Void handleRequest(S3Event input, Context context) {
      context.getLogger().log("Input: " + input);

      // For every S3 object
      for (S3EventNotificationRecord event : input.getRecords()) {
        S3Entity entity = event.getS3();
        String bucketName = entity.getBucket().getName();
        String objectKey = entity.getObject().getKey();
        
        context.getLogger().log("bucketName, objectKey");
        
        // analyize logics
        analyzeLogis(bucketName, objectKey, context);
      }
	
      return null;     
    }
    
    private void analyzeLogis(String bucket, String path, Context context)
    {
    	//you can add your logics here
      // 1. download file
      getObjectFromS3(bucket, path, context);
    }
    
    private void getObjectFromS3(String bucket, String prefix, Context context)
    {
    	String SAVE_FILE = "/tmp/save.gz";
      try {
        S3Object response = s3Client.getObject(new GetObjectRequest(bucket, prefix));
        String contentType = response.getObjectMetadata().getContentType();
        S3ObjectInputStream in = response.getObjectContent();
        
        context.getLogger().log("### CONTENT TYPE: " + contentType);
        
        try {
        	
        	//check existing files 
        	File checkfile = new File(SAVE_FILE);
        	if(checkfile.exists()) {
        		context.getLogger().log("### FILE ALREADY EXISTS, Delete it");
        		Files.delete(Paths.get(SAVE_FILE));
        	}
        	
        	Files.copy(in, Paths.get(SAVE_FILE));
        	
        	context.getLogger().log("### Saved Content in /Users/seonpark/tmp/save.gz");  
        	
        } catch(IOException e) {
			        e.printStackTrace();
			        context.getLogger().log("Error during save file excetion:"+ e.getMessage());
			 }
          
      }catch (Exception e) {
        e.printStackTrace();
        context.getLogger().log(String.format(
            "Error getting object %s from bucket %s. Make sure they exist and"
            + " your bucket is in the same region as this function.", prefix, bucket));
        throw e;
      }
    }
    

}
