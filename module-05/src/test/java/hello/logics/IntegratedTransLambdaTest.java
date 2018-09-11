package hello.logics;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import hello.Application;
import hello.model.ddb.PhotoInfo;
import hello.repository.ddb.PhotoInfoRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class IntegratedTransLambdaTest {
	
	private String bucket = "seon-virginia-2016";
	private Regions region = Regions.US_EAST_1;
	String photoPath = "images/a.jpeg";

	
	@Autowired
	IntegratedTransLambda tr;
	
	@Test
	public void testRetrieveAndSave()
	{
		String result = tr.RetrieveAndSave(bucket, photoPath, region);
		assertEquals(result, "SUCCESS");
	}
}
