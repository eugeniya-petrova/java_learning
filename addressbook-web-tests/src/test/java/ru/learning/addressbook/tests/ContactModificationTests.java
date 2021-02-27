package ru.learning.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData("for update", "for update", "for update", "+7(495)3332211", null, null, null, "test-update@test.ru", null, null, null, null), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactModification() {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        ContactData contact = new ContactData(before.get(index).getId(), "Ирвин модиф", "Шоу модиф", "адрес 1", "+7(495)1112233", null, null, null, "test@test.ru", null, null, null, "Адрес 2");
        app.contact().modify(index, contact, false);
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
