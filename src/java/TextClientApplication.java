import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class TextClientApplication {

	public static AIResponse getData(String line) {

		AIConfiguration configuration = new AIConfiguration(
				"0aa04d5a7b0f4921873db055fbf6f85d");

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