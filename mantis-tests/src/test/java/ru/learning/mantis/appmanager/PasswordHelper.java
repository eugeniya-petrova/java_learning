package ru.learning.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.learning.mantis.model.UserData;

public class PasswordHelper extends HelperBase{
	
	private WebDriver wd;
	
	public PasswordHelper(ApplicationManager app) {
		super(app);
		wd = app.getDriver();
	}
	
	public void start(UserData user) {
		adminLogin();
		searchUser(user);
		initResetPassword();
	}
	
	public void finish(String confirmationLink, UserData user, String newPassword) {
		wd.get(confirmationLink);
		type(By.name("realname"), user.getRealname());
		type(By.name("password"), newPassword);
		type(By.name("password_confirm"), newPassword);
		click(By.cssSelector("button[type='submit']"));
	}
	
	public void adminLogin() {
		wd.get(app.getProperty("web.baseURL") + "login_page.php");
		type(By.name("username"), app.getProperty("web.adminLogin"));
		click(By.cssSelector("input[type='submit']"));
		type(By.name("password"), app.getProperty("web.adminPassword"));
		click(By.cssSelector("input[type='submit']"));
	}
	
	public void searchUser(UserData user) {
		wd.findElement(By.cssSelector("a[href='/mantisbt-2.25.0/manage_overview_page.php']")).click();
		wd.findElement(By.cssSelector("a[href='/mantisbt-2.25.0/manage_user_page.php']")).click();
		wd.findElement(By.cssSelector(String.format("a[href='/mantisbt-2.25.0/manage_user_page.php?user_id=%s']", user.getId()))).click();
	}
	
	public void initResetPassword() {
		click(By.xpath("//form[@id='manage-user-reset-form']//input[@type='submit']"));
	}
}