package ru.learning.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.learning.addressbook.appmanager.ApplicationManager;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
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

}
