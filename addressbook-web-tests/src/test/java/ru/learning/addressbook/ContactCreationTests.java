package ru.learning.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("Дмитрий", "Менделеев", "Тобольск, улица Тюменская, дом 5", "+7(495)1112233", "+79034445566", "+74957778899", "+74950001122", "test0802@test.ru", "test0802-1@test.ru", "test0802-2@test.ru", "Санкт-Петербург, улица Пушкина, дом 6"));
    submitContactCreation();
    returnToHomePage();
  }

}
