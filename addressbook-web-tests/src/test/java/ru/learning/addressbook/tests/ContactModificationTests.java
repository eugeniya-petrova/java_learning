package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (! app.getContactHelper().whereContact()) {
            app.getContactHelper().createContact(new ContactData("for update", "for update", "for update", "+7(495)1112233", "+79054445566", "+74957778899", "+74950001122", "test0902@test.ru", "test0902-1@test.ru", "test0902-2@test.ru", "Group модиф", "Санкт-Петербург, улица Пушкина, дом 8"), true);
            app.getNavigationHelper().returnToHomePage();
        }

        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Василий модиф", "Чапаев модиф", "Будайка, улица Чебоксарская, дом 7", "+7(495)1112233", "+79054445566", "+74957778899", "+74950001122", "test0902@test.ru", "test0902-1@test.ru", "test0902-2@test.ru", null, "Санкт-Петербург, улица Пушкина, дом 8"), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().returnToHomePage();
    }
}
