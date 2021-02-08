package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Дмитрий", "Менделеев", "Тобольск, улица Тюменская, дом 5", "+7(495)1112233", "+79034445566", "+74957778899", "+74950001122", "test0802@test.ru", "test0802-1@test.ru", "test0802-2@test.ru", "Санкт-Петербург, улица Пушкина, дом 6"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnToHomePage();
  }

}
