package org.alkemy.campus.challenge.services;

import java.io.IOException;

import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	public String sendTextEmail(String receiver) throws IOException {
		// the sender email should be the same as we used to Create a Single Sender Verification
		Email from = new Email("c.rodri92@gmail.com");
		String subject = "Welcome to DisneyAPI";
		Email to = new Email(receiver);
		Content content = new Content("text/plain", "This is a test email");
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			logger.info(response.getBody());
			return response.getBody();
		} catch (IOException ex) {
			throw ex;
		}
	}
}
