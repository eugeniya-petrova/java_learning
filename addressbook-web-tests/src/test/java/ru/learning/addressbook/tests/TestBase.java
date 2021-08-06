package ru.learning.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import ru.learning.addressbook.appmanager.ApplicationManager;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Listeners(MyTestListener.class)
public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);
    Object attr;

    protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite(alwaysRun = true)
    public void setUp(ITestContext context) throws Exception {
        app.init();
		context.setAttribute("app", app); //в контекст помещаем ссылку на ApplicationManager, чтобы её мог извлечь MyTestListener
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }
	
	@BeforeMethod(alwaysRun = true)
    public void logTestStart(Method m, Object[] p) {
        logger.debug("Start test " + m.getName() + " with parameters" + Arrays.asList(p));
    }
	
	@AfterMethod(alwaysRun = true)
    public void logTestStop(Method m, ITestResult r) {
        logger.debug("Stop test " + m.getName());
        logger.debug("test result " + String.valueOf(r.getStatus()));

        if (r.getStatus() == 2) { //если тест упал
            logger.debug("Test failed with result " + r.getThrowable().toString());
			//logger.debug("Test failed with result " + r.getAttribute("m_throwable").toString());
            attr = r.getFactoryParameters();
        }
        
    }

    public void verifyGroupListInUI() {
        //проверка, что указано системное свойство verifyUI - по умолчанию false (без проверки)
        if (Boolean.getBoolean("verifyUI")) {
            GroupSet dbGroupSet = app.db().groupSet();
            GroupSet uiGroupSet = app.group().set();
            assertThat(uiGroupSet, equalTo(dbGroupSet.stream()
                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName())).collect(Collectors.toSet())));
        }
    }

    public void verifyContactListInUI() {
        //проверка, что указано системное свойство verifyUI - по умолчанию false (без проверки)
        if (Boolean.getBoolean("verifyUI")) {
            ContactSet dbContactSet = app.db().contactSet();
            ContactSet uiContactSet = app.contact().set();
            assertThat(uiContactSet, equalTo(dbContactSet.stream()
                    .map((c) -> new ContactData().withId(c.getId()).withFirstName(c.getFirstName()).withLastName(c.getLastName())
                            .withAllPhones(c.getAllPhones()).withAllEmails(c.getAllEmails()).withAddress(c.getAddress()))
                    .collect(Collectors.toSet())));
        }
    }
	
	public void verifyContactsInGroupUI(GroupData group) {
        //проверка, что указано системное свойство verifyUI - по умолчанию false (без проверки)
        if (Boolean.getBoolean("verifyUI")) {
            ContactSet dbContactSet = app.db().groupById(group.getId()).getContactSet();
            ContactSet uiContactSet = app.contact().set();
            assertThat(uiContactSet, equalTo(dbContactSet.stream()
                    .map((c) -> new ContactData().withId(c.getId()).withFirstName(c.getFirstName()).withLastName(c.getLastName())
                            .withAllPhones(c.getAllPhones()).withAllEmails(c.getAllEmails()).withAddress(c.getAddress()))
                    .collect(Collectors.toSet())));
        }
    }

    public boolean isIssueOpen(int issueId) throws IOException {
        if (app.rest().getIssueStateNameById(issueId).equals("Resolved")) {
            return false;
        } else {
            return true;
        }
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

}
