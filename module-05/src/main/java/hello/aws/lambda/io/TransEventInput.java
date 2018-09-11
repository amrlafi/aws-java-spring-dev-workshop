package hello.aws.lambda.io;

public class TransEventInput {
	

	private String text;
	private String sourceLangCode;
	private String targetLangCode;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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

}
