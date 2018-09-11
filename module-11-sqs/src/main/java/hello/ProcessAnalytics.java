package hello;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


import hello.io.FileData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;


public class ProcessAnalytics
{
    private static final String QUEUE_NAME = "testQueue";
    private static AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    public static void main(String[] args)
    {
        final AmazonSQS sqs = AmazonSQSClientBuilder.standard().build();

        try {
            CreateQueueResult create_result = sqs.createQueue(QUEUE_NAME);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }

        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();

        String jsonInput = "{\"bucket\" : \"seon-virginia-01\" , \"prefix\" : \"nasa_19950630.22-19950728.12.tsv.gz\"}";
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(jsonInput)
                .withDelaySeconds(0);
        sqs.sendMessage(send_msg_request);


        // receive messages from the queue
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        receiveMessageRequest.setMaxNumberOfMessages(10);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();        

        System.out.println("Message size = " + messages.size());
        
        try {
	        // delete messages from the queue
	        for (Message m : messages) {
	        		String jsonBody = m.getBody();
	            System.out.println("Messages = " + jsonBody);
	            ObjectMapper mapper = new ObjectMapper();
	            FileData data = mapper.readValue(jsonBody, FileData.class);
	            
	            System.out.println("json data.prefix = " + data.getPrefix());
	            
	            //this is processing logis
	            analyzeLogis(data);
	            
	            sqs.deleteMessage(queueUrl, m.getReceiptHandle());
	        }
        } catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
    }
    
    private static void analyzeLogis(FileData data)
    {
    	//you can add your logics here
      //download file
      getObjectFromS3(data.getBucket(), data.getPrefix());
   
    }
    
    private static void getObjectFromS3(String bucket, String prefix)
    {

      try {
          S3Object response = s3.getObject(new GetObjectRequest(bucket, prefix));
          String contentType = response.getObjectMetadata().getContentType();
          S3ObjectInputStream in = response.getObjectContent();
          
          
          System.out.println("### CONTENT TYPE: " + contentType);
          
          try {
          
          	Files.copy(in, Paths.get("/Users/seonpark/tmp/save.gz"));
          	System.out.println("### Saved Content in /Users/seonpark/tmp/save.gz");  
          	
          } catch(IOException e) {
				        e.printStackTrace();
				        System.out.println("Error during save file excetion:"+ e.getMessage());
				 }
          
      }catch (Exception e) {
          e.printStackTrace();
          System.out.println(String.format(
              "Error getting object %s from bucket %s. Make sure they exist and"
              + " your bucket is in the same region as this function.", prefix, bucket));

      }
    }
    


}
