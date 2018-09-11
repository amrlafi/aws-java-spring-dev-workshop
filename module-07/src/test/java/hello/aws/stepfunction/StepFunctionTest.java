package hello.aws.stepfunction;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;

import hello.Application;

@SpringBootTest
public class StepFunctionTest {
	
	@Test
	public void callStepFucntion()
	{
		final AWSStepFunctions stepFunctionclient = AWSStepFunctionsClientBuilder.defaultClient();
		
		URL inputFile = Application.class.getResource("/aws/step-input.json");

		String input = jsonFileRead(inputFile);
		
		StartExecutionRequest startExecutionRequest 
		= new StartExecutionRequest()
		.withInput(input)
		.withStateMachineArn("arn:aws:states:us-east-1:550622896891:stateMachine:workshop-stepfunction").withSdkRequestTimeout(30000);
		
		StartExecutionResult executionResult = stepFunctionclient.startExecution(startExecutionRequest);
		
		assertContains(executionResult.getSdkResponseMetadata(), "AWS_REQUEST_ID");
	}
	
	private void assertContains(ResponseMetadata sdkResponseMetadata,
			String string) {
		// TODO Auto-generated method stub
		
	}

	public String jsonFileRead(URL input)
	{
		BufferedReader br = null;
		FileReader fr = null;
		StringBuffer sb = new StringBuffer();
		
		try {
	    br = new BufferedReader(new InputStreamReader(input.openStream()));
	    String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (br != null)br.close();
				if (fr != null)fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}
}

