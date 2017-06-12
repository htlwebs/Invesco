package ai.recast;

import java.util.Scanner;
/**
 * Testing recast.ai custom files
 * api.recast.ai/v2/converse -  used for conversion and return repies
 * api.recast.ai/v2/request - used only to identify actions and entites
 * @author Haresh
 *
 */
public class TestApp {

	public static String token = "16ab3934649f67c6cc517977dda51160";
	Response response = null;
	Intent intent = null;
	static Entity entity[];

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		Client client = new Client(token);
		Conversation conv = null;

		Scanner scanner = null;
		while (true) {
			scanner = new Scanner(System.in);

			try {

				// response=client.textRequest(ss.nextLine());
				// System.out.println("Intent Name: "+intent.getName());
				// System.out.println("Entites Name: "+entity[0].getName());
				// System.out.println("Entites Value: "+entity[0].getValue());
				// System.out.println("Source: "+response.getSource());
				// System.out.println("Replies: "+response.getReplies());

				conv = client.request.doTextConverse(scanner.nextLine());
				entity = conv.getEntities().get("name");

				System.out.println("Conversion Source:" + conv.getSource());
				System.out.println("Conversion Replies:"
						+ conv.getAction().getReply());
				System.out.println("Conversion Action:"
						+ conv.getAction().getSlug());
				System.out.println("Conversion Entities:"
						+ entity[0].getValue());
			} catch (Exception e) {
				System.out.println("Error in request");
			}
		}
	}

}
