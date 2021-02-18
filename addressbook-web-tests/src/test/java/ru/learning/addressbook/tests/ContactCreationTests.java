package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("Фёдор", "Шаляпин", "Казань, улица Чебоксарская, дом 11", "+7(495)1112233", null, null, null, "test1802@test.ru", null, null, "Group модиф", "Санкт-Петербург, улица Пушкина, дом 12"), true);
    app.getNavigationHelper().returnToHomePage();
  }

}
