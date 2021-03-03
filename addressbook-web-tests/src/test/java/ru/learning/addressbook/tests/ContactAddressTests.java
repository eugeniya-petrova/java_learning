package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase{

    @BeforeMethod
    public void checkPreconditions() {
        if (app.contact().set().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstName("for address")
                    .withAddress("111222, Республика Саха(Якутия), Якутск, ул. Петропавловско-Камчатская, дом312, кв.84, этаж 3"), true);
            app.goTo().homePage();
        }
    }

    @Test(enabled = false)
    public void testContactAddress() {
        app.goTo().homePage();
        ContactData contact = app.contact().set().iterator().next();
        ContactData addressFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAddress(), equalTo(addressFromEditForm.getAddress()));
    }
}
