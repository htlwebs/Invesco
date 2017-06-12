import java.util.HashMap;

public class ProcessedResponse {
	private String responseText;
	private String responseAction;
	private HashMap responseParameters;

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getResponseAction() {
		return responseAction;
	}

	public void setResponseAction(String responseAction) {
		this.responseAction = responseAction;
	}

	public HashMap getResponseParameters() {
		return responseParameters;
	}

	public void setResponseParameters(HashMap responseParameters) {
		this.responseParameters = responseParameters;
	}

}
