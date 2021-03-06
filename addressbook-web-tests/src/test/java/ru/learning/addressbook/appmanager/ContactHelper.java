package ru.learning.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;
import ru.learning.addressbook.model.GroupSet;
import ru.learning.addressbook.model.GroupData;

import java.util.List;

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
		attach(By.name("photo"), contactData.getPhoto());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("fax"), contactData.getFax());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());

        if (create) {
			//проверка, что именно передано во входных данных в тесте!
			//если не передано ни одной группы - не добавляем контакт никуда
			//если передана одна группа - пытаемся выбрать её из выпадающего списка
			//если передано больше одной группы - это невалидные ТД
            if (contactData.getGroupSet().size() > 0) {
				Assert.assertTrue(contactData.getGroupSet().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroupSet().iterator().next().getName());
            } 
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
		
		type(By.name("address2"), contactData.getAddress2());
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContactById(int id) {
        //wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
        wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void initContactModificationById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }
	
	//выбор группы, в которую хотим добавить контакт
	public void selectTargetGroupById(int id) {
		new Select(wd.findElement(By.name("to_group"))).selectByValue(String.format("%s", id));
	}
	
	//выбор группы, из которой хотим удалить контакт
	public void selectParentGroupById(int id) {
		new Select(wd.findElement(By.name("group"))).selectByValue(String.format("%s", id));
	}
	
	public void submitContactAdditionToGroup() {
		click(By.name("add"));
	}
	
	public void submitContactRemovingFromGroup() {
		click(By.name("remove"));
	}

	//переход на страницу конкретной группы
    public void goToSelectedGroupPage(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='./?group=%s']", id))).click();
    }

    public void create(ContactData contactData, boolean create) {
        initContactCreation();
        fillContactForm(contactData, create);
        submitContactCreation();
		contactCache = null;
    }

    public void modify(ContactData contact, boolean create) {
        initContactModificationById(contact.getId());
        fillContactForm(contact, create);
        submitContactModification();
		contactCache = null;
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
		contactCache = null;
    }
	
	public void addToGroup(ContactData contact, GroupData group) {
		selectContactById(contact.getId());
		selectTargetGroupById(group.getId());
		submitContactAdditionToGroup();
		contactCache = null;
        goToSelectedGroupPage(group.getId());
	}
	
	public void removeFromGroup(ContactData contact, GroupData group) {
		selectParentGroupById(group.getId());
		selectContactById(contact.getId());
		submitContactRemovingFromGroup();
		contactCache = null;
        goToSelectedGroupPage(group.getId());
	}

    public boolean whereContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private ContactSet contactCache = null;

    public ContactSet set() {
		if (contactCache != null) {
            return new ContactSet(contactCache); //создаётся копия кэша
        }
		
        contactCache = new ContactSet();
        List<WebElement> rows = wd.findElements(By.xpath("//table[@id='maintable']//tr"));

        //проходим по всем строкам таблицы, исключая строку заголовка, поэтому счётчик начинается с 1
        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.name("selected[]")).getAttribute("value")); //в первой ячейке ищем чекбокс, берём его value
            String lastName = cells.get(1).getText(); //во второй ячейке фамилия
            String firstName = cells.get(2).getText(); //в третьей ячейке имя
            String allPhones = cells.get(5).getText(); //в 6-й ячейке телефоны
            String allEmails = cells.get(4).getText(); //в 5-й ячейке емейлы
            String address = cells.get(3).getText(); //в 4-й ячейке адрес
            contactCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName)
                    .withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address));
        }
        return new ContactSet(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstName = getLocatorText(By.name("firstname"));
        String lastName = getLocatorText(By.name("lastname"));
        String homePhone = getLocatorText(By.name("home"));
        String mobilePhone = getLocatorText(By.name("mobile"));
        String workPhone = getLocatorText(By.name("work"));
        String email = getLocatorText(By.name("email"));
        String email2 = getLocatorText(By.name("email2"));
        String email3 = getLocatorText(By.name("email3"));
        String address = getLocatorText(By.name("address"));
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName)
                .withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone)
                .withEmail(email).withEmail2(email2).withEmail3(email3).withAddress(address);
    }
}
