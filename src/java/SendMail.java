import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	public void sendMail(String usremail, String pass) {
		String email = usremail;
		String subject = "Password Reset";
		// String msg = request.getParameter("message");
		// String htmlmsg = "<h1><center>"+msg+"</center></h1>";
		String htmlmsg = "<center><h1 style=\"text-align: center;color: #195A94;font-size: 350%;\">Map My Marketing</h1>"
				+ "<p style=\"text-align: center;font-family: Consolas;font-size:130%;\">"

				+ "<br><br> </p>"
				+ "</center>"
				+ "<h2><b>User Email : </b>"
				+ usremail
				+ "<br><br><b>Password : </b>"
				+ pass
				+ "<br><br></h2>";

		// code for sending main

		final String username = "no-reply@mapmymarketing.ai";
		final String password = "M3M#7982";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(username,
						"Map My Marketing"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject(subject);
			message.setContent(htmlmsg, "text/html");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub SendMail send = new SendMail(); send.sendMail(); }
	 */

}
