package hello.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;


import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
