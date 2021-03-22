package ru.learning.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.mantis.appmanager.HttpSession;
import ru.learning.mantis.model.MailMessage;
import ru.learning.mantis.model.UserData;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase{
	
	//перед каждым тестом запускаем почтовый сервер заново, чтобы не было взаимовлияния тестов друг на друга
	@BeforeMethod
	public void startMailServer() {
		app.mail().start();
	}
	
	@Test
	public void testChangePassword() throws IOException, MessagingException{
		UserData user = app.db().userSet().iterator().next();
		app.changePassword().start(user);
		List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000); //должно придти одно письмо, ожидание 10 секунд
		String confirmationLink = app.mail().findConfirmationLink(mailMessages, user.getEmail());
		
		long now = System.currentTimeMillis();
		String newPassword = String.format("pass%s", now);
		app.changePassword().finish(confirmationLink, user, newPassword);
		
		HttpSession session = app.newSession();
		assertTrue(session.preLogin(user.getUsername()));
        assertTrue(session.login(user.getUsername(), newPassword));
        assertTrue(session.isLoggedInAs(user.getUsername()));
	}
	
	//после каждого теста останавливаем почтовый сервер
	@AfterMethod(alwaysRun = true)
	public void stopMailServer() {
		app.mail().stop();
	}


	//String newPassword = app.db().userSet().stream().filter((u) -> ((Integer) u.getId()).equals(user.getId()))
	//		.findAny().get().getPassword();
}
