package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.db().contactSet().size() == 0) {
            app.contact().create(new ContactData().withFirstName("for update"), true);
            app.goTo().homePage();
        }
    }

    @Test(enabled = true)
    public void testContactModification() {
        ContactSet before = app.db().contactSet();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstName("Сомерсет модиф").withLastName("Моэм модиф")
                .withAddress("адрес 1").withMobilePhone("+7(916)1112233").withEmail("test@test.ru").withAddress2("Адрес 2");
        app.contact().modify(contact, false);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size())); //сравниваем количество контактов до и после
        ContactSet after = app.db().contactSet();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(app.db().contactById(contact.getId()))));
		verifyContactListInUI();
    }
}
