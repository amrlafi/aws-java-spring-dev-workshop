package hello.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.regions.Regions;
import hello.logics.IntegratedTrans;
import hello.logics.IntegratedTransLambda;

@Controller
@RequestMapping(path="/workshop")
public class LogicsController {
	
//	@Autowired 
//	private IntegratedTrans trans;
	@Autowired 
	private IntegratedTransLambda trans;
	
	//add to test a integrate logics
	@GetMapping(path = "/trans/integrated")
  public @ResponseBody String transIntegrated(@RequestParam  String bucket
  		, @RequestParam String prefix, @RequestParam String region) {
			Regions _region = Regions.fromName(region);
  			trans.RetrieveAndSave(bucket, prefix, _region);
      return "\nTransferred.\n";
  } 
  
  
}
