package ru.learning.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationHelper extends HelperBase{
	
	private WebDriver wd;
	
	public RegistrationHelper(ApplicationManager app) {
		super(app);
		wd = app.getDriver();
	}

    public void start(String username, String email) {
		wd.get(app.getProperty("web.baseURL") + "signup_page.php");
		type(By.name("username"), username);
		type(By.name("email"), email);
		click(By.cssSelector("input[type='submit']"));
	}
}
