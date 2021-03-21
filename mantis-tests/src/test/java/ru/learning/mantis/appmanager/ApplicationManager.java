package ru.learning.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
	private final Properties properties;
    private WebDriver wd;
	private RegistrationHelper registrationHelper;

    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
		properties = new Properties();
    }

    public void init() throws IOException {
		String target = System.getProperty("target", "local");
		properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
		if (wd != null) { //останавливаем драйвер, только если он был проинициализирован
			wd.quit();
		}
    }
	
	//позволяет помощникам получать у ApplicationManager системные свойства
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	//метод инициализирует драйвер в момент первого обращения
	public WebDriver getDriver() {
		if (wd == null) { //если драйвер ещё не проинициализирован, нужно его проинициализировать
			if (browser.equals(BrowserType.FIREFOX)) {
            wd = new FirefoxDriver();
            wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        } else if (browser.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
            wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }

        wd.get(properties.getProperty("web.baseURL"));
		
		}
		return wd;
	}
	
	//вызов помощника HttpSession
	public HttpSession newSession() {
		return new HttpSession(this);
	}
	
	//вызов помощника RegistrationHelper
	public RegistrationHelper registration() {
		if (registrationHelper == null) { //если метод registration() ещё ни разу не дёргали, инициализируем новый RegistrationHelper
			registrationHelper = new RegistrationHelper(this);
		}
		return registrationHelper;
	}

    private boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
