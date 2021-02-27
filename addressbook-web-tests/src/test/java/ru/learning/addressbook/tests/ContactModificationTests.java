package ru.learning.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withFirstName("for update"), true);
            app.goTo().homePage();
        }
    }

    @Test(enabled = false)
    public void testContactModification() {
        Set<ContactData> before = app.contact().set();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstName("Ирвин модиф").withLastName("Шоу модиф").withAddress("адрес 1").withMobilePhone("+7(916)1112233").withEmail("test@test.ru").withAddress2("Адрес 2");
        app.contact().modify(contact, false);
        app.goTo().homePage();
        Set<ContactData> after = app.contact().set();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedContact);
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
