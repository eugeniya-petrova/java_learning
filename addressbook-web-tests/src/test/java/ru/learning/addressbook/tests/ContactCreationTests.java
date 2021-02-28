package ru.learning.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        ContactSet before = app.contact().set();
        ContactData contact = new ContactData()
                .withFirstName("Мигель").withLastName("Мигелидзе").withAddress("адрес 2702-02").withHomePhone("+7(495)1112233").withEmail("testcontact@test.ru").withGroup("Group модиф").withAddress2("адрес 2702-03");
        app.contact().create(contact, true);
        app.goTo().homePage();
        ContactSet after = app.contact().set();
        assertThat(after.size(), equalTo(before.size() + 1));

        //contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

}
