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
            app.contact().create(new ContactData().withFirstName("for update"), true);
            app.goTo().homePage();
        }
    }

    @Test(enabled = false)
    public void testContactModification() {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        ContactData contact = new ContactData()
                .withId(before.get(index).getId()).withFirstName("Ирвин модиф").withLastName("Шоу модиф").withAddress("адрес 1").withMobilePhone("+7(916)1112233").withEmail("test@test.ru").withAddress2("Адрес 2");
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
