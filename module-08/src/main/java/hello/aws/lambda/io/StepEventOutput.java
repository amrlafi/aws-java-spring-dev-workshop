package hello.aws.lambda.io;

public class StepEventOutput {
	private String id;
	private String bucket;
	private String prefix;
	private String text;
	private String translated;
	private String sourceLangCode;
	private String targetLangCode;	
	private String err_message;
	
	public StepEventOutput() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTranslated() {
		return translated;
	}
	public void setTranslated(String translated) {
		this.translated = translated;
	}
	public String getSourceLangCode() {
		return sourceLangCode;
	}
	public void setSourceLangCode(String sourceLangCode) {
		this.sourceLangCode = sourceLangCode;
	}
	public String getTargetLangCode() {
		return targetLangCode;
	}
	public void setTargetLangCode(String targetLangCode) {
		this.targetLangCode = targetLangCode;
	}
	public String getErr_message() {
		return err_message;
	}
	public void setErr_message(String err_message) {
		this.err_message = err_message;
	}

	

}
