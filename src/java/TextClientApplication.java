import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class TextClientApplication {

	public static AIResponse getData(String line) {

		AIConfiguration configuration = new AIConfiguration("0f7e16edc3d34b40904c5f775eb9d17b");

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
