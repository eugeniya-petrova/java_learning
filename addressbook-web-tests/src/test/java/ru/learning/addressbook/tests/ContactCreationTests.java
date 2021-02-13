package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("Фёдор", "Шаляпин", "Казань, улица Чебоксарская, дом 11", "+7(495)1112233", "+79054445566", "+74957778899", "+74950001122", "test1302@test.ru", "test1302-1@test.ru", "test1302-2@test.ru", "Санкт-Петербург, улица Пушкина, дом 12"));
    app.getNavigationHelper().returnToHomePage();
  }

}
