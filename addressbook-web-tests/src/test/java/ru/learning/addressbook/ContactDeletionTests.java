package ru.learning.addressbook;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        selectContact();
        deleteSelectedContacts();
    }

}
