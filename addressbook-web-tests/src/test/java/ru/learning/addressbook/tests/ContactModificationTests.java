package ru.learning.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (! app.getContactHelper().whereContact()) {
            app.getContactHelper().createContact(new ContactData("for update", "for update", "for update", "+7(495)1112233", "+79054445566", "+74957778899", "+74950001122", "test0902@test.ru", "test0902-1@test.ru", "test0902-2@test.ru", "Group модиф", "Санкт-Петербург, улица Пушкина, дом 8"), true);
            app.getNavigationHelper().returnToHomePage();
        }

        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification(before.size() - 1);
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "Дитмар модиф", "Розенталь модиф", "адрес 1", "+7(495)1112233", null, null, null, "test@test.ru", null, null, null, "Адрес 2");
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
