package ru.learning.mantis.tests;

import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.learning.mantis.appmanager.HttpSession;
import ru.learning.mantis.model.MailMessage;

import java.io.IOException;
import javax.mail.MessagingException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        String user = "user1";
        String password = "password";
        String email = "user1@localhost.domain";
        app.registration().start(user, email);
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000); //должно придти два письма, ожидание 10 секунд
        String confirmationLink = app.mail().findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password);

        HttpSession session = app.newSession();
        assertTrue(session.preLogin(user));
        assertTrue(session.login(user, password));
        assertTrue(session.isLoggedInAs(user));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
