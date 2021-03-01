package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.contact().set().size() == 0) {
            app.contact().create(new ContactData().withFirstName("for delete"), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        ContactSet before = app.contact().set();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        ContactSet after = app.contact().set();
        assertThat(after.size(), equalTo(before.size() - 1));
        assertThat(after, equalTo(before.without(deletedContact)));
    }

}
