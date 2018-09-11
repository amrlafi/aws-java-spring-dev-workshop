package hello.repository.ddb;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;

import hello.model.ddb.PhotoInfo;


public interface PhotoInfoRepository extends PagingAndSortingRepository<PhotoInfo, String> {

 	@EnableScan
	List<PhotoInfo> findById(String id);
//	@EnableScan 
//	List<PhotoInfo> findByPrefix(String prefix);	
  @EnableScan 
	List<PhotoInfo> findAll();
	@EnableScan 
	void deleteAll();
	
}