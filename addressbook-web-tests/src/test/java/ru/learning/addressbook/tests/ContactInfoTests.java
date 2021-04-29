package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTests extends TestBase {

    @BeforeTest
    public void checkPreconditions() {
        if (app.contact().set().size() == 0) {
            app.contact().create(new ContactData().withFirstName("for info")
                    .withHomePhone("123-22 22").withWorkPhone("+7(345)").withEmail("t-t_@test.ru").withEmail3("t.t@test.ru")
                    .withAddress("111222, Республика Саха(Якутия), Якутск, ул. Петропавловско-Камчатская, дом312, кв.84, этаж 3"), true);
            app.goTo().homePage();
        }
    }

    @Test(enabled = true)
    public void testContactPhones() {
        app.goTo().homePage();
        ContactData contact = app.contact().set().iterator().next();
        ContactData phonesFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(phonesFromEditForm)));
    }

    @Test(enabled = true)
    public void testContactEmails() {
        app.goTo().homePage();
        ContactData contact = app.contact().set().iterator().next();
        ContactData emailsFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(emailsFromEditForm)));
    }

    @Test(enabled = true)
    public void testContactAddress() {
        app.goTo().homePage();
        ContactData contact = app.contact().set().iterator().next();
        ContactData addressFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAddress(), equalTo(addressFromEditForm.getAddress()));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactInfoTests::cleaned).collect(Collectors.joining("\n"));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals("")).collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}
