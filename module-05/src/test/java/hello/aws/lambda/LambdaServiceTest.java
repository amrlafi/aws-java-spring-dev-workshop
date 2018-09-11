package hello.aws.lambda;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

import hello.aws.lambda.io.StepEventInput;
import hello.aws.lambda.io.StepEventOutput;

public class LambdaServiceTest {
	//@Test
	//public void callCustomLamdba()
	//{
	//	
	//  final MyLambdaServices myService = LambdaInvokerFactory.builder()
	//  		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
	//  		 .build(MyLambdaServices.class);
	//  
	//  CustomEventInput input = new CustomEventInput();
	//  List<Integer> list = new ArrayList();
	//  list.add(1);
	//		list.add(5);
	//		input.setValues(list);
	//
	//  CustomEventOutput output = myService.myCustumFunc(input);  
	//  assertEquals((int)output.getValue(), (int)5);
	//  
	//}
	//
	//@Test
	//public void callDynamoDBLamdba()
	//{
	//	
	//	final MyLambdaServices myService = LambdaInvokerFactory.builder()
	//	 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
	//	 		 .build(MyLambdaServices.class);
	//	 
	//	StepEventInput input = new StepEventInput();
	//	
	//	input.setBucket("seon-virginia-2016");
	//	input.setPrefix("/images/a.jpeg");
	//	input.setText("hello");
	//	input.setTranslated("hallo");
	//	 
	//	StepEventOutput output = myService.myDynamoDBFunc(input);  
	//	assertEquals(output.getBucket(), "seon-virginia-2016");
	//	
	//  
	//}
	//
	//@Test
	//public void callRekognitionLamdba()
	//{
	//	
	//	
	//	final MyLambdaServices myService = LambdaInvokerFactory.builder()
	//	 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
	//	 		 .build(MyLambdaServices.class);
	//	 
	//	StepEventInput input = new StepEventInput();
	//	
	//	input.setBucket("seon-virginia-2016");
	//	input.setPrefix("images/a.jpeg");
	//	 
	//	StepEventOutput output = myService.myRekognitionFunc(input);  
	//	assertNotNull(output.getText());
	//	
	//  
	//}	
	
	@Test
	public void callTranslateLamdba()
	{
		
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
	}

}
