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
import com.amazonaws.xray.AWSXRay;

import hello.Application;
import hello.model.ddb.PhotoInfo;
import hello.repository.ddb.PhotoInfoRepository;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class IntegratedTransTest {
	
	private String bucket = "seon-virginia-2016";
	private Regions region = Regions.US_EAST_1;
	String photoPath = "a.jpeg";

	@Autowired
	PhotoInfoRepository repository;
	
	@Autowired
	IntegratedTrans tr;
	
	@Test
	public void testRetrieveAndSave()
	{
		AWSXRay.beginSegment("Workshop:logics RetrieveAndSave"); 
		repository.deleteAll();
		tr.RetrieveAndSave(bucket, photoPath, region);
		String photoPrefix = bucket + "/" + photoPath;
		List<PhotoInfo> list = (List<PhotoInfo>) repository.findAll();
		assertTrue(list.size() > 0);
		AWSXRay.endSegment(); 
	}

}
