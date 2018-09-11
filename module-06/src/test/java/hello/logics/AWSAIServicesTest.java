package hello.logics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.model.Label;

@SpringBootTest
public class AWSAIServicesTest {
	
	private String bucket = "seon-virginia-2016";
	private Regions region = Regions.US_EAST_1;
	String photoPath = "a.jpeg";
	
	@Test
	public void testRetrieveInfoFromPhoto()
	{

	}
	
	@Test
	public void testTranslate()
	{
		AWSAIServices ai = new AWSAIServices();
		assertEquals(ai.translate("Hello,World", "en", "es", region), "Hola, Mundo");
	}
	

}
