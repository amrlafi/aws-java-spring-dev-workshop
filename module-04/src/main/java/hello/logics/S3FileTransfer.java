package hello.logics;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class S3FileTransfer {
	
	public boolean checkBucket(String bucket, Regions region) {
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
		if (s3.doesBucketExistV2(bucket)) {return true;
		} else {
			//create a bucket
			 try {
	        Bucket b = s3.createBucket(bucket);
	    } catch (AmazonS3Exception e) {
	        System.err.println(e.getErrorMessage());
	        return false; //error
	    }
		}
		return true;
	}
	
	public void put(String bucket, String key, File file, Regions region)
	{
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
		try {
			
		    s3.putObject(bucket, key, file);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
	
	public List<String> list(String prefix, Regions region) 
	{
		List<String> objectList = new ArrayList<String>();
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
		ObjectListing object_listing = s3.listObjects(prefix);

    for (Iterator<?> iterator = object_listing.getObjectSummaries().iterator(); iterator.hasNext();) {
	    S3ObjectSummary summary = (S3ObjectSummary)iterator.next();
//	    System.out.println("################# = " + summary.getKey());
	    objectList.add(summary.getKey());
//	    s3.deleteObject(prefix, summary.getKey());
    }
    return objectList;
		 
	}
	


}
