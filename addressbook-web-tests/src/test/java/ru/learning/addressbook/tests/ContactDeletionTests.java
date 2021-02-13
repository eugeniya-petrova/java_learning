package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        if (! app.getContactHelper().whereContact()) {
            app.getContactHelper().createContact(new ContactData("for delete", "for delete", "for delete", "+7(495)1112233", "+79054445566", "+74957778899", "+74950001122", "test0902@test.ru", "test0902-1@test.ru", "test0902-2@test.ru", "Санкт-Петербург, улица Пушкина, дом 8"));
            app.getNavigationHelper().returnToHomePage();
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
    }

}
