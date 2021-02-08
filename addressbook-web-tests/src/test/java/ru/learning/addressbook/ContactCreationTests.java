package ru.learning.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("Владимир", "Маковский", "Москва, улица Ленина, дом 3", "+7(495)1112233", "+79164445566", "+74957778899", "+74950001122", "test@test.ru", "test1@test.ru", "test2@test.ru", "Санкт-Петербург, улица Пушкина, дом 4"));
    submitContactCreation();
    returnToHomePage();
  }

}
