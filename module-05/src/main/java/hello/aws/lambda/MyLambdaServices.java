package hello.aws.lambda;

import com.amazonaws.services.lambda.invoke.LambdaFunction;

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

public interface MyLambdaServices {
	@LambdaFunction(functionName="MyCustomFunc")
	CustomEventOutput myCustumFunc(CustomEventInput input);
	
	@LambdaFunction(functionName="MyFunction-workshop-dynamodb")
	StepEventOutput myDynamoDBFunc(StepEventInput input);	

	@LambdaFunction(functionName="MyFunction-workshop-rekognition")
	StepEventOutput myRekognitionFunc(StepEventInput input);

	@LambdaFunction(functionName="MyFunction-translate")
	StepEventOutput myTranslateFunc(StepEventInput input);
	
}
