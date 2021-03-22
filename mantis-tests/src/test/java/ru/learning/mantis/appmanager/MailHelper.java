package ru.learning.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import ru.lanwen.verbalregex.VerbalExpression;
import ru.learning.mantis.model.MailMessage;

public class MailHelper {
	
	private ApplicationManager app;
	private final Wiser wiser;
	
	public MailHelper(ApplicationManager app) {
		this.app = app;
		wiser = new Wiser(); //почтовый сервер
	}
	
	public List<MailMessage> waitForMail(int count, long timeout) throws MessagingException, IOException {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < start + timeout) {
			if (wiser.getMessages().size() >= count) {
				return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		throw new Error("No mail");
	}
	
	public static MailMessage toModelMail(WiserMessage m) {
		try {
			MimeMessage mm = m.getMimeMessage();
			return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void start() {
		wiser.start();
	}
	
	public void stop() {
		wiser.stop();
	}
	
	public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
		MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get(); //фильтруем письма, которые пришли на нужный емейл, берём первое
		
		//регулярное выражение, которое ищет вхождение http:// после которого идёт один или несколько непробельных символов
		VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
		return regex.getText(mailMessage.text); //применяем это регулярное выражение к телу письма
	}
}
