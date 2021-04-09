package ru.learning.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
	private final Properties properties;
    WebDriver wd;

    private SessionHelper sessionHelper;
    private ContactHelper contactHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private RestHelper restHelper;
	private DbHelper dbHelper;
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
		properties = new Properties();
    }

    public void init() throws IOException {
		String target = System.getProperty("target", "local");
		properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
		
		dbHelper = new DbHelper();

		if ("".equals(properties.getProperty("selenium.server"))) { //если не указан адрес selenium server
            if (browser.equals(BrowserType.FIREFOX)) {
                wd = new FirefoxDriver();
                wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            } else if (browser.equals(BrowserType.CHROME)) {
                wd = new ChromeDriver();
                wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            }
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
            wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }


        wd.get(properties.getProperty("web.baseURL"));
        groupHelper = new GroupHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        contactHelper = new ContactHelper(wd);
        sessionHelper = new SessionHelper(wd);
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

    public void stop() {
        sessionHelper.logout();
        wd.quit();
    }
	
	//позволяет помощникам получать у ApplicationManager системные свойства
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

    private boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    //вызов помощника RestHelper
    public RestHelper rest() {
        if (restHelper == null) { //если метод rest() ещё ни разу не дёргали, инициализируем новый RestHelper
            restHelper = new RestHelper(this);
        }
        return restHelper;
    }
	
	public DbHelper db() {
        return dbHelper;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES); //явное указание, что wd реализует интерфейс TakesScreenshot
    }

    //public SessionHelper getSessionHelper() {
    // return sessionHelper;
    //}
}
