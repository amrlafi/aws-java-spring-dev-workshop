package hello.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;

import hello.Application;
import hello.config.DynamoDBConfig;
import hello.model.ddb.PhotoInfo;
import hello.repository.ddb.PhotoInfoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PhotoInfoTest {
	
  private DynamoDBMapper dynamoDBMapper;
  private AmazonDynamoDBAsync amazonDynamoDBAsync;
  private Regions region = Regions.US_EAST_1;
  
  private boolean ASYNC = false; //to show the differenece of Sync and Async
  
//  @Autowired
//  private AmazonDynamoDB amazonDynamoDB;

  @Autowired
  PhotoInfoRepository repository;


//  @Before
//  public void setup() throws Exception {
//	  AWSCredentials credentials;
//	  try {
//	      credentials = new ProfileCredentialsProvider("default").getCredentials();
//	  } catch(Exception e) {
//	     throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
//	      + "Please make sure that your credentials file is at the correct "
//	      + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
//	  }
//
//	  if(ASYNC) {
//	  		amazonDynamoDBAsync = AmazonDynamoDBAsyncClientBuilder.standard()
//	        .withRegion(region)
//	        .withCredentials(new AWSStaticCredentialsProvider(credentials))
//	        .build();
//		  
//			dynamoDBMapper = new DynamoDBMapper(amazonDynamoDBAsync);
//	  		  
//	  } else {
//		  amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
//	         .withRegion(region)
//	         .withCredentials(new AWSStaticCredentialsProvider(credentials))
//	         .build();
//		  
//			dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
//	  }
//
//
//		
//		try {
//			CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(PhotoInfo.class);
//			tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
//			
//			if(ASYNC) {
//	      Future<CreateTableResult> future_res = amazonDynamoDBAsync.createTableAsync(tableRequest);
//        System.out.print("\n\n####### Waiting for async callback");
//  
////        while (!future_res.isDone() && !future_res.isCancelled()) {
//        while (!future_res.isDone()) {
//            try {
//                Thread.sleep(1000);
//            }
//            catch (InterruptedException e) {
//                System.err.println("Thread.sleep() was interrupted!");
//                System.exit(0);
//            }
//            System.out.print("### .");
//        }	      
//			} else { //SYNC
//				amazonDynamoDB.createTable(tableRequest);
//			}
//			
//		} catch (ResourceInUseException e) {
//			System.out.print("\n\n####### Table already exist!");
//		}
//		
//		// delete all after creating photo table
////		List<PhotoInfo> list =  (List<PhotoInfo>)repository.findAll();
//  }

  @Test
  public void sampleTestCase() {
	  repository.deleteAll();
	  PhotoInfo p = new PhotoInfo("a.jpeg", "hello", "hallo");	
	  repository.save(p);
    
    List<PhotoInfo> result2 = (List<PhotoInfo>) repository.findAll(); 
    
    assertTrue("Not empty", result2.size() > 0);
  }

}
