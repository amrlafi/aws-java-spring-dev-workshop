package hello;

import java.net.URL;
import java.util.Properties;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.handlers.TracingHandler;
import com.amazonaws.xray.plugins.EC2Plugin;
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;

import hello.config.XRayConfig;


public class CustomConfigListner implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
//  static {
//  	  System.out.println("\n############# static CustomConfig Listner : init AWSXRayRecorderBuilder\n");
//    AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withPlugin(new EC2Plugin());
//
//    URL ruleFile = WebConfig.class.getResource("/sampling-rules.json");
//    builder.withSamplingStrategy(new LocalizedSamplingStrategy(ruleFile));
//
//    AWSXRay.setGlobalRecorder(builder.build());
//    System.out.println("\n############# static CustomConfig Listner : build AWSXRayRecorderBuilder\n");
//  }
  
	// DO !! overide this method with your Parameter Store, refer ParameterStoreTest.java and 
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		
		AWSXRay.beginSegment("Workshop : Load ParameterStore");
		
		AWSSimpleSystemsManagement client = AWSSimpleSystemsManagementClientBuilder.standard()
				.withRequestHandlers(new TracingHandler(AWSXRay.getGlobalRecorder()))
				.build();
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
		String version = parameterResult.getParameter().getVersion().toString();
		
    ConfigurableEnvironment environment = event.getEnvironment();
    Properties props = new Properties();
    props.put("spring.mysql.jpa.hibernate.ddl-auto", "update");
    props.put("spring.mysql.datasource.url", url);
    props.put("spring.mysql.datasource.username", username);
    props.put("spring.mysql.datasource.password", password);
    props.put("spring.mysql.datasource.driver-class-name", "com.mysql.jdbc.Driver");
    //xray
    props.put("spring.mysql.datasource.jdbc-interceptors", "com.amazonaws.xray.sql.mysql.TracingInterceptor");   
    environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
    
    System.out.println("##### url = " + url);
    System.out.println("##### username = " + username);
    System.out.println("##### password = " + password);	
	    
    AWSXRay.endSegment();
	 }
	
}