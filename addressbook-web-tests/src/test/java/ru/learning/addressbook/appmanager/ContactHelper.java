package ru.learning.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.learning.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData, boolean create) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("fax"), contactData.getFax());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());

        if (create) {
            if (contactData.getGroup() != null) {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            } else {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText("[none]");
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
		
		type(By.name("address2"), contactData.getAddress2());
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void initContactModification(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void create(ContactData contactData, boolean create) {
        initContactCreation();
        fillContactForm(contactData, create);
        submitContactCreation();
    }

    public void modify(ContactData contact, boolean create) {
        initContactModification(contact.getId());
        fillContactForm(contact, create);
        submitContactModification();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
    }

    public boolean whereContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public Set<ContactData> set() {
        Set<ContactData> contacts = new HashSet<ContactData>();
        List<WebElement> rows = wd.findElements(By.xpath("//table[@id='maintable']//tr"));

        //проходим по всем строкам таблицы, исключая строку заголовка, поэтому счётчик начинается с 1
        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            String lastName = cells.get(1).getText(); //во второй ячейке фамилия
            String firstName = cells.get(2).getText(); //в третьей ячейке имя
            int id = Integer.parseInt(cells.get(0).findElement(By.name("selected[]")).getAttribute("value")); //в первой ячейке ищем чекбокс, берём его value
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
        }

        return contacts;
    }
}
