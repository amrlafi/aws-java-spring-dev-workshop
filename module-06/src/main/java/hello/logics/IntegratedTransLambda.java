package hello.logics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;

import hello.Application;


@Service
public class IntegratedTransLambda {
	
	public String RetrieveAndSave(String bucket, String photoPath, Regions region)
	{
		String result = null;
		try {
		
			final AWSStepFunctions stepFunctionclient = AWSStepFunctionsClientBuilder.defaultClient();
			
//			URL inputFile = Application.class.getResource("/aws/step-input.json");
//			String input = jsonFileRead(inputFile);
			
			StringBuilder input = new StringBuilder();
			input.append("{ ")
					.append("  \"bucket\": \"")
					.append(bucket)
					.append("\",  ")
					.append("  \"prefix\": \"")
					.append(photoPath)
					.append("\",  ")
					.append("  \"text\" : \"Hello, hello\",  ")	
					.append("  \"translated\" : \"\",  ")	
					.append("  \"sourceLangCode\" : \"en\",  ")	
					.append("  \"targetLangCode\" : \"es\"  ")
					.append(" } ");

			StartExecutionRequest startExecutionRequest 
			= new StartExecutionRequest()
			.withInput(input.toString())
			.withStateMachineArn("arn:aws:states:us-east-1:550622896891:stateMachine:workshop-stepfunction").withSdkRequestTimeout(30000);
			
			StartExecutionResult executionResult = stepFunctionclient.startExecution(startExecutionRequest);
			
			
		} catch(Exception e) {
			result = "FAIL : " + e.getMessage();
		}
		
		return result;
	}
	
//	public String jsonFileRead(URL input)
//	{
//		BufferedReader br = null;
//		FileReader fr = null;
//		StringBuffer sb = new StringBuffer();
//		
//		try {
//	    br = new BufferedReader(new InputStreamReader(input.openStream()));
//	    String line;
//			while ((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		} finally {
//			try {
//				if (br != null)br.close();
//				if (fr != null)fr.close();
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//		return sb.toString();
//	}

}
