package hello.logics;

import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

import hello.aws.lambda.MyLambdaServices;
import hello.aws.lambda.io.StepEventInput;
import hello.aws.lambda.io.StepEventOutput;

@Service
public class IntegratedTransLambda {
	final MyLambdaServices myService = LambdaInvokerFactory.builder()
 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
 		 .build(MyLambdaServices.class);
	
	public String RetrieveAndSave(String bucket, String photoPath, Regions region)
	{
		String result = null;
		try {
			// 1. call rekognition
			StepEventInput input = new StepEventInput();
			StepEventOutput output = new StepEventOutput();
			
//			input.setBucket("seon-virginia-2016");
//			input.setPrefix("images/a.jpeg");
			input.setBucket(bucket);
			input.setPrefix(photoPath);
			
			input.setText("");
			input.setTranslated("");
			input.setSourceLangCode("en");
			input.setTargetLangCode("es");
			 
			output = myService.myRekognitionFunc(input);  
			System.out.println("#### rekog output = " + output.getText());
			
			// 2. call trans
			input.setText(output.getText());

			output = myService.myTranslateFunc(input); 
			System.out.println("#### trans output = " + output.getTranslated());
			
			// 3. call ddb
		  input.setTranslated(output.getTranslated());
			output = myService.myDynamoDBFunc(input);  
			result = "SUCCESS";

		} catch(Exception e) {
			result = "FAIL : " + e.getMessage();
		}
		
		return result;
	}

}
