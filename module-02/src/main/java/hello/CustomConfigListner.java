package hello;

import java.util.Properties;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;

import com.amazonaws.SDKGlobalConfiguration;


public class CustomConfigListner implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
	
	// DO !! overide this method with your Parameter Store, refer ParameterStoreTest.java and 
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		
		System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
		
		AWSSimpleSystemsManagement client = AWSSimpleSystemsManagementClientBuilder.standard().build();
			
		GetParameterRequest parameterRequest = new GetParameterRequest();
		parameterRequest.withName("datasource.url").setWithDecryption(Boolean.valueOf(true));
		GetParameterResult parameterResult = client.getParameter(parameterRequest);
		String url = parameterResult.getParameter().getValue();

		parameterRequest.withName("datasource.username").setWithDecryption(Boolean.valueOf(true));
		parameterResult = client.getParameter(parameterRequest);
		String username = parameterResult.getParameter().getValue();	
		
		parameterRequest.withName("datasource.password").setWithDecryption(Boolean.valueOf(true));
		parameterResult = client.getParameter(parameterRequest);
		String password = parameterResult.getParameter().getValue();
//		String version = parameterResult.getParameter().getVersion().toString();
		
    ConfigurableEnvironment environment = event.getEnvironment();
    Properties props = new Properties();
    props.put("spring.datasource.url", url);
    props.put("spring.datasource.username", username);
    props.put("spring.datasource.password", password);
    environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
    
    System.out.println("##### url = " + url);
    System.out.println("##### username = " + username);
    System.out.println("##### password = " + password);	
	 }
	
}