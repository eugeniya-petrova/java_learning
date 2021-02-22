package ru.learning.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        if (! app.getContactHelper().whereContact()) {
            app.getContactHelper().createContact(new ContactData("for delete", "for delete", "for delete", null, null, null, null, null, null, null, "Group модиф", null), true);
            app.getNavigationHelper().returnToHomePage();
        }

        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().deleteSelectedContacts();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }

}
