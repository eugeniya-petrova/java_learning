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
        if (app.db().contactSet().size() == 0) {
            app.contact().create(new ContactData().withFirstName("for delete"), true);
            app.goTo().homePage();
        }
    }

    @Test(enabled = true)
    public void testContactDeletion() throws Exception {
        ContactSet before = app.db().contactSet();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
		app.goTo().homePage(); //принудительный переход на стартовую страницу
        assertThat(app.contact().count(), equalTo(before.size() - 1)); //сравниваем количество контактов до и после
        ContactSet after = app.db().contactSet();
        assertThat(after, equalTo(before.without(deletedContact)));
		verifyContactListInUI();
    }

}
