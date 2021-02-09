package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Василий", "Чапаев", "Будайка, улица Чебоксарская, дом 7", "+7(495)1112233", "+79054445566", "+74957778899", "+74950001122", "test0902@test.ru", "test0902-1@test.ru", "test0902-2@test.ru", "Санкт-Петербург, улица Пушкина, дом 8"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnToHomePage();
  }

}
