package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase{

    @BeforeMethod
    public void checkPreconditions() {
        if (app.contact().set().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstName("for phones").withHomePhone("123-22 22").withWorkPhone("+7(345)"), true);
            app.goTo().homePage();
        }
    }

    @Test(enabled = false)
    public void testContactPhones() {
        app.goTo().homePage();
        ContactData contact = app.contact().set().iterator().next();
        ContactData phonesFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(phonesFromEditForm)));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactPhoneTests::cleaned).collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
