package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase{

    @BeforeMethod
    public void checkPreconditions() {
        if (app.contact().set().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstName("for emails").withEmail("t-t_@test.ru").withEmail3("t.t@test.ru"), true);
            app.goTo().homePage();
        }
    }

    @Test(enabled = false)
    public void testContactEmails() {
        app.goTo().homePage();
        ContactData contact = app.contact().set().iterator().next();
        ContactData emailsFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(emailsFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> ! s.equals("")).collect(Collectors.joining("\n"));
    }

}
