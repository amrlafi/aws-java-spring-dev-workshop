package hello.aws.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.xray.AWSXRay;

import hello.aws.lambda.io.CustomEventInput;
import hello.aws.lambda.io.CustomEventOutput;
import hello.aws.lambda.io.DDBEventInput;
import hello.aws.lambda.io.DDBEventOutput;
import hello.aws.lambda.io.RekoEventInput;
import hello.aws.lambda.io.RekoEventOutput;
import hello.aws.lambda.io.StepEventInput;
import hello.aws.lambda.io.StepEventOutput;
import hello.aws.lambda.io.TransEventInput;
import hello.aws.lambda.io.TransEventOutput;

import java.util.List;


@SpringBootTest
public class CustomLambdaTest {
    
//	@Test
//	public void callCustomLamdba()
//	{
//		AWSXRay.beginSegment("callCustomLamdba"); 
//		
//    final MyLambdaServices myService = LambdaInvokerFactory.builder()
//    		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
//    		 .build(MyLambdaServices.class);
//    
//    CustomEventInput input = new CustomEventInput();
//    List<Integer> list = new ArrayList();
//    list.add(1);
//  		list.add(5);
//  		input.setValues(list);
//
//    CustomEventOutput output = myService.myCustumFunc(input);  
//    assertEquals((int)output.getValue(), (int)5);
//    
//    AWSXRay.endSegment();	    
//	}
//	
//	@Test
//	public void callDynamoDBLamdba()
//	{
//		AWSXRay.beginSegment("callDynamoDBLamdba test"); 
//		
//		final MyLambdaServices myService = LambdaInvokerFactory.builder()
//		 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
//		 		 .build(MyLambdaServices.class);
//		 
//		StepEventInput input = new StepEventInput();
//		
//		input.setBucket("seon-virginia-2016");
//		input.setPrefix("/images/a.jpeg");
//		input.setText("hello");
//		input.setTranslated("hallo");
//		 
//		StepEventOutput output = myService.myDynamoDBFunc(input);  
//		assertEquals(output.getBucket(), "seon-virginia-2016");
//		
//    
//    AWSXRay.endSegment();	 
//	}
//	
//	@Test
//	public void callRekognitionLamdba()
//	{
//		AWSXRay.beginSegment("callRekognitionLamdba test"); 
//		
//		
//		final MyLambdaServices myService = LambdaInvokerFactory.builder()
//		 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
//		 		 .build(MyLambdaServices.class);
//		 
//		StepEventInput input = new StepEventInput();
//		
//		input.setBucket("seon-virginia-2016");
//		input.setPrefix("images/a.jpeg");
//		 
//		StepEventOutput output = myService.myRekognitionFunc(input);  
//		assertNotNull(output.getText());
//		
//    
//    AWSXRay.endSegment();	 
//	}	
	
	@Test
	public void callTranslateLamdba()
	{
		
		AWSXRay.beginSegment("callTranslateLamdba test"); 
		
		final MyLambdaServices myService = LambdaInvokerFactory.builder()
		 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
		 		 .build(MyLambdaServices.class);
		 
		StepEventInput input = new StepEventInput();
		
		input.setText("Hello");
		input.setSourceLangCode("en");
		input.setTargetLangCode("es");
		 
		StepEventOutput output = myService.myTranslateFunc(input);  
		System.out.println("");
		assertEquals(output.getTranslated().trim(), "Hola");
		
    
    AWSXRay.endSegment();	 
	}		
}
