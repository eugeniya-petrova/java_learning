package ru.learning.mantis.tests;

import org.testng.annotations.Test;
import ru.learning.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase{
	
	@Test
	public void testLogin() throws IOException {
		HttpSession session = app.newSession();
		assertTrue(session.preLogin("administrator"));
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.isLoggedInAs("administrator"));
	}
}
