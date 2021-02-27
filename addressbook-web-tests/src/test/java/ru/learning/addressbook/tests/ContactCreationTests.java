package ru.learning.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test(enabled = false)
  public void testContactCreation() throws Exception {
    Set<ContactData> before = app.contact().set();
    ContactData contact = new ContactData()
            .withFirstName("Мигель").withLastName("Мигелидзе").withAddress("адрес 2702-02").withHomePhone("+7(495)1112233").withEmail("testcontact@test.ru").withGroup("Group модиф").withAddress2("адрес 2702-03");
    app.contact().create(contact, true);
    app.goTo().homePage();
    Set<ContactData> after = app.contact().set();
    Assert.assertEquals(after.size(), before.size() + 1);

    //contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
