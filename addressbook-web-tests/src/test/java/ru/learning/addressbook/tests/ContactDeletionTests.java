package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        if (! app.getContactHelper().whereContact()) {
            app.getContactHelper().createContact(new ContactData("for delete", "for delete", "for delete", null, null, null, null, null, null, null, "Group модиф", null), true);
            app.getNavigationHelper().returnToHomePage();
        }

        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
    }

}
