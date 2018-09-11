package hello;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class LongPolling
{
		private static final String QUEUE_NAME = "longPollingQueue";
	
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


        // Enable long polling when creating a queue
        CreateQueueRequest create_request = new CreateQueueRequest()
                .withQueueName(QUEUE_NAME)
                .addAttributesEntry("ReceiveMessageWaitTimeSeconds", "20");

        try {
            sqs.createQueue(create_request);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }

        // Enable long polling on an existing queue
        SetQueueAttributesRequest set_attrs_request = new SetQueueAttributesRequest()
                .withQueueUrl(queueUrl)
                .addAttributesEntry("ReceiveMessageWaitTimeSeconds", "20");
        sqs.setQueueAttributes(set_attrs_request);

        // Enable long polling on a message receipt
        ReceiveMessageRequest receive_request = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withWaitTimeSeconds(20);
        sqs.receiveMessage(receive_request);
    }
}
