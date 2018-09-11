package hello.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.amazonaws.xray.AWSXRay;

import hello.Application;
import hello.model.mysql.User;
import hello.repository.mysql.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class MySqlTest {
	
	@Autowired
	UserRepository repository;
  
	@Test
	public void test () {
		
		AWSXRay.beginSegment("MySQLTest test"); 
		
		repository.deleteAll();
		
    User user1 = new User();
    user1.setName("Jeff Bar");
    user1.setEmail("bar@gmail.com");

    repository.save(user1);
    
    User user2 = new User();
    user2.setName("John Bell");
    user2.setEmail("bell@gmail.com");
    
    repository.save(user2);
    repository.findAll();

		assertEquals(repository.count(), 2);
		
    AWSXRay.endSegment();

	}

}
