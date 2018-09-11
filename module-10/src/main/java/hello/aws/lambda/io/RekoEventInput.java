package hello.aws.lambda.io;

import java.util.List;

public class RekoEventInput {

	private String bucket;
	private String path;
	
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
