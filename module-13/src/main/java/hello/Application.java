package hello;


import hello.aws.lambda.io.*;
import hello.aws.lambda.MyLambdaServices;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import hello.LoadRunner;

public class Application {

    public static void main(String[] args) {

        // final MyLambdaServices myService = LambdaInvokerFactory.builder()
        // .lambdaClient(AWSLambdaClientBuilder.defaultClient())
        // .build(MyLambdaServices.class);

        // CustomEventInput input = new CustomEventInput();

        // input.setText("Hello");

        // // CustomEventOutput output = myService.sqsProducerLambda(input);  
        // String output = myService.sqsProducerLambda(input);        
        // System.out.println("output =" + output);
        int thread_num = 1;
        int iter = 1;
        if(args.length >= 2) {
            System.out.println("args[3] = " + args[0] + " args[4] = " + args[1]);
            thread_num = Integer.parseInt(args[0]);
            iter = Integer.parseInt(args[1]);
        }

        LoadRunner load = new LoadRunner(thread_num, iter);
        load.runThread();
    }



}
