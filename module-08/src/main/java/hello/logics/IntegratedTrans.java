package hello.logics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.model.Label;

import hello.model.ddb.PhotoInfo;
import hello.repository.ddb.PhotoInfoRepository;

// 
// This is a integrated logics to retrieve information from images and save it to the DyanmoDB
@Service
public class IntegratedTrans {
	
	@Autowired
	PhotoInfoRepository repository;
	
	public void RetrieveAndSave(String bucket, String photoPath, Regions region)
	{
		
		String path = bucket + "/" + photoPath;
		String source;
		String translated;
		
		//retrieve information
		AWSAIServices ai = new AWSAIServices();
		List<Label> labels = ai.retrieveInformation(bucket, photoPath, region);

		
		System.out.println("Detected labels for " + photoPath);
		StringBuilder words = new StringBuilder();
		for (Label label: labels) {
			String w = label.getName().toString();
			if(words.length() > 0) words.append(",").append(w);
			else words.append(w);
			
			System.out.println("#### = " + label.getName() + ": " + label.getConfidence().toString());
		}
		
		//Translate
		source = words.toString();
		translated = ai.translate(source, "en", "es", region);
		System.out.println("#### source = " + translated );
		System.out.println("#### translated = " + translated );
		
		//save info to DDB
		PhotoInfo p = new PhotoInfo(path, source, translated);	
	    repository.save(p);
	    
	}

}
