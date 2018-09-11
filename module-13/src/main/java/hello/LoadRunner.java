package hello;

import java.util.concurrent.TimeUnit;
import hello.aws.lambda.io.*;
import hello.aws.lambda.MyLambdaServices;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import java.time.Instant;


public class LoadRunner {

    private int THREAD_NUM = 100;
    private int ITER = 10;
    private int PAUSE = 0;

    private long startTime;
    private long endTime;
    private long totalExecution;

    public LoadRunner(int thread_num, int iter) {
        THREAD_NUM = thread_num;
        ITER = iter;

    } 
    public void runThread() {
        System.out.println("Main thread starts here...");

        // Create Threads
        Thread t[] = new Thread[THREAD_NUM];

        for(int i=0; i < THREAD_NUM; i++) {
            t[i] = new MyThreadTask(ITER, PAUSE);            
        }

        startTime = getTimestamp();

        for(int i = 0; i < THREAD_NUM; i++) {
            t[i].start();   
        }

        try {
            for(int i = 0; i < THREAD_NUM; i++) {
                t[i].join();   
            }
        }catch(InterruptedException ie){
        ie.printStackTrace();
        } 

        endTime = getTimestamp();

        System.out.println("endTime="+endTime);

        calculateResult();

    }

    private long getTimestamp()
    {
        return Instant.now().toEpochMilli();
    }

    private void calculateResult()
    {
        float duration = endTime - startTime;

        totalExecution = THREAD_NUM * ITER ;
        System.out.println("duration="+duration +"total="+totalExecution);       
        float rps = (float) (totalExecution/duration*1000);

        System.out.println("###Result###");
        System.out.printf("Total = %d  duration(s)= %.2f  RPS = %f \n", totalExecution, (duration/1000), rps);
    }
}

// class MyThreadTaskSpawn extends Thread{

//     private int THREAD_NUM = 100;
//     private int ITER = 10;
//     private int PAUSE = 0;

//     public MyThreadTaskSpawn(int thread_num, int iter)  {
//         System.out.println("Spawning Threads");

//         THREAD_NUM = thread_num;
//         ITER = iter;

//     }

//     public void run() {



//     }
// }

class MyThreadTask extends Thread {
    private static int count = 0;
    private static int ITERATION;
    private static int PAUSE;
    private int id;
    
    private final MyLambdaServices myService;
    private CustomEventInput input;


	public MyThreadTask(int iter, int pause) {
        myService = LambdaInvokerFactory.builder()
        .lambdaClient(AWSLambdaClientBuilder.defaultClient())
        .build(MyLambdaServices.class);

        input = new CustomEventInput();
        input.setText("Hello");   


		this.id = ++count;
        ITERATION = iter;
        PAUSE = pause;
    }
    
	@Override
	public void run(){
		for(int i = 0; i<ITERATION; i++) {
            String output = myService.sqsProducerLambda(input);        
          
            System.out.println("<" + id + "> output[" + i + "] = " + output);
			try {
				TimeUnit.MICROSECONDS.sleep((long)PAUSE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}