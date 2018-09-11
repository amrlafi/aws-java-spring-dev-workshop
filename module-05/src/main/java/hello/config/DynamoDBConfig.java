package hello.config;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableDynamoDBRepositories(basePackages = "hello.repository.ddb")
public class DynamoDBConfig {

	  @Value("${amazon.dynamodb.endpoint}")
	  private String amazonDynamoDBEndpoint;
	
	  @Value("${amazon.aws.accesskey}")
	  private String amazonAWSAccessKey;
	
	  @Value("${amazon.aws.secretkey}")
	  private String amazonAWSSecretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
    	   
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .build();
            
        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
  	  	AWSCredentials credentials;
	  	  try {
	  	      credentials = new ProfileCredentialsProvider("default").getCredentials();
	  	  } catch(Exception e) {
	  	     throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
	  	      + "Please make sure that your credentials file is at the correct "
	  	      + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
	  	  }
        return credentials;
    }

}
