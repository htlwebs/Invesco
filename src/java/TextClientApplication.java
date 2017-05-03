import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class TextClientApplication {

	public static AIResponse getData(String line) {

		AIConfiguration configuration = new AIConfiguration(
				"c707d732add944d2904e15b13d54501f");

		AIDataService dataService = new AIDataService(configuration);

		try {

			AIRequest request = new AIRequest(line);
			AIResponse response = dataService.request(request);
			return response;

		} catch (Exception ex) {

			ex.printStackTrace();
			System.out.println("Error in api ai TextClientApplication : " + ex);
			return null;
		}
	}
}
